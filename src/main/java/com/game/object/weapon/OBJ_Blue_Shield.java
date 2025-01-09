package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.SuperItem;

public class OBJ_Blue_Shield extends SuperItem {

    public OBJ_Blue_Shield(GamePanel gp) {
        super(gp);

        type              = Type.SHIELD;
        name         = ObjectName.BLUE_SHIELD;
        down_1       = getImage(ImageUtility.BLUE_SHIELD);
        defenseValue = 2;
        description = "[" + name.getName() + "]\nA shiny blue shield.";

        price = 250;
    }
}
