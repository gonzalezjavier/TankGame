package game.GameObjects;

import java.awt.image.BufferedImage;

public class Projectile extends Movable {
    private int damage = 1;

    public Projectile(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy, angle);
    }

    //this will call functions that update the positioning of the projectile in the world
    @Override
    public void update(int frameCounter) {
        this.hitBox.setLocation(this.x, this.y);
        moveForwards();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
