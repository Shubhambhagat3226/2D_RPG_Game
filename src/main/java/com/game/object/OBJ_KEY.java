package com.game.object;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.sound.SoundUtility;

public class OBJ_KEY extends Entity {
    public OBJ_KEY(GamePanel gp) {
        super(gp);

        type = Type.CONSUMABLE;
        name = ObjectName.KEY;
        down_1 = getImage(ImageUtility.KEY);
        description = "[" + name.getName() + "]\nIt opens a door.";
        price = 100;
        stackable = true;
    }
    public boolean use(Entity entity) {
        gp.setGameState(GameState.DIALOGUE);
        int objectIndex = getDetected(entity, gp.getObjects(), ObjectName.DOOR);

        if (objectIndex != 999) {
            gp.getUi().setCurrentDialogue("You use the " + name.getName() + " and open the door.");
            gp.playSoundEffect(SoundUtility.DOOR_OPEN);
            gp.getObjects()[gp.getCurrentMap()][objectIndex] = null;
            return true;
        } else {
            gp.getUi().setCurrentDialogue("What are you doing?");
            return false;
        }
    }
}
