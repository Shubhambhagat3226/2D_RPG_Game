package com.game.object;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

public class OBJ_CHEST extends Entity {
    public OBJ_CHEST(GamePanel gp) {
        super(gp);

        name = ObjectName.CHEST;
        down_1 = getImage(ImageUtility.CHEST);
        collisionOn = true;
    }
}
