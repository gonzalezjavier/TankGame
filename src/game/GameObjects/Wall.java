package game.GameObjects;

import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary{
    public Wall(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }
}
