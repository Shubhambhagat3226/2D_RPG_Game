package com.game.object;

import com.game.constants.ImageUtility;

public class Obj_Chest extends SuperObject{
    public Obj_Chest() {
        name = "Chest";
        image = ImageUtility.CHEST;
        collision = true;
    }
}
