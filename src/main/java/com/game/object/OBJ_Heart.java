package com.game.object;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.entity.SuperItem;
import com.game.sound.SoundUtility;

public class OBJ_Heart extends SuperItem {
    public OBJ_Heart(GamePanel gp) {
        super(gp);

        type   = Type.PICKUP_ONLY;
        name   = ObjectName.HEART;
        value  = 2;
        down_1 = getImage(ImageUtility.HEART_FULL);
    }

    public boolean use(Entity entity) {

        gp.playSoundEffect(SoundUtility.COIN);
        gp.getUi().addMessage("Life +" + value);
        entity.setLife(entity.getLife() + value);
        return true;
    }
}
