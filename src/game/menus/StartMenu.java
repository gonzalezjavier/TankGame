package game.menus;

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
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //add in start and end buttons
        start = new JButton("START");
        start.setFont(new Font("Times New Roman", Font.BOLD, 24));
        start.setBounds(150, 300, 150, 50);
        start.addActionListener((actionEvent -> {
            this.launcher.setFrame("game");
        }));

        exit = new JButton("EXIT");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Times New Roman", Font.BOLD, 24));
        exit.setBounds(150, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.launcher.endGame();
        }));
        this.add(start);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
        g.drawImage(this.menuBackground, 0, 0, null);
    }


}
