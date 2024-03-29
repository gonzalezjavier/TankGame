package game.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    //position
    protected int x;
    protected int y;
    //image for each object
    protected BufferedImage objImage;

    //for detecting collisions
    protected Rectangle hitBox;

    public GameObject(int x, int y, BufferedImage objImage) {
        this.x = x;
        this.y = y;
        this.objImage = objImage;
        this.hitBox = new Rectangle(x, y, objImage.getWidth()+1, objImage.getHeight()+1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public abstract void drawImage(Graphics graphics);

}
