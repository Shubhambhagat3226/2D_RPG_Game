package com.game.object;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.sound.SoundUtility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_CHEST extends Entity {

    BufferedImage openImage, closeImage;

    public OBJ_CHEST(GamePanel gp) {
        super(gp);

        type = Type.OBSTACLE;
        name = ObjectName.CHEST;
        closeImage  = getImage(ImageUtility.CHEST);
        openImage   = getImage(ImageUtility.CHEST_OPENED);
        down_1      = closeImage;
        collisionOn = true;

        solidArea           = new Rectangle(4, 16, 40, 32);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
    }
    public void setLoot(Entity loot) {
        this.loot = loot;
    }

    @Override
    public void interact() {
        gp.setGameState(GameState.DIALOGUE);
        if (!opened) {
            gp.playSoundEffect(SoundUtility.DOOR_OPEN);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.getName().getName() + "!");
            if (!canObtainItem(loot)) {
                sb.append("\n...But you cannot carry any more!");

            } else {
                sb.append("\nYou obtain the " + loot.getName().getName() + "!");
                down_1 = openImage;
                opened = true;
            }
            gp.getUi().setCurrentDialogue(sb.toString());

        } else {
            gp.getUi().setCurrentDialogue("It's empty");
        }
    }
}
