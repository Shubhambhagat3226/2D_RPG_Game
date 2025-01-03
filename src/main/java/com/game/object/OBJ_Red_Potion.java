package com.game.object;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;

public class OBJ_Red_Potion extends Entity {
    int value = 5;

    public OBJ_Red_Potion(GamePanel gp) {
        super(gp);

        type              = Type.CONSUMABLE;
        name              = ObjectName.POTION;
        down_1            = getImage(ImageUtility.RED_POTION);
        description       = "[" + name.getName() + "]\nHeals your life by " + value + ".";
    }
    public void use(Entity entity) {
        gp.setGameState(GameState.DIALOGUE);
        gp.getUi().setCurrentDialogue("You drink the " + name.getName() + "!\n" +
                "Your life has been recovered by " + value + ".");
        entity.setLife(entity.getLife() + value);
        if (entity.getLife() > entity.getMaxLife()) {
            entity.setLife(entity.getMaxLife());
        }
    }
}
