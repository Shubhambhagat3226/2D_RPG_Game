package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.sound.SoundUtility;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {
    public IT_DryTree(GamePanel gp) {
        super(gp);

        destructible = true;
        down_1       = getImage(ImageUtility.DRY_TREE);
        life         = 3;

    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.getCurrentWeapon().getType() == Type.AXE) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSoundEffect(SoundUtility.CUT_TREE);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp);
        tile.setWorldX(worldX);
        tile.setWorldY(worldY);
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(117, 78, 50);
        return color;
    }
    public int getParticleSize() {
        int size = 6; // 6PX
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
