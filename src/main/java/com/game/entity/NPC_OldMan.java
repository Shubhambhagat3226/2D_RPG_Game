package com.game.entity;

import com.game.GamePanel;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = Direction.SOUTH;
        speed     = 1;

        loadImage();
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
}
