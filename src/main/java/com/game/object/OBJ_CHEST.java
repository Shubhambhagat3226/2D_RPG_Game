package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_CHEST extends SuperObject{
    public OBJ_CHEST() {
        name = ObjectName.CHEST;
        image = ImageUtility.CHEST;
        collision = true;
    }
}
