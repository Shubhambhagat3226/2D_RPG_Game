package com.game.object;

import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_KEY extends SuperObject{
    public OBJ_KEY() {
        name = ObjectName.KEY;
        image = getImage(ImageUtility.KEY);
    }
}
