package com.game.object;

import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_DOOR extends SuperObject{
    public OBJ_DOOR() {
        name = ObjectName.DOOR;
        image = UtilityTool.setImage(ImageUtility.WOODEN_DOOR, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        collision = true;
    }
}
