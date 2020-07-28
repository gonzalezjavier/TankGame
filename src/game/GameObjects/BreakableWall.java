package game.GameObjects;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    private int health=2;

    public BreakableWall(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    void setHealth(int health){
        this.health = health;
    }

    int getHealth(){
        return health;
    }

}
