package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.entity.Entity;

public class InteractiveTile extends Entity {

    boolean destructible;

    public InteractiveTile(GamePanel gp) {
        super(gp);
    }
    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible        = false;
                invincibleCounter = 0;
            }
        }
    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }
    public void playSE() {}
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    // GETTER
    public boolean isDestructible() {return destructible;}
}
