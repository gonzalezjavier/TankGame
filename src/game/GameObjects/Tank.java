package game.GameObjects;

import game.Game;

import java.awt.*;
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
            this.ammo.add(new Projectile(this.x, this.y, Game.bulletImage, this.vx, this.vy, this.angle));
        }
        this.hitBox.setLocation(this.x, this.y);
        this.ammo.forEach(projectile -> projectile.update());
    }

    private void rotateLeft() {
        this.angle -= this.rotationSpeed;
    }

    private void rotateRight() {
        this.angle += this.rotationSpeed;
    }


    @Override
    public void drawImage(Graphics graphics) {
        super.drawImage(graphics);
        this.ammo.forEach(projectile -> projectile.drawImage(graphics));
    }
}
