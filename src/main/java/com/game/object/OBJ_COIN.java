package com.game.object;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.entity.SuperItem;
import com.game.sound.SoundUtility;

public class OBJ_COIN extends SuperItem {
    public OBJ_COIN(GamePanel gp) {
        super(gp);

        type   = Type.PICKUP_ONLY;
        name   = ObjectName.COIN;
        value  = 1;
        down_1 = getImage(ImageUtility.BRONZE_COIN);
    }

    public void use(Entity entity) {

        gp.playSoundEffect(SoundUtility.COIN);
        gp.getUi().addMessage("Coin +" + value);
        entity.setCoin(entity.getCoin() + value);
    }
}
