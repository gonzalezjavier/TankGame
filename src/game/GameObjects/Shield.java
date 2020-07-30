package game.GameObjects;

import java.awt.image.BufferedImage;

public class Shield extends PowerUp{

    private int time = 500;
    public Shield(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    void checkTime(Tank tank){
        if (time == 0) {
            tank.setPowerUp(null);
            time = 500;
        } else {
            time--;
        }
    }

    @Override
    public void action(Tank tank) {
        tank.setPowerUp(this);
    }

}
