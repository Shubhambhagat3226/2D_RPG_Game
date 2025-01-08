package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;
import com.game.object.OBJ_KEY;
import com.game.object.OBJ_Red_Potion;
import com.game.object.weapon.OBJ_Axe;
import com.game.object.weapon.OBJ_Blue_Shield;
import com.game.object.weapon.OBJ_Sword;
import com.game.object.weapon.OBJ_Wooden_Shield;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_Merchant extends Entity{
    public NPC_Merchant(GamePanel gp) {
        super(gp);
        direction = Direction.SOUTH;
        speed     = 1;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;

        inventory = new ArrayList<>();

        loadImage();
        setDialogue();
    }

    private void loadImage() {
        up_1       = getImage(ImageUtility.MERCHANT_DOWN_1);
        up_2       = getImage(ImageUtility.MERCHANT_DOWN_2);
        down_1     = getImage(ImageUtility.MERCHANT_DOWN_1);
        down_2     = getImage(ImageUtility.MERCHANT_DOWN_2);
        left_1     = getImage(ImageUtility.MERCHANT_DOWN_1);
        left_2     = getImage(ImageUtility.MERCHANT_DOWN_2);
        right_1    = getImage(ImageUtility.MERCHANT_DOWN_1);
        right_2    = getImage(ImageUtility.MERCHANT_DOWN_2);
    }

    private void setDialogue() {
        dialogue = new String[4];

        dialogue[0] = "He he, so you found me. \nI have some good Stuff.\nDo you want to trade?";

    }
    public void setItems() {
        inventory.add(new OBJ_Red_Potion(gp));
        inventory.add(new OBJ_KEY(gp));
        inventory.add(new OBJ_Sword(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Wooden_Shield(gp));
        inventory.add(new OBJ_Blue_Shield(gp));
    }

    @Override
    public void speak() {
        // DO THIS CHARACTER SPECIFIC STUFF
        super.speak();
    }
}
