package game.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    //position
    private int x;
    private int y;
    //image for each object
    private BufferedImage objImage;

    public GameObject(int x, int y, BufferedImage objImage) {
        this.x = x;
        this.y = y;
        this.objImage = objImage;
    }

    protected BufferedImage getObjImage() {
        return objImage;
    }

    protected void setObjImage(BufferedImage objImage) {
        this.objImage = objImage;
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

    public abstract void drawImage(Graphics graphics);

}
