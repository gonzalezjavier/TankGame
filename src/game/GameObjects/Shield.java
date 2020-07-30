package game.GameObjects;

import java.awt.image.BufferedImage;

public class Shield extends PowerUp{

    private final static int originalTime = 500;
    private int time = originalTime;
    public Shield(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    /**
     * checks if the time is up for the powerup
     * if it is then tank's power up status is reset
     * and time is reset to original time.
     * @param tank
     */
    void checkTime(Tank tank){
        if (time == 0) {
            tank.setPowerUp(null);
            time = originalTime;
        } else {
            time--;
        }
    }

    @Override
    public void action(Tank tank) {
        tank.setPowerUp(this);
    }

}
