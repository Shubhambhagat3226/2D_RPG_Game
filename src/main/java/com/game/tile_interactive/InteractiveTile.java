package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.entity.Entity;

public class InteractiveTile extends Entity {

    boolean destructible;

    public InteractiveTile(GamePanel gp) {
        super(gp);
    }
    public void update() {}
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    // GETTER
    public boolean isDestructible() {return destructible;}
}
