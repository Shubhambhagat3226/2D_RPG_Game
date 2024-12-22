package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

public class OBJ_KEY extends Entity {
    public OBJ_KEY() {
        name = ObjectName.KEY;
        down_1 = getImage(ImageUtility.KEY);
    }
}
