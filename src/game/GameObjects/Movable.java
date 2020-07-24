package game.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {

    //used for tracking the movement of an object
    private int vx;
    private int vy;
    //way object is facing
    private float angle;


    public Movable(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }

    protected float getAngle() {
        return angle;
    }

    protected void setAngle(float angle) {
        this.angle = angle;
    }

    public abstract void update();



    @Override
    public void drawImage(Graphics graphics){
        AffineTransform rotation = AffineTransform.getTranslateInstance(getX(), getY());
        rotation.rotate(Math.toRadians(getAngle()), getObjImage().getWidth() / 2.0, getObjImage().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(getObjImage(), rotation, null);
    }


}
