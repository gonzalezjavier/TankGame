package game.menus;

import game.Game;
import game.GameConstants;
import game.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndMenu extends JPanel {

    private BufferedImage menuBackground;
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

        //add in exit button
        exit = new JButton("EXIT");
        exit.setFont(new Font("Impact", Font.BOLD, 24));
        exit.setBounds(GameConstants.END_MENU_SCREEN_WIDTH/2 - 75, 325, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.launcher.endGame();
        }));
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.drawImage(this.menuBackground, 0, 0, null);
        //prints the winner
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        g.drawString("Winner: "+Game.winner,GameConstants.END_MENU_SCREEN_WIDTH/2 -115,425);
    }
}
