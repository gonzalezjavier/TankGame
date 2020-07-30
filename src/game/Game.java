package game;

import game.GameObjects.*;
import game.menus.EndMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel implements Runnable {

    private int tick = 0; //keeps track of how many refreshes thread has had
    private boolean isOver = false;
    public static String winner;

    //shared images
    private BufferedImage worldImage;
    public static BufferedImage bulletImage;
    private static BufferedImage healthBarImage;
    //instances of gameObjects involved in gameplay
    private Tank tank1;
    private Tank tank2;
    private Launcher launcher;
    private ArrayList<Wall> walls;
    private ArrayList<PowerUp> powerUps;

    //values for initialization
    private final int tank1SpawnX = 100;
    private final int tank1SpawnY = 100;
    private final int tank2SpawnX = GameConstants.WORLD_WIDTH-200;
    private final int tank2SpawnY = GameConstants.WORLD_HEIGHT-200;


    public Game(Launcher launcher) {
        this.launcher = launcher;
    }

    /**
     * Load all resources for game. Set all GameObjects to their
     * initial state as well.
     */
    public void initializeGame() {
        this.worldImage = new BufferedImage(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tank1Image = null;
        BufferedImage tank2Image = null;
        BufferedImage breakableWallImage;
        BufferedImage unbreakableWallImage;
        BufferedImage shieldImage;
        BufferedImage healthBoostImage;
        BufferedImage speedBoostImage;
        walls = new ArrayList<>();
        powerUps = new ArrayList<>();
        //load in all resources to proper objects
        try {
            tank1Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("blue_tank.png")));
            tank2Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("red_tank.png")));
            bulletImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Weapon.gif")));
            healthBarImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health_bar.png")));
            breakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall1.gif")));
            unbreakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall2.gif")));
            shieldImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("shield.png")));
            healthBoostImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health_boost.png")));
            speedBoostImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("speed_boost.png")));
            InputStreamReader isr = new InputStreamReader(Game.class.getClassLoader().getResourceAsStream("maps/map1.txt"));
            BufferedReader mapReader = new BufferedReader(isr);
            //read in map details
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
                            this.powerUps.add(new Shield(curCol * 32, curRow * 32, shieldImage));
                            break;
                        case "6":
                            this.powerUps.add(new HealthBoost(curCol * 32, curRow * 32, healthBoostImage));
                            break;
                        case "7":
                            this.powerUps.add(new SpeedBoost(curCol * 32, curRow * 32, speedBoostImage));
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
        tank2 = new Tank(tank2SpawnX, tank2SpawnY, tank2Image, 0, 0, -90f);
        //create the controllers for the two tanks
        VehicleController tank1Controller = new VehicleController(tank1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_SPACE);
        VehicleController tank2Controller = new VehicleController(tank2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_ENTER);
        this.launcher.getjFrame().addKeyListener(tank1Controller);
        this.launcher.getjFrame().addKeyListener(tank2Controller);
    }

    @Override
    public void run() {
        try {
            //this.resetGame();
            while (true) {
                tick++;
                //call all update functions
                checkCollision(tank1, tank2);
                checkCollision(tank2, tank1);
                tank1.update(tick);
                tank2.update(tick);
                checkGameStatus();
                //redraw game
                this.repaint();
                Thread.sleep(1000/144);
                if (isOver) { //checks if someone won the game
                    System.out.println("switching to end menu");
                    this.launcher.setFrame("end");
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void checkGameStatus() {
        if (tank1.isDestroyed()){ // p2 winner
            isOver = true;
            winner = "Player 2";
        }
        if (tank2.isDestroyed()) { //p1 winner
            isOver = true;
            winner = "Player 1";
        }
    }

    public String getWinner(){
        return winner;
    }

    //this resets gameObject values to their initial locations/values
//    private void resetGame() {
//        this.tank1.setX(tank1SpawnX);
//        this.tank1.setY(tank1SpawnY);
//        this.tank2.setX(tank2SpawnX);
//        this.tank2.setY(tank2SpawnY);
//    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        Graphics2D buffer = worldImage.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.powerUps.forEach(powerUp -> powerUp.drawImage(buffer));
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);
        //creates left and right screen and miniMap
        BufferedImage leftScreen = worldImage.getSubimage(getSplitScreenXCoordinate(tank1), getSplitScreenYCoordinate(tank1), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightScreen = worldImage.getSubimage(getSplitScreenXCoordinate(tank2), getSplitScreenYCoordinate(tank2), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = worldImage.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        //draw left and right screen and both players lives
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, GameConstants.GAME_SCREEN_WIDTH / 2, 0, null);
        g2.drawString("Player 1 Lives: "+Integer.toString(tank1.getLives()),40,40);
        g2.drawString("Player 2 Lives: "+Integer.toString(tank2.getLives()),800,40);
        //draw health bar for both tanks
        BufferedImage tankOneHealthBar = healthBarImage.getSubimage(0,0,tank1.getHealth()*4,healthBarImage.getHeight()/2);
        BufferedImage tankTwoHealthBar = healthBarImage.getSubimage(0,0,tank2.getHealth()*4,healthBarImage.getHeight()/2);
        g2.drawImage(tankOneHealthBar,40,60,null);
        g2.drawImage(tankTwoHealthBar,800,60,null);
        //draw mini map
        g2.scale(.10, .10);
        g2.drawImage(miniMap, 5700, 0, null);
    }

    /**
     * Based on the tanks location, the proper tracking is provided
     * in the horizontal plane.
     * @param tank
     * @return
     */
    private int getSplitScreenXCoordinate(Tank tank) {
        int x = tank.getX() - (GameConstants.GAME_SCREEN_WIDTH / 4);
        if (x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2) {
            x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2;
        }
        if (x <= 0) {
            x = 0;
        }
        return x;
    }

    /**
     * Based on the tanks location, the proper tracking is provided in the
     * vertical plane.
     * @param tank
     * @return y coordinate for split screen
     */
    private int getSplitScreenYCoordinate(Tank tank) {
        int y = tank.getY() - (GameConstants.GAME_SCREEN_HEIGHT / 2);
        if (y > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_HEIGHT) {
            y = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_HEIGHT;
        }
        if (y <= 0) {
            y = 0;
        }
        return y;
    }

    /**
     * The collisions of Tanks and their projectiles are handled
     * and the actions are called to update the state of the game
     * based on the collisions.
     * @param tankOne checks all its objects against other world objects
     * @param tankTwo used to compare against first tank
     */
    private void checkCollision(Tank tankOne, Tank tankTwo) {
        //checks if two tanks collide and implements change
        if (tankOne.getHitBox().intersects(tankTwo.getHitBox())) {
            //tank one is colliding from above
            if (tankOne.getY() < tankTwo.getY()) {
                tankOne.setY(tankOne.getY() - 1);
                tankTwo.setY(tankTwo.getY() + 1);
                tankOne.checkBorder();
                tankTwo.checkBorder();
            }
            //tank one is colliding from right
            if (tankOne.getX() > tankTwo.getX()) {
                tankOne.setX(tankOne.getX() + 1);
                tankTwo.setX(tankTwo.getX() - 1);
                tankOne.checkBorder();
                tankTwo.checkBorder();
            }
            //tank one is colliding from bottom
            if (tankOne.getY() > tankTwo.getY()) {
                tankOne.setY(tankOne.getY() + 1);
                tankTwo.setY(tankTwo.getY() - 1);
                tankOne.checkBorder();
                tankTwo.checkBorder();
            }
            //tank one is colliding from left side
            if (tankOne.getX() < tankTwo.getX()) {
                tankOne.setX(tankOne.getX() - 1);
                tankTwo.setX(tankTwo.getX() + 1);
                tankOne.checkBorder();
                tankTwo.checkBorder();
            }
            //tank one has same coordinates as tank two
            if (tankOne.getX() == tankTwo.getX()) {
                tankOne.setX(tankOne.getX() - 1);
                tankTwo.setX(tankTwo.getX() + 1);
                tankOne.checkBorder();
                tankTwo.checkBorder();
            }

        }
        //check if tank is colliding with wall
        for (int currentWall = 0; currentWall < walls.size(); currentWall++) {
            if (tankOne.getHitBox().intersects(walls.get(currentWall).getHitBox())) {
                //tank one is colliding from above
                if (tankOne.getY() < walls.get(currentWall).getY()) {
                    tankOne.setY(tankOne.getY() - 1);
                }
                //tank one is colliding from right
                if (tankOne.getX() > walls.get(currentWall).getX()) {
                    tankOne.setX(tankOne.getX() + 1);
                }
                //tank one is colliding from bottom
                if (tankOne.getY() > walls.get(currentWall).getY()) {
                    tankOne.setY(tankOne.getY() + 1);
                }
                //tank one is colliding from left side
                if (tankOne.getX() < walls.get(currentWall).getX()) {
                    tankOne.setX(tankOne.getX() - 1);
                }
            }
        }
        //check if tank is colliding with powerUp
        for (int currentPowerUp = 0; currentPowerUp < powerUps.size(); currentPowerUp++) {
            if (tankOne.getHitBox().intersects(powerUps.get(currentPowerUp).getHitBox())) {
                //checks if there is no active power up
                if (tankOne.getPowerUp() == null){
                    //call the specific power ups action
                    if (powerUps.get(currentPowerUp) instanceof Shield) {
                        tankOne.setPowerUp(powerUps.get(currentPowerUp));
                        powerUps.get(currentPowerUp).action(tankOne);
                    }
                    if (powerUps.get(currentPowerUp) instanceof HealthBoost) {
                        tankOne.setPowerUp(powerUps.get(currentPowerUp));
                        powerUps.get(currentPowerUp).action(tankOne);
                    }
                    if (powerUps.get(currentPowerUp) instanceof SpeedBoost) {
                        tankOne.setPowerUp(powerUps.get(currentPowerUp));
                        powerUps.get(currentPowerUp).action(tankOne);
                    }
                    powerUps.remove(currentPowerUp);
                }
                break;
            }
        }
        //checks if tank's projectiles collide with tank or walls
        for (int currentProjectile = 0; currentProjectile < tankOne.getAmmo().size(); currentProjectile++) {
            //checks if the projectile has hit other tank
            if (tankOne.getAmmo().get(currentProjectile).getHitBox().intersects(tankTwo.getHitBox())) {
                //check if tank has a shield, if yes then remove projectile but do not cause damage
                if (tankTwo.getPowerUp() instanceof Shield) {
                } else {
                    tankTwo.decreaseHealth(tankOne.getAmmo().get(currentProjectile).getDamage());
                }
                //remove projectile
                tankOne.getAmmo().remove(currentProjectile);
                continue;
            }
            //checks for currentProjectile hitting a wall
            for (int currentWall = 0; currentWall < walls.size(); currentWall++) {
                if (tankOne.getAmmo().get(currentProjectile).getHitBox().intersects(walls.get(currentWall).getHitBox())) {
                    if (walls.get(currentWall) instanceof BreakableWall) {
                        //damage wall
                        ((BreakableWall) walls.get(currentWall)).decreaseHealth(tankOne.getAmmo().get(currentProjectile).getDamage());
                        if (((BreakableWall) walls.get(currentWall)).getHealth() <= 0) {
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
