package game;

import game.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel implements Runnable {

    private int tick = 0;

    private BufferedImage world;
    private ArrayList<Tank> tanks;
    private Tank tank1;
    private Tank tank2;
    public static BufferedImage bulletImage;
    private Launcher launcher;
    private ArrayList<Wall> walls;
    private ArrayList<PowerUp> powerUps;

    //values for initialization
    private final int tank1SpawnX = 100;
    private final int tank1SpawnY = 100;
    private final int tank2SpawnX = 200;
    private final int tank2SpawnY = 200;


    public Game(Launcher launcher) {
        this.launcher = launcher;
    }

    /**
     * Load all resources for game. Set all GameObjects to their
     * initial state as well.
     */
    public void initializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tank1Image = null;
        BufferedImage tank2Image = null;
        BufferedImage breakableWallImage = null;
        BufferedImage unbreakableWallImage = null;
        BufferedImage shieldImage = null;
        walls = new ArrayList<>();
        powerUps = new ArrayList<>();

        try {
            //world = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Background.bmp")));
            tank1Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank1.gif")));
            tank2Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank2.gif")));
            bulletImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Weapon.gif")));
            breakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall1.gif")));
            unbreakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall2.gif")));
            shieldImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Shield1.gif")));
            InputStreamReader isr = new InputStreamReader(Game.class.getClassLoader().getResourceAsStream("maps/map1.txt"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("map cannot be read.");
            }
            String[] mapInfo = row.split("\t");
            //get size of the map
            int numOfCol = Integer.parseInt(mapInfo[0]);
            int numOfRows = Integer.parseInt(mapInfo[1]);
            //add all gameObjects to the correct spot in map
            for (int curRow = 0; curRow < numOfRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numOfCol; curCol++) {
                    switch (mapInfo[curCol]) {
                        case "2": //breakable wall
                            this.walls.add(new BreakableWall(curCol * 32, curRow * 32, breakableWallImage));
                            break;
                        case "5": // powerUp
                            this.powerUps.add(new Shield(curCol*32, curRow*32,shieldImage));
                            break;
                        case "3": //unbreakable wall
                        case "9": //outer border, not in use
                            this.walls.add(new UnbreakableWall(curCol * 32, curRow * 32, unbreakableWallImage));
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        tank1 = new Tank(tank1SpawnX, tank1SpawnY, tank1Image, 0, 0, 0);
        tank2 = new Tank(tank2SpawnX, tank2SpawnY, tank2Image, 0, 0, 0);
        //create the controllers for the two tanks
        VehicleController tank1Controller = new VehicleController(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_ENTER);
        VehicleController tank2Controller = new VehicleController(tank2, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_J, KeyEvent.VK_SPACE);
        this.launcher.getjFrame().addKeyListener(tank1Controller);
        this.launcher.getjFrame().addKeyListener(tank2Controller);

    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                tick++;
                //call all update functions
                checkCollision(tank1, tank2);
                checkCollision(tank2, tank1);
                tank1.update(tick);
                tank2.update(tick);
                //redraw game
                this.repaint();
                Thread.sleep(1000 / 144);
                if (tick > 3000) {
                    System.out.println("switching to end menu");
                    this.launcher.setFrame("end");
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    //this resets gameObject values to their initial locations/values
    private void resetGame() {
        this.tank1.setX(tank1SpawnX);
        this.tank1.setY(tank1SpawnY);
        this.tank2.setX(tank2SpawnX);
        this.tank2.setY(tank2SpawnY);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.powerUps.forEach(powerUp -> powerUp.drawImage(buffer));
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);
        //creates left and right screen and miniMap
        BufferedImage leftScreen = world.getSubimage(getSplitScreenWidth(tank1), getSplitScreenHeight(tank1), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightScreen = world.getSubimage(getSplitScreenWidth(tank2), getSplitScreenHeight(tank2), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        //draw
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, GameConstants.GAME_SCREEN_WIDTH / 2, 0, null);
        g2.scale(.10, .10);
        g2.drawImage(miniMap, 5700, 0, null); //6200

    }

    //gives width of split screen to track tank properly
    private int getSplitScreenWidth(Tank tank) {
        int x = tank.getX() - (GameConstants.GAME_SCREEN_WIDTH / 4);
        if (x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2) {
            x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2;
        }
        if (x <= 0) {
            x = 0;
        }
        return x;
    }

    //gives height for split screen to track tank properly
    private int getSplitScreenHeight(Tank tank) {
        int y = tank.getY() - (GameConstants.GAME_SCREEN_HEIGHT / 2);
        if (y > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_HEIGHT) {
            y = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_HEIGHT;
        }
        if (y <= 0) {
            y = 0;
        }
        return y;
    }

    //check for collisions
    private void checkCollision(Tank tankOne, Tank tankTwo) {
        //checks if two tanks collide and implements change
        if (tankOne.getHitBox().intersects(tankTwo.getHitBox())) {
            System.out.println("collision has occurred between two tanks");
        }


        //check if current tank is colliding with wall
        for (int currentWall = 0; currentWall < walls.size();currentWall++) {
            if (tankOne.getHitBox().intersects(walls.get(currentWall).getHitBox())){
                System.out.println("tank colliding with wall");
            }
        }

        //check if tank is colliding with powerUp
        for (int currentPowerUp = 0; currentPowerUp < powerUps.size();currentPowerUp++) {
            if (tankOne.getHitBox().intersects(powerUps.get(currentPowerUp).getHitBox())){
                System.out.println("tank colliding with powerUp");
                powerUps.remove(currentPowerUp);
            }
        }


        //checks if tank's projectiles collide with tank or walls
        for (int currentProjectile = 0; currentProjectile < tankOne.getAmmo().size(); currentProjectile++) {
            //grabs the projectile hitBox for each ammo and checks collision with other tank
            if (tankOne.getAmmo().get(currentProjectile).getHitBox().intersects(tankTwo.getHitBox())) {
                System.out.println("the ammo has collided with a tank");
                //cause damage to tank and remove projectile
                tankTwo.decreaseHealth(tankOne.getAmmo().get(currentProjectile).getDamage());
                tankOne.getAmmo().remove(currentProjectile);
                //check if game should end
                System.out.println(tankTwo.getHealth());
                continue;
            }
            //checks for currentProjectile hitting a wall
            for (int currentWall = 0; currentWall < walls.size(); currentWall++) {
                if (tankOne.getAmmo().get(currentProjectile).getHitBox().intersects(walls.get(currentWall).getHitBox())) {
                    if (walls.get(currentWall) instanceof BreakableWall) {
                        //damage wall
                        ((BreakableWall) walls.get(currentWall)).setHealth(tankOne.getAmmo().get(currentProjectile).getDamage());
                        if (((BreakableWall) walls.get(currentWall)).getHealth() == 0) {
                            walls.remove(currentWall);
                        }
                    }
                    tankOne.getAmmo().remove(currentProjectile);
                    break;
                }
            }
        }





    }
}
