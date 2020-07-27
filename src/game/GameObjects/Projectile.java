package game.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Projectile extends Movable {
    private int damage = 1;

    public Projectile(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy, angle);
        this.hitBox = new Rectangle(x, y, objImage.getWidth(), objImage.getHeight());
    }

    //this will call functions that update the positioning of the projectile in the world
    @Override
    public void update() {
        moveForwards();
    }

    protected int getDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void drawImage(Graphics graphics) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(getX(), getY());
        rotation.rotate(Math.toRadians(getAngle()), getObjImage().getWidth() / 2.0, getObjImage().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(this.getObjImage(), rotation, null);
    }
}
