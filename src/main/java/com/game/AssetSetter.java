package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;
import com.game.entity.NPC_OldMan;
import com.game.object.*;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        Entity[] obj = gp.getObjects();

        obj[0] = setEntity(new OBJ_DOOR(gp), 21, 22);
        obj[1] = setEntity(new OBJ_DOOR(gp), 23, 25);
    }

    private Entity setEntity(Entity e, int x, int y) {
        e.setWorldX(CommonConstant.TILE_SIZE * x);
        e.setWorldY(CommonConstant.TILE_SIZE * y);
        return e;
    }

    public void setNPC() {
        Entity[] npc = gp.getNpc();

        npc[0] = setEntity(new NPC_OldMan(gp), 21, 21);
        npc[1] = setEntity(new NPC_OldMan(gp), 11, 21);
        npc[2] = setEntity(new NPC_OldMan(gp), 31, 21);
        npc[3] = setEntity(new NPC_OldMan(gp), 21, 11);
        npc[4] = setEntity(new NPC_OldMan(gp), 21, 31);

    }
}
