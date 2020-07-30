package game.GameObjects;

import game.VehicleController;

import java.awt.image.BufferedImage;

public abstract class Vehicle extends Movable {
    //provides status for buttons
    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean shootPressed;
    //used for controlling rotational speed
    float rotationSpeed = 3.0f;


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

    public void toggleShootPressed() {
        shootPressed = true;
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

    public void unToggleShootPressed() {
        shootPressed = false;
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

    protected boolean getShootPressed() {
        return shootPressed;
    }
}
