package com.game.monster;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.entity.Entity;
import com.game.object.OBJ_COIN;
import com.game.object.OBJ_Heart;
import com.game.object.OBJ_ManaCrystal;

import java.awt.*;
import java.util.Random;

public class MON_Orc extends Entity {
    public MON_Orc(GamePanel gp) {
        super(gp);
        name = ObjectName.ORC;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        type = Type.MONSTER;
        attack = 8;
        defence = 2;
        exp = 10;
        knockBackPower = 5;

        solidArea = new Rectangle(4, 4, 40, 44);
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;

        attackArea.width  = 48;
        attackArea.height = 48;
        loadImage();
        getAttackImage();
    }

    public void loadImage() {
        up_1 = getImage(ImageUtility.ORC_UP_1);
        up_2 = getImage(ImageUtility.ORC_UP_2);
        down_1 = getImage(ImageUtility.ORC_DOWN_1);
        down_2 = getImage(ImageUtility.ORC_DOWN_2);
        left_1 = getImage(ImageUtility.ORC_LEFT_1);
        left_2 = getImage(ImageUtility.ORC_LEFT_2);
        right_1 = getImage(ImageUtility.ORC_RIGHT_1);
        right_2 = getImage(ImageUtility.ORC_RIGHT_2);

    }
    public void getAttackImage() {
        width         = CommonConstant.TILE_SIZE;
        height        = CommonConstant.TILE_SIZE * 2;
        attackUp_1 = getImage(ImageUtility.ORC_ATTACK_UP_1);
        attackUp_2 = getImage(ImageUtility.ORC_ATTACK_UP_2);
        attackDown_1 = getImage(ImageUtility.ORC_ATTACK_DOWN_1);
        attackDown_2 = getImage(ImageUtility.ORC_ATTACK_DOWN_2);

        width = CommonConstant.TILE_SIZE * 2;
        height = CommonConstant.TILE_SIZE;
        attackLeft_1 = getImage(ImageUtility.ORC_ATTACK_LEFT_1);
        attackLeft_2 = getImage(ImageUtility.ORC_ATTACK_LEFT_2);
        attackRight_1 = getImage(ImageUtility.ORC_ATTACK_RIGHT_1);
        attackRight_2 = getImage(ImageUtility.ORC_ATTACK_RIGHT_2);
    }

    @Override
    public void setAction() {

        if (onPath) {

            // CHECK IF IT STOP CHASING
            checkStopChasingOrNot(gp.getPlayer(), 10, 100);

            // SEARCH THE DIRECTION TO GO
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));

        } else {
            // CHECK IF IT START CHASING
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);

            // GET RANDOM DIRECTION
            getRandomDirection();

        }

        if (!attacking) {
            checkAttackOrNot(30, CommonConstant.TILE_SIZE*4, CommonConstant.TILE_SIZE);
        }

    }

    @Override
    public void damageReaction() {

        actionCounter = 0;
//        direction     = gp.getPlayer().getDirection();
        onPath = true;
    }

    @Override
    public void checkDrop() {

        int i = new Random().nextInt(100);

        // SET DROP
        if (i < 50) dropItem(new OBJ_COIN(gp));
        else if (i < 75) dropItem(new OBJ_Heart(gp));
        else dropItem(new OBJ_ManaCrystal(gp));

    }
}
