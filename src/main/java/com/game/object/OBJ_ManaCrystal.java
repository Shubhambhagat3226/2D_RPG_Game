package com.game.object;

import com.game.GamePanel;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.entity.SuperItem;
import com.game.sound.SoundUtility;

public class OBJ_ManaCrystal extends SuperItem {
    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);

        type   = Type.PICKUP_ONLY;
        name   = ObjectName.HEART;
        value  = 1;
        down_1 = getImage(ImageUtility.MANA_FULL);
    }

    public boolean use(Entity entity) {

        gp.playSoundEffect(SoundUtility.COIN);
        gp.getUi().addMessage("Mana +" + value);
        entity.setMana(entity.getMana() + value);

        return true;
    }
}
