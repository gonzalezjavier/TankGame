package game.GameObjects;

import java.awt.image.BufferedImage;

public abstract class Vehicle extends Movable {

    private int r = 2;
    private float rotationSpeed = 3.0f;


    public Vehicle(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy,angle);
    }

    protected int getR() {
        return r;
    }

    protected void setR(int r) {
        this.r = r;
    }

    protected float getRotationSpeed() {
        return rotationSpeed;
    }

    protected void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
