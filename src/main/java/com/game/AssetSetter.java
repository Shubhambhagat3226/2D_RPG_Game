package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;
import com.game.entity.NPC_Merchant;
import com.game.entity.NPC_OldMan;
import com.game.monster.MON_GreenSlime;
import com.game.object.*;
import com.game.object.weapon.OBJ_Axe;
import com.game.object.weapon.OBJ_Blue_Shield;
import com.game.tile_interactive.IT_DryTree;
import com.game.tile_interactive.InteractiveTile;

import java.awt.*;

public class AssetSetter {
    private final GamePanel gp;

    public AssetSetter(GamePanel gp) {  this.gp = gp;  }

    public void setObject() {
        Entity[][] obj = gp.getObjects();

        int mapNum = 0;
        int i = 0;
        obj[mapNum][i]   = setEntity(new OBJ_COIN(gp), 25, 19);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_COIN(gp), 21, 19);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_COIN(gp), 26, 21);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_Axe(gp), 20, 12);
        i++;
        obj[mapNum][i]   = setEntity(new OBJ_Blue_Shield(gp), 35, 21);
        i++;
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

    }

    public void setInteractiveTile() {
//
//        Point[] points = {new Point(27, 12), new Point(28, 12),new Point(29, 12),
//                new Point(30, 12),new Point(31, 12), new Point(32, 12),
//                new Point(33, 12), new Point(27, 16),new Point(18, 40),
//                new Point(17, 40),new Point(16, 40), new Point(15, 40),
//                new Point(14, 40), new Point(13, 40),new Point(13, 41),
//                new Point(12, 41),new Point(11, 41), new Point(10, 41),
//                new Point(10, 40), new Point(29, 40),new Point(30, 40),
//                new Point(36, 30),new Point(33, 7), new Point(30, 21),
//                new Point(31, 21), new Point(32, 21)};
//        int mapNum = 0;
//        int i = 0;
//        for (Point point : points) {
//            gp.getiTile()[mapNum][i] = (InteractiveTile) setEntity(new IT_DryTree(gp), point.x, point.y);
//            i++;
//        }

    }

}
