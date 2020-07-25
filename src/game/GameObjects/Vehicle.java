package game.GameObjects;

import java.awt.image.BufferedImage;

public abstract class Vehicle extends Movable {
    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;

    private int r = 2;
    private float rotationSpeed = 3.0f;


    public Vehicle(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy, angle);
    }


    public void toggleUpPressed() {
        upPressed = true;
    }

    public void toggleDownPressed() {
        downPressed = true;

    }

    public void toggleLeftPressed() {
        leftPressed = true;
    }

    public void toggleRightPressed() {
        rightPressed = true;
    }

    public void unToggleUpPressed() {
        upPressed = false;
    }

    public void unToggleDownPressed() {
        downPressed = false;
    }

    public void unToggleLeftPressed() {
        leftPressed = false;
    }

    public void unToggleRightPressed() {
        rightPressed = false;
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

    protected boolean getUpPressed() {
        return upPressed;
    }

    protected boolean getDownPressed() {
        return downPressed;
    }

    protected boolean getRightPressed() {
        return rightPressed;
    }

    protected boolean getLeftPressed() {
        return leftPressed;
    }
}
