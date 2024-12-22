package com.game.object;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

import java.awt.*;

public class OBJ_DOOR extends Entity {
    public OBJ_DOOR(GamePanel gp) {
        super(gp);

        name        = ObjectName.DOOR;
        down_1      = getImage(ImageUtility.WOODEN_DOOR);
        collisionOn = true;

        solidArea           = new Rectangle(0, 16, 48, 32);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
    }
}
