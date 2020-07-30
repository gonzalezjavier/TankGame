package game.menus;

import game.GameConstants;
import game.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class StartMenu extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher launcher;

    public StartMenu(Launcher launcher) {
        this.launcher = launcher;
        try {
            //sets background image from resources
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("Title.bmp"));
        } catch (Exception exception) {
            System.out.println("Error cannot read background image");
            exception.printStackTrace();
            System.exit(-1);
        }
        this.setLayout(null);

        Font labels = new Font("Impact", Font.BOLD, 24);
        //add in start and end buttons
        start = new JButton("START");
        start.setFont(labels);
        start.setBounds(GameConstants.START_MENU_SCREEN_WIDTH/2 - 75, 325, 150, 50);
        start.addActionListener((actionEvent -> {
            this.launcher.setFrame("game");
        }));

        exit = new JButton("EXIT");
        exit.setFont(labels);
        exit.setBounds(GameConstants.START_MENU_SCREEN_WIDTH/2 - 75, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.launcher.endGame();
        }));
        this.add(start);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.drawImage(this.menuBackground, 0, 0, null);
    }


}
