package game.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary{
    public PowerUp(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }
}
