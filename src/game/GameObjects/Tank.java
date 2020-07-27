package game.GameObjects;

import game.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends Vehicle {

    private ArrayList<Projectile> ammo;

    public Tank(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy, angle);
        this.hitBox = new Rectangle(x, y, objImage.getWidth(), objImage.getHeight());
        this.ammo = new ArrayList<>();
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
        if (getShootPressed()) {
            System.out.println("shooting");
            this.ammo.add(new Projectile(this.getX(), this.getY(), Game.bulletImage, this.getVx(), this.getVy(), this.getAngle()));
        }
        this.ammo.forEach(projectile -> projectile.update());
    }

    private void rotateLeft() {
        setAngle(getAngle() - getRotationSpeed());
    }

    private void rotateRight() {
        setAngle(getAngle() + getRotationSpeed());
    }


    @Override
    public void drawImage(Graphics graphics) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(getX(), getY());
        rotation.rotate(Math.toRadians(getAngle()), getObjImage().getWidth() / 2.0, getObjImage().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(getObjImage(), rotation, null);
        this.ammo.forEach(projectile -> projectile.drawImage(graphics));
    }
}
