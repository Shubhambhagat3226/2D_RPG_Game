package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.SuperItem;

public class OBJ_Wooden_Shield extends SuperItem {

    public OBJ_Wooden_Shield(GamePanel gp) {
        super(gp);

        type              = Type.SHIELD;
        name         = ObjectName.WOODEN_SHIELD;
        down_1       = getImage(ImageUtility.WOODEN_SHIELD);
        defenseValue = 1;
        description = "[" + name.getName() + "]\nMade by wood.";

        price = 35;
    }
}
