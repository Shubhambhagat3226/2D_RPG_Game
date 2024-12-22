package com.game.object;

import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_DOOR extends SuperObject{
    public OBJ_DOOR() {
        name = ObjectName.DOOR;
        image = getImage(ImageUtility.WOODEN_DOOR);
        collision = true;
    }
}
