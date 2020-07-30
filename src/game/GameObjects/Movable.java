package game.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {

    //used for tracking the movement of an object
    protected int vx;
    protected int vy;
    //way object is facing
    protected float angle;

    private int r = 4;

    public Movable(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }

    protected void moveForwards() {
        this.vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        this.x+=vx;
        this.y+=vy;
    }

    protected void moveBackwards() {
        this.vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        this.x-=vx;
        this.y-=vy;
    }

    protected void setR(int r) {
        this.r = r;
    }

    public abstract void update(int frameCounter);

    @Override
    public void drawImage(Graphics graphics) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        rotation.rotate(Math.toRadians(angle), this.objImage.getWidth() / 2.0, this.objImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(this.objImage, rotation, null);
    }

}
