package game.GameObjects;

import game.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {

    //used for tracking the movement of an object
    private int vx;
    private int vy;
    //way object is facing
    private float angle;

    private int r = 3;
    Rectangle hitBox;


    public Movable(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }

    protected float getAngle() {
        return angle;
    }

    protected int getVx() {
        return vx;
    }

    protected void setVx(int vx) {
        this.vx = vx;
    }

    protected int getVy() {
        return vy;
    }

    protected void setVy(int vy) {
        this.vy = vy;
    }

    protected void setAngle(float angle) {
        this.angle = angle;
    }

    protected int getR() {
        return r;
    }

    protected void moveForwards() {
        setVx((int) Math.round(getR() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getR() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX() + getVx());
        setY(getY() + getVy());
    }

    protected void moveBackwards() {
        setVx((int) Math.round(getR() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getR() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX() - getVx());
        setY(getY() - getVy());
    }

    private void checkBorder() {
    }

    public abstract void update();


}
