package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.entity.Entity;

import java.awt.*;

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
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

            g2.drawImage(down_1, screenX, screenY, null);
        }
    }

    // GETTER
    public boolean isDestructible() {return destructible;}
}
