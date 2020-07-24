package game.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject {

    public Stationary(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    @Override
    public void drawImage(Graphics graphics){
        AffineTransform location = AffineTransform.getTranslateInstance(getX(), getY());
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(getObjImage(), location, null);
    }
}
