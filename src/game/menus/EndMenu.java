package game.menus;

import game.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndMenu extends JPanel {

    private BufferedImage menuBackground;
    private JButton restart;
    private JButton exit;
    private Launcher launcher;


    public EndMenu(Launcher launcher) {
        this.launcher = launcher;
        try {
            //sets background image for menu
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("Title.bmp"));
        } catch (Exception exception) {
            System.out.println("Error cannot read background image");
            exception.printStackTrace();
            System.exit(-1);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //add in restart button
        restart = new JButton("RESTART");
        restart.setFont(new Font("Times New Roman", Font.BOLD, 24));
        restart.setBounds(150, 300, 150, 50);
        restart.addActionListener((actionEvent -> {
            this.launcher.setFrame("game");
        }));

        //add in exit button
        exit = new JButton("EXIT");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Times New Roman", Font.BOLD, 24));
        exit.setBounds(150, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.launcher.endGame();
        }));

        this.add(restart);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
        g.drawImage(this.menuBackground, 0, 0, null);
    }
}
