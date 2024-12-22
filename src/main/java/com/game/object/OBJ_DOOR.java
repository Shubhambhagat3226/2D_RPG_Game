package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

public class OBJ_DOOR extends Entity {
    public OBJ_DOOR() {
        name = ObjectName.DOOR;
        down_1 = getImage(ImageUtility.WOODEN_DOOR);
        collisionOn = true;
    }
}
