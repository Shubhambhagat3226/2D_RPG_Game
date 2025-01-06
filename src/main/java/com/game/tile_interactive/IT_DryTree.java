package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.Type;
import com.game.entity.Entity;

public class IT_DryTree extends InteractiveTile {
    public IT_DryTree(GamePanel gp) {
        super(gp);

        destructible = true;
        down_1       = getImage(ImageUtility.DRY_TREE);

    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.getCurrentWeapon().getType() == Type.AXE) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
}
