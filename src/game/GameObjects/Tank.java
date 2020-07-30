package game.GameObjects;

import game.Game;
import game.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends Vehicle {

    private final static int originalHealth = 100;


    private ArrayList<Projectile> ammo; //projectiles shot
    private int health = originalHealth;
    private int lives = 3;
    private boolean isDestroyed = false;
    private PowerUp powerUp;

    public Tank(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy, angle);
        this.ammo = new ArrayList<>();
    }

    public PowerUp getPowerUp(){
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * this will call functions that update the direction the tank is facing,
     * and the positioning of the tank in the world. Will also implement changes
     * from collisions.
     **/
    @Override
    public void update(int frameCounter) {
        if (getUpPressed() && (frameCounter % 2 == 0)) {
            moveForwards();
            checkBorder();
        }
        if (getDownPressed() && (frameCounter % 2 == 0)) {
            moveBackwards();
            checkBorder();
        }
        if (getLeftPressed()) {
            rotateLeft();
        }
        if (getRightPressed()) {
            rotateRight();
        }
        if (getShootPressed() && (frameCounter % 30 == 0)) {
            this.ammo.add(new Projectile((int) (this.x+(this.getHitBox().getWidth()/2)), (int) (this.y+(this.getHitBox().getHeight()/2)), Game.bulletImage, this.vx, this.vy, this.angle));
        }
        //check if power up is done
        if (powerUp != null) { //means not done
            if (powerUp instanceof Shield) {
                ((Shield) powerUp).checkTime(this);
            }
            if (powerUp instanceof SpeedBoost) {
                ((SpeedBoost) powerUp).checkTime(this);
            }
        }
        //moves hitBox to follow change in location
        this.hitBox.setLocation(this.x, this.y);
        this.ammo.forEach(projectile -> projectile.update(frameCounter));
    }

    //makes sure tank does not go out of map's boundaries
    public void checkBorder() {
        if (this.x < 34) {
            this.x = 34;
        }
        if (this.x >= GameConstants.WORLD_WIDTH - 34 - this.objImage.getWidth()) {
            x = GameConstants.WORLD_WIDTH - 34 - this.objImage.getWidth();
        }
        if (this.y < 34) {
            this.y = 34;
        }
        if (this.y >= GameConstants.WORLD_HEIGHT - 34 - this.objImage.getHeight()) {
            this.y = GameConstants.WORLD_HEIGHT - 34 - this.objImage.getHeight();
        }
    }

    private void rotateLeft() {
        this.angle -= this.rotationSpeed;
    }

    private void rotateRight() {
        this.angle += this.rotationSpeed;
    }

    public ArrayList<Projectile> getAmmo() {
        return this.ammo;
    }

    public int getHealth() {
        return health;
    }

    void increaseHealth(int value) {
        this.health+=value;
    }

    public void decreaseHealth(int value) {
        health -= value;
        //checks if tank's health is finished and checks lives to determine if destroyed
        if (health <= 0) {
            lives--;
            health = originalHealth;
            if (lives == 0) {
                isDestroyed = true;
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    @Override
    public void drawImage(Graphics graphics) {
        super.drawImage(graphics);
        this.ammo.forEach(projectile -> projectile.drawImage(graphics));
    }
}
