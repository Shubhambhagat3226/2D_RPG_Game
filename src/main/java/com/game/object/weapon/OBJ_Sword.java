package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;

public class OBJ_Sword extends SuperItem{
    public OBJ_Sword(GamePanel gp) {
        super(gp);

        name        = ObjectName.NORMAL_SWORD;
        down_1      = getImage(ImageUtility.SWORD);
        attackValue = 4;
    }
}
