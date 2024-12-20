package com.game;

import com.game.constants.CommonConstant;
import com.game.object.*;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        SuperObject[] obj = gp.getObjects();

        // KEYS
        for (int i = 0; i < 3; i++) {
            obj[i] = new OBJ_KEY();
        }
        obj[0].setWorldX(CommonConstant.TILE_SIZE * 23);
        obj[0].setWorldY(CommonConstant.TILE_SIZE * 7);

        obj[1].setWorldX(CommonConstant.TILE_SIZE * 23);
        obj[1].setWorldY(CommonConstant.TILE_SIZE * 40);

        obj[2].setWorldX(CommonConstant.TILE_SIZE * 37);
        obj[2].setWorldY(CommonConstant.TILE_SIZE * 7);

        // DOORS
        for (int i = 3; i < 6; i++) {
            obj[i] = new OBJ_DOOR();
        }
        obj[3].setWorldX(CommonConstant.TILE_SIZE * 10);
        obj[3].setWorldY(CommonConstant.TILE_SIZE * 11);

        obj[4].setWorldX(CommonConstant.TILE_SIZE * 8);
        obj[4].setWorldY(CommonConstant.TILE_SIZE * 28);

        obj[5].setWorldX(CommonConstant.TILE_SIZE * 12);
        obj[5].setWorldY(CommonConstant.TILE_SIZE * 22);

        // CHEST
        obj[6] = new OBJ_CHEST();
        obj[6].setWorldX(CommonConstant.TILE_SIZE * 10);
        obj[6].setWorldY(CommonConstant.TILE_SIZE * 7);

        // BOOTS
        obj[7] = new OBJ_BOOTS();
        obj[7].setWorldX(CommonConstant.TILE_SIZE * 37);
        obj[7].setWorldY(CommonConstant.TILE_SIZE * 42);
    }
}
