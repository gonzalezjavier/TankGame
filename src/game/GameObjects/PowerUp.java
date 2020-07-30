package game.GameObjects;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary{
    public PowerUp(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    public abstract void action(Tank tank);
}
