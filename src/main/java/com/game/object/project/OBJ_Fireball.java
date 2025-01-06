package com.game.object.project;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.entity.Entity;
import com.game.entity.Projectile;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    public OBJ_Fireball(GamePanel gp) {
        super(gp);

        name    = ObjectName.FIREBALL;
        speed   = 5;
        maxLife = 80;
        life    = maxLife;
        attack  = 5;
        useCost = 1;
        alive   = false;
        loadImage();
    }

    private void loadImage() {
        width      = CommonConstant.TILE_SIZE;
        height     = CommonConstant.TILE_SIZE;
        up_1       = getImage(ImageUtility.FIREBALL_UP_1);
        up_2       = getImage(ImageUtility.FIREBALL_UP_2);
        down_1     = getImage(ImageUtility.FIREBALL_DOWN_1);
        down_2     = getImage(ImageUtility.FIREBALL_DOWN_2);
        left_1     = getImage(ImageUtility.FIREBALL_LEFT_1);
        left_2     = getImage(ImageUtility.FIREBALL_LEFT_2);
        right_1    = getImage(ImageUtility.FIREBALL_RIGHT_1);
        right_2    = getImage(ImageUtility.FIREBALL_RIGHT_2);
    }

    public boolean haveResource(Entity user) {

        boolean haveResource = false;
        if (user.getMana() >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.setMana(user.getMana() - useCost);
    }


    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return color;
    }
    public int getParticleSize() {
        int size = 10; // 6PX
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
