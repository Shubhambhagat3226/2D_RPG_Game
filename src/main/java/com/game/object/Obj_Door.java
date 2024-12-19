package com.game.object;

import com.game.constants.ImageUtility;

public class Obj_Door extends SuperObject{
    public Obj_Door() {
        name = "Door";
        image = ImageUtility.WOODEN_DOOR;
        collision = true;
    }
}
