package game.GameObjects;

import java.awt.image.BufferedImage;

public class HealthBoost extends PowerUp{

    private static int boostValue = 10;

    public HealthBoost(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    @Override
    public void action(Tank tank) {
        if (tank.getHealth() <= 100 - boostValue){
            tank.increaseHealth(boostValue);
        }
        tank.setPowerUp(null); //resets to accept new power up
    }

}
