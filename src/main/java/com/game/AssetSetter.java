package com.game;

import com.game.constants.CommonConstant;
import com.game.object.Obj_Chest;
import com.game.object.Obj_Door;
import com.game.object.Obj_Key;
import com.game.object.SuperObject;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        SuperObject[] obj = gp.getObjects();

        // KEYS
        for (int i = 0; i < 3; i++) {
            obj[i] = new Obj_Key();
        }
        obj[0].setWorldX(CommonConstant.TILE_SIZE * 23);
        obj[0].setWorldY(CommonConstant.TILE_SIZE * 7);

        obj[1].setWorldX(CommonConstant.TILE_SIZE * 23);
        obj[1].setWorldY(CommonConstant.TILE_SIZE * 40);

        obj[2].setWorldX(CommonConstant.TILE_SIZE * 37);
        obj[2].setWorldY(CommonConstant.TILE_SIZE * 7);

        // DOORS
        for (int i = 3; i < 6; i++) {
            obj[i] = new Obj_Door();
        }
        obj[3].setWorldX(CommonConstant.TILE_SIZE * 10);
        obj[3].setWorldY(CommonConstant.TILE_SIZE * 11);

        obj[4].setWorldX(CommonConstant.TILE_SIZE * 8);
        obj[4].setWorldY(CommonConstant.TILE_SIZE * 28);

        obj[5].setWorldX(CommonConstant.TILE_SIZE * 12);
        obj[5].setWorldY(CommonConstant.TILE_SIZE * 22);

        // CHEST
        obj[6] = new Obj_Chest();
        obj[6].setWorldX(CommonConstant.TILE_SIZE * 10);
        obj[6].setWorldY(CommonConstant.TILE_SIZE * 7);
    }
}
