package game.GameObjects;

import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp{
    private static int speed = 7;
    private int time = 500;
    public SpeedBoost(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }


    void checkTime(Tank tank){
        if (time == 0) {
            //this resets and removes the speed boost
            tank.setR(4);
            tank.setPowerUp(null);
            time = 500;
        } else {
            time--;
        }
    }

    @Override
    public void action(Tank tank) {
        tank.setR(speed);
    }

}
