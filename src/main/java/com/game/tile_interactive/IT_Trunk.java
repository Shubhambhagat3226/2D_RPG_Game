package com.game.tile_interactive;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.Type;
import com.game.entity.Entity;

import java.awt.*;

public class IT_Trunk extends InteractiveTile {
    public IT_Trunk(GamePanel gp) {
        super(gp);

        destructible = false;
        down_1       = getImage(ImageUtility.TRUNK);

        solidArea           = new Rectangle(0,0,0,0);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
    }

}
