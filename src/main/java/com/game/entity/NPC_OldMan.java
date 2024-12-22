package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
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
        dialogue = new String[4];

        dialogue[0] = "Hello, lad!";
        dialogue[1] = "So you,ve come to this island to \nfind the treasure?";
        dialogue[2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogue[3] = "Well, good luck on you.";
    }

    @Override
    public void setAction() {
        actionCounter++;

        if (actionCounter == CommonConstant.FPS*3) {
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
        // DO THIS CHARACTER SPECIFIC STUFF
        super.speak();
    }
}
