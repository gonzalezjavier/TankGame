package game.GameObjects;

import game.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {

    //used for tracking the movement of an object
    protected int vx;
    protected int vy;
    //way object is facing
    protected float angle;

    private int r = 3;

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
        this.vx = (int) Math.round(getR() * Math.cos(Math.toRadians(getAngle())));
        this.vy = (int) Math.round(getR() * Math.sin(Math.toRadians(getAngle())));
        this.x+=vx;
        this.y+=vy;
        checkBorder();
    }

    protected void moveBackwards() {
        this.vx = (int) Math.round(getR() * Math.cos(Math.toRadians(getAngle())));
        this.vy = (int) Math.round(getR() * Math.sin(Math.toRadians(getAngle())));
        this.x-=vx;
        this.y-=vy;
        checkBorder();
    }

    private void checkBorder() {
        if (this.x < 34) {
            this.x = 34;
        }
        if (this.x >= GameConstants.WORLD_WIDTH - 34 - this.getObjImage().getWidth()) {
            x = GameConstants.WORLD_WIDTH - 34 - this.getObjImage().getWidth();
        }
        if (this.y < 34) {
            this.y = 34;
        }
        if (this.y >= GameConstants.WORLD_HEIGHT - 34 - this.getObjImage().getHeight()) {
            this.y = GameConstants.WORLD_HEIGHT - 34 - this.getObjImage().getHeight();
        }
    }

    public abstract void update();

    @Override
    public void drawImage(Graphics graphics) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        rotation.rotate(Math.toRadians(getAngle()), this.objImage.getWidth() / 2.0, this.objImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(this.objImage, rotation, null);
        g2d.setColor(Color.CYAN);
        g2d.draw(this.hitBox);
    }

}
