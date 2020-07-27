package game;

import game.GameObjects.BreakableWall;
import game.GameObjects.Tank;
import game.GameObjects.UnbreakableWall;
import game.GameObjects.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank tank1;
    private Tank tank2;
    public static BufferedImage bulletImage;
    private Launcher launcher;
    ArrayList<Wall> walls;

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
        walls = new ArrayList<>();

        try {
            //world = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Background.bmp")));
            tank1Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank1.gif")));
            tank2Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank2.gif")));
            bulletImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Weapon.gif")));
            breakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall1.gif")));
            unbreakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall2.gif")));
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
                //call all update functions
                tank1.update();
                tank2.update();
                //redraw game
                this.repaint();
                Thread.sleep(1000 / 144);
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
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);
        //creates left and right screen and minimap
        BufferedImage leftScreen = world.getSubimage(tank1.getX() - 50, tank1.getY() - 50, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightScreen = world.getSubimage(tank2.getX() - 50, tank2.getY() - 50, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        //draw
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, GameConstants.GAME_SCREEN_WIDTH / 2, 0, null);
        g2.scale(.10, .10);
        g2.drawImage(miniMap, 1000, 1000, null);
        //g2.drawImage(world, 0, 0, null);
    }
}
