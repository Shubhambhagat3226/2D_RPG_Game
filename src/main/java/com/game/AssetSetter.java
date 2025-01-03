package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;
import com.game.entity.NPC_OldMan;
import com.game.monster.MON_GreenSlime;
import com.game.object.*;
import com.game.object.weapon.OBJ_Axe;
import com.game.object.weapon.OBJ_Blue_Shield;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        Entity[] obj = gp.getObjects();

        int i = 0;
        obj[i]   = setEntity(new OBJ_KEY(gp), 25, 19);
        i++;
        obj[i]   = setEntity(new OBJ_KEY(gp), 21, 19);
        i++;
        obj[i]   = setEntity(new OBJ_KEY(gp), 26, 21);
        i++;
        obj[i]   = setEntity(new OBJ_Axe(gp), 33, 21);
        i++;
        obj[i]   = setEntity(new OBJ_Blue_Shield(gp), 35, 21);
        i++;
        obj[i]   = setEntity(new OBJ_Red_Potion(gp), 22, 27);
        i++;
        obj[i]   = setEntity(new OBJ_Red_Potion(gp), 22, 29);
    }

    private Entity setEntity(Entity e, int x, int y) {
        e.setWorldX(CommonConstant.TILE_SIZE * x);
        e.setWorldY(CommonConstant.TILE_SIZE * y);
        return e;
    }

    public void setNPC() {
        Entity[] npc = gp.getNpc();

        npc[0] = setEntity(new NPC_OldMan(gp), 21, 21);

    }

    public void setMonster() {
        Entity[] monster = gp.getMonster();

        int i      = 0;
        monster[i] = setEntity(new MON_GreenSlime(gp), 23, 36);
        i++;
        monster[i] = setEntity(new MON_GreenSlime(gp), 23, 37);
        i++;
        monster[i] = setEntity(new MON_GreenSlime(gp), 24, 37);
        i++;
        monster[i] = setEntity(new MON_GreenSlime(gp), 34, 42);
        i++;
        monster[i] = setEntity(new MON_GreenSlime(gp), 38, 42);

    }


}
