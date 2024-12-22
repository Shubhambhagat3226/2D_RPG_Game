package com.game.object;

import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_BOOTS extends SuperObject{
    public OBJ_BOOTS() {
        name = ObjectName.BOOTS;
        image = getImage(ImageUtility.BOOTS);
    }
}
