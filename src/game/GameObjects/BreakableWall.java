package game.GameObjects;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    private int health=5;

    public BreakableWall(int x, int y, BufferedImage objImage) {
        super(x, y, objImage);
    }

    public void setHealth(int health){
        this.health -= health;
    }

    public int getHealth(){
        return health;
    }

}
