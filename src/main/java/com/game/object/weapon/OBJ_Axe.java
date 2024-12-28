package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;

public class OBJ_Axe extends SuperItem {
    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type              = Type.AXE;
        name              = ObjectName.AXE;
        down_1            = getImage(ImageUtility.AXE);
        attackValue       = 2;
        attackArea.width  = 30;
        attackArea.height = 30;
        description       = "[" + name.getName() + "]\nA bit rusty but still can \ncut some tree.";
    }
}
