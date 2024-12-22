package com.game.object;

import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_CHEST extends SuperObject{
    public OBJ_CHEST() {
        name = ObjectName.CHEST;
        image = getImage(ImageUtility.CHEST);
        collision = true;
    }
}
