package com.game.monster;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;
import com.game.entity.Entity;
import com.game.constants.ObjectName;

import java.awt.*;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        name    = ObjectName.GREEN_SLIME;
        speed   = 1;
        maxLife = 20;
        life    = maxLife;
        type    = 2;
        attack  = 4;
        defence = 0;

        solidArea           = new Rectangle(3, 18, 42, 30);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
        loadImage();
    }

    public void loadImage() {
        up_1  = getImage(ImageUtility.GREEN_SLIME_1);
        up_2  = getImage(ImageUtility.GREEN_SLIME_2);
        down_1  = getImage(ImageUtility.GREEN_SLIME_1);
        down_2  = getImage(ImageUtility.GREEN_SLIME_2);
        left_1  = getImage(ImageUtility.GREEN_SLIME_1);
        left_2  = getImage(ImageUtility.GREEN_SLIME_2);
        right_1  = getImage(ImageUtility.GREEN_SLIME_1);
        right_2  = getImage(ImageUtility.GREEN_SLIME_2);

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
    public void damageReaction() {

        actionCounter = 0;
        direction     = gp.getPlayer().getDirection();
    }
}
