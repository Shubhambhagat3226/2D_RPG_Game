package com.game.object.weapon;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.sound.SoundUtility;

public class OBJ_Tent extends Entity {
    public OBJ_Tent(GamePanel gp) {
        super(gp);

        type = Type.CONSUMABLE;
        name = ObjectName.TENT;

        down_1      = getImage(ImageUtility.TENT);
        description = "[Tent]\nYou can sleep util\nnext morning";

        price     = 300;
        stackable = true;
    }

    @Override
    public boolean use(Entity entity) {

        gp.setGameState(GameState.SLEEP);
        gp.playSoundEffect(SoundUtility.SLEEP);
        gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
        gp.getPlayer().setMana(gp.getPlayer().getMaxMana());
        gp.getPlayer().getSleepingImage(down_1);
        return true;
    }
}
