package game.GameObjects;

import java.awt.image.BufferedImage;

public class Tank extends Vehicle {

    public Tank(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy,angle);
    }


    /**
    * this will call functions that update the direction the tank is facing,
    * and the positioning of the tank in the world
    **/
    @Override
    public void update() {
        if (getUpPressed()) {
            moveForwards();
        }
        if (getDownPressed()) {
            moveBackwards();
        }
        if (getLeftPressed()) {
            rotateLeft();
        }
        if (getRightPressed()) {
            rotateRight();
        }
    }
    private void moveForwards() {
        setVx((int) Math.round(getR() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getR() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX()+getVx());
        setY(getY()+getVy());
    }
    private void moveBackwards() {
        setVx((int) Math.round(getR() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getR() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX()-getVx());
        setY(getY()-getVy());
    }
    private void rotateLeft() {
        setAngle(getAngle()-getRotationSpeed());
    }
    private void rotateRight() {
        setAngle(getAngle()+getRotationSpeed());
    }





}
