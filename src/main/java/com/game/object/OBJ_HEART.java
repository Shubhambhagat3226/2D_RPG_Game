package com.game.object;

import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_HEART extends SuperObject {
    public OBJ_HEART() {
        name   = ObjectName.HEART;
        image  = getImage(ImageUtility.HEART_FULL);
        image2 = getImage(ImageUtility.HEART_HALF);
        image3 = getImage(ImageUtility.HEART_BLANK);
    }
}
