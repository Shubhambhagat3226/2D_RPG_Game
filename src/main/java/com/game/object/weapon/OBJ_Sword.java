package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.SuperItem;

public class OBJ_Sword extends SuperItem {
    public OBJ_Sword(GamePanel gp) {
        super(gp);

        type              = Type.SWORD;
        name              = ObjectName.NORMAL_SWORD;
        down_1            = getImage(ImageUtility.SWORD);
        attackValue       = 1;
        attackArea.width  = 36;
        attackArea.height = 36;
        description       = "[" + name.getName() + "]\nAn old sword.";
    }
}
