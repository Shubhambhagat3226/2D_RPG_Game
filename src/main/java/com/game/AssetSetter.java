package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;
import com.game.entity.NPC_Merchant;
import com.game.entity.NPC_OldMan;
import com.game.monster.MON_GreenSlime;
import com.game.monster.MON_Orc;
import com.game.object.*;
import com.game.object.weapon.OBJ_Axe;
import com.game.object.weapon.OBJ_Blue_Shield;
import com.game.object.weapon.OBJ_Tent;
import com.game.tile_interactive.IT_DryTree;
import com.game.tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        Entity[][] obj = gp.getObjects();

        int mapNum = 0;
        int i = 0;
        obj[mapNum][i]   = setEntity(new OBJ_Axe(gp), 33, 7);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_DOOR(gp), 14, 28);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_DOOR(gp), 12, 12);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_CHEST(gp), 30, 29);
        obj[mapNum][i].setLoot(new OBJ_KEY(gp));
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_Lantern(gp), 18, 20); i++;
        obj[mapNum][i]   = setEntity(new OBJ_Tent(gp), 19, 20); i++;
        obj[mapNum][i]   = setEntity(new OBJ_KEY(gp), 20, 10); i++;
    }

    private Entity setEntity(Entity e, int x, int y) {
        e.setWorldX(CommonConstant.TILE_SIZE * x);
        e.setWorldY(CommonConstant.TILE_SIZE * y);
        return e;
    }

    public void setNPC() {
        Entity[][] npc = gp.getNpc();
        int mapNum = 0;
        int i = 0;
        npc[mapNum][i] = setEntity(new NPC_OldMan(gp), 21, 21); i++;

        mapNum = 1;
        i = 0;
        npc[mapNum][i] = setEntity(new NPC_Merchant(gp), 12, 7); i++;

    }

    public void setMonster() {
        Entity[][] monster = gp.getMonster();
        int mapNum = 0;
        int i      = 0;
        monster[mapNum][i] = setEntity(new MON_GreenSlime(gp), 23, 36);
        i++;
        monster[mapNum][i] = setEntity(new MON_GreenSlime(gp), 23, 37);
        i++;
        monster[mapNum][i] = setEntity(new MON_GreenSlime(gp), 24, 37);
        i++;
        monster[mapNum][i] = setEntity(new MON_GreenSlime(gp), 34, 42);
        i++;
        monster[mapNum][i] = setEntity(new MON_GreenSlime(gp), 38, 42);
        i++;
        monster[mapNum][i] = setEntity(new MON_Orc(gp), 12, 33);

    }

    public void setInteractiveTile() {
        int map = 0;
        setDryTree(map, 27, 12);
        setDryTree(map, 28, 12);
        setDryTree(map, 29, 12);
        setDryTree(map, 30, 12);
        setDryTree(map, 31, 12);
        setDryTree(map, 31, 12);
        setDryTree(map, 32, 12);
        setDryTree(map, 33, 12);

        setDryTree(map, 27, 16);

        setDryTree(map, 25, 27);
        setDryTree(map, 26, 27);
        setDryTree(map, 27, 27);
        setDryTree(map, 27, 28);
        setDryTree(map, 27, 29);
        setDryTree(map, 27, 30);
        setDryTree(map, 27, 31);
        setDryTree(map, 28, 31);
        setDryTree(map, 29, 31);
        setDryTree(map, 30, 31);

        setDryTree(map, 18, 40);
        setDryTree(map, 17, 40);
        setDryTree(map, 16, 40);
        setDryTree(map, 15, 40);
        setDryTree(map, 14, 40);
        setDryTree(map, 13, 40);
        setDryTree(map, 13, 41);
        setDryTree(map, 12, 41);
        setDryTree(map, 11, 41);
        setDryTree(map, 10, 41);


    }

    private Point getXY(int x, int y) {
        return new Point(x, y);
    }

    private void setDryTree(int map, int x, int y) {
        for (int i = 0; i < gp.getiTile()[map].length; i++) {
            if (gp.getiTile()[map][i] == null) {
                gp.getiTile()[map][i] = (InteractiveTile) setEntity(new IT_DryTree(gp), x, y);
                break;
            }
        }
    }

}
