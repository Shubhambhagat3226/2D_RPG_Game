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

    }

    public void setNPC() {
        Entity[] npc = gp.getNpc();
        int point = CommonConstant.TILE_SIZE;

        npc[0] = new NPC_OldMan(gp);
        npc[0].setWorldX(point * 21);
        npc[0].setWorldY(point * 21);
    }
}
