package com.game.entity;

import com.game.GamePanel;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;

import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = Direction.SOUTH;
        speed     = 1;

        loadImage();
        setDialogue();
    }

    private void loadImage() {
        up_1       = setImage(ImageUtility.OLD_MAN_UP_1);
        up_2       = setImage(ImageUtility.OLD_MAN_UP_2);
        down_1     = setImage(ImageUtility.OLD_MAN_DOWN_1);
        down_2     = setImage(ImageUtility.OLD_MAN_DOWN_2);
        left_1     = setImage(ImageUtility.OLD_MAN_LEFT_1);
        left_2     = setImage(ImageUtility.OLD_MAN_LEFT_2);
        right_1    = setImage(ImageUtility.OLD_MAN_RIGHT_1);
        right_2    = setImage(ImageUtility.OLD_MAN_RIGHT_2);
    }

    private void setDialogue() {
        dialogue = new String[5];

        dialogue[0] = "Hello, lad!";
    }

    @Override
    public void setAction() {
        actionCounter++;

        if (actionCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100); // pick up a number from 1 to 100

            if (i < 25) {
                direction = Direction.NORTH;
            } else if (i < 50) {
                direction = Direction.SOUTH;
            } else if (i < 75) {
                direction = Direction.WEST;
            } else {
                direction = Direction.EAST;
            }

            actionCounter=0;
        }
    }

    @Override
    public void speak() {
        gp.getUi().setCurrentDialogue(dialogue[0]);
    }
}
