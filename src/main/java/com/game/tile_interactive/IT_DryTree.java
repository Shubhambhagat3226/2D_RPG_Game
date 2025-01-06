package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.constants.ImageUtility;

public class IT_DryTree extends InteractiveTile {
    public IT_DryTree(GamePanel gp) {
        super(gp);

        destructible = true;
        down_1       = getImage(ImageUtility.DRY_TREE);

    }
}
