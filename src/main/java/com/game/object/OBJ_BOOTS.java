package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;

public class OBJ_BOOTS extends Entity {
    public OBJ_BOOTS() {
        name = ObjectName.BOOTS;
        down_1 = getImage(ImageUtility.BOOTS);
    }
}
