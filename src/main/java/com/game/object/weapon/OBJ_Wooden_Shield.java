package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_Wooden_Shield extends SuperItem{

    public OBJ_Wooden_Shield(GamePanel gp) {
        super(gp);

        name         = ObjectName.WOODEN_SHIELD;
        down_1       = getImage(ImageUtility.WOODEN_SHIELD);
        defenseValue = 1;
    }
}