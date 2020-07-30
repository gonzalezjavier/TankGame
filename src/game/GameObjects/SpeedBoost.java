package game.GameObjects;

import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp{
    private final static int originalTime = 500;
    private int time = originalTime;
    private int originalSpeed;
    private int speed = 7;

    public SpeedBoost(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }


    /**
     * check if the tank's power up time is over and
     * decreases time if not. If it is over it resets the tank's
     * power up status and resets the speed to original value.
     * @param tank
     */
    void checkTime(Tank tank){
        if (time == 0) {
            tank.setR(originalSpeed);
            tank.setPowerUp(null);
            time = originalTime;
        } else {
            time--;
        }
    }

    @Override
    public void action(Tank tank) {
        originalSpeed = tank.getR();
        tank.setR(speed);
    }

}
