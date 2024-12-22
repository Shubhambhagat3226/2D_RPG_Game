package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

public class OBJ_CHEST extends Entity {
    public OBJ_CHEST() {
        name = ObjectName.CHEST;
        down_1 = getImage(ImageUtility.CHEST);
        collisionOn = true;
    }
}
