package game;

import javax.swing.*;

import game.menus.EndMenu;
import game.menus.StartMenu;

import java.awt.*;
import java.awt.event.WindowEvent;

public class Launcher {

    private JPanel mainPanel;
    private JPanel startPanel;
    private Game gamePanel;
    private JPanel endPanel;
    private JFrame jFrame;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.initUIComponents();
    }

    //creates a new JFrame
    public Launcher() {
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Tank Wars Game");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this stops the VM when closed
    }

    //sets all panels for start, game, and end
    private void initUIComponents() {
        this.mainPanel = new JPanel();
        this.startPanel = new StartMenu(this);
        this.gamePanel = new Game(this);
        this.gamePanel.initializeGame(); //loads resources and gameObjects are set to initial states
        this.endPanel = new EndMenu(this);
        this.cardLayout = new CardLayout();
        this.jFrame.setResizable(false);
        this.mainPanel.setLayout(cardLayout);
        this.mainPanel.add(startPanel, "start");
        this.mainPanel.add(gamePanel, "game");
        this.jFrame.add(mainPanel);
        this.setFrame("start"); //starts the jFrame with start panel
    }

    //used for switching to different panels
    public void setFrame(String type) {
        this.jFrame.setVisible(false);
        switch (type) {
            case "start":
                this.jFrame.setSize(GameConstants.START_MENU_SCREEN_WIDTH, GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                this.jFrame.setSize(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
                break;
            case "end":
                this.jFrame.setSize(GameConstants.END_MENU_SCREEN_WIDTH, GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }
        this.cardLayout.show(mainPanel, type);
        this.jFrame.setVisible(true);
    }

    public JFrame getjFrame() {
        return jFrame;
    }

    //used for ending the game process
    public void endGame() {
        this.jFrame.dispatchEvent(new WindowEvent(this.jFrame, WindowEvent.WINDOW_CLOSING));
    }


}
