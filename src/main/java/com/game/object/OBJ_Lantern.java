package com.game.object;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;

public class OBJ_Lantern extends Entity {
    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type        = Type.LIGHT;
        name        = ObjectName.LANTERN;
        down_1      = getImage(ImageUtility.LANTERN);
        description = "[Lantern]\nIlluminate your\nsurroundings.";
        price       = 200;
        lightRadius = 250;
    }
}
