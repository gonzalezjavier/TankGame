package game;

import game.GameObjects.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank tank1;
    private Tank tank2;
    private Launcher launcher;

    private static final int WORLD_WIDTH = 1324;
    private static final int WORLD_HEIGHT = 1024;

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
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tank1Image = null;
        BufferedImage tank2Image = null;
        BufferedImage breakableWallImage = null;
        BufferedImage unbreakableWallImage = null;

        try {
            //world = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Background.bmp")));
            tank1Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank1.gif")));
            tank2Image = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Tank2.gif")));
            //breakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall1.gif")));
            //unbreakableWallImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Wall2.gif")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        tank1 = new Tank(tank1SpawnX, tank1SpawnY, tank1Image, 0, 0, 0);
        tank2 = new Tank(tank2SpawnX, tank2SpawnY, tank1Image, 0, 0, 0);
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
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);
    }
}
