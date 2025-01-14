package com.game.monster;

import com.game.GamePanel;
import com.game.constants.*;
import com.game.entity.Entity;
import com.game.object.OBJ_COIN;
import com.game.object.OBJ_Heart;
import com.game.object.OBJ_ManaCrystal;
import com.game.object.project.OBJ_ROCK;

import java.awt.*;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        name    = ObjectName.GREEN_SLIME;
        defaultSpeed = 1;
        speed   = defaultSpeed;
        maxLife = 5;
        life    = maxLife;
        type    = Type.MONSTER;
        attack  = 4;
        defence = 0;
        exp     = 2;
        projectile = new OBJ_ROCK(gp);

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

        if (onPath) {

            // CHECK IF IT STOP CHASING
            checkStopChasingOrNot(gp.getPlayer(), 10, 100);

            // SEARCH THE DIRECTION TO GO
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));

            // CHECK IF IT SHOOTS A PROJECT TILE
           checkShotOrNot(200, 30);

        } else {
            // CHECK IF IT START CHASING
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);

            // GET RANDOM DIRECTION
            getRandomDirection();

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
