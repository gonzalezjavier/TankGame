package game.GameObjects;

import java.awt.image.BufferedImage;

public class Tank extends Vehicle {


    public Tank(int x, int y, BufferedImage objImage, int vx, int vy, float angle) {
        super(x, y, objImage, vx, vy,angle);
    }

    /**
    * this will call functions that update the direction the tank is facing,
    * and the positioning of the tank in the world
    **/
    @Override
    public void update() {

    }



}
