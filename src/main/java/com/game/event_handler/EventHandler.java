package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.GameState;
import com.game.sound.SoundUtility;

public class EventHandler {
    private final GamePanel gp;
    private EventRect[][][] eventRect;

    public EventHandler(GamePanel gp) {
        this.gp   = gp;
        eventRect = new EventRect[CommonConstant.MAX_MAP][CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

        int map   = 0;
        int col   = 0;
        int row   = 0;
        while (map < CommonConstant.MAX_MAP &&
                col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {

            eventRect[map][col][row]              = new EventRect(23, 23, 2, 2);
            eventRect[map][col][row].x           += CommonConstant.TILE_SIZE * col;
            eventRect[map][col][row].y           += CommonConstant.TILE_SIZE * row;
//            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
//            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == CommonConstant.MAX_WORLD_COL) {
                row++;
                col = 0;

                if (row == CommonConstant.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {
        if (hit(0, 26, 16, Direction.EAST))  {damagePit(0, 26, 16, GameState.DIALOGUE);}
//        if (hit(0, 23, 12, Direction.ANY))  {damagePit(0, 26, 16, GameState.DIALOGUE);}
//        if (hit(0, 26, 16, Direction.EAST))  {teleport(GameState.DIALOGUE);}
        else if (hit(0, 23, 12, Direction.NORTH)) {healingPool( GameState.DIALOGUE);}

        else if (hit(0, 10, 39, Direction.ANY)) teleport(1, 12, 13);
        else if (hit(1, 12, 13, Direction.ANY)) teleport(0, 10, 39);

    }
    // CAN TOUCH
    private void eventDone(int map, int col , int row) {
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - eventRect[map][col][row].x);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - eventRect[map][col][row].y);
        int distance = Math.max(xDistance, yDistance);
        if (distance > CommonConstant.TILE_SIZE) {
            eventRect[map][col][row].eventDone = false;
        }
    }
    // CHECK IF PLAYER HIT THE EVENT
    public boolean hit(int map, int col, int row, Direction reqDirection) {
        boolean hit                      = false;

        if (map == gp.getCurrentMap()) {
            gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
            gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
//            eventRect[col][row].x           += CommonConstant.TILE_SIZE * col;
//            eventRect[col][row].y           += CommonConstant.TILE_SIZE * row;
            eventDone(map, col, row);
            if (gp.getPlayer().getSolidArea().intersects(eventRect[map][col][row]) &&
                    !eventRect[map][col][row].eventDone) {
                if (reqDirection.equals(Direction.ANY) || gp.getPlayer().getDirection().equals(reqDirection)) {
                    hit = true;
                }
            }

            gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidArea_Default_X();
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidArea_Default_Y();
//        eventRect[col][row].x           = eventRect[col][row].eventRectDefaultX;
//        eventRect[col][row].y           = eventRect[col][row].eventRectDefaultY;
        }

        return hit;
    }
    // DAMAGE OCCUR IF HIT
    public void damagePit(int map, int col, int row, GameState gameState) {
        gp.setGameState(gameState);
        gp.playSoundEffect(SoundUtility.DAMAGE_RECEIVE);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        eventRect[map][col][row].eventDone = true;
    }
    // HEALING OCCUR IF HIT
    public void healingPool( GameState gameState) {

        if (gp.getPlayer().getLife() < gp.getPlayer().getMaxLife()
                && gp.getKeyH().isEnteredPressed()) {
            gp.getPlayer().attackCanceled = true;
            gp.playSoundEffect(SoundUtility.POWER_UP);
            gp.setGameState(gameState);
            gp.getUi().setCurrentDialogue("You drink the water.\nYour life has been recovered.");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getPlayer().setMana(gp.getPlayer().getMaxMana());
            gp.getaSetter().setMonster();
        }
    }
    // TELEPORT OCCUR
    public void teleport(GameState gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("Teleport!");
        gp.getPlayer().setWorldX(CommonConstant.TILE_SIZE * 37);
        gp.getPlayer().setWorldY(CommonConstant.TILE_SIZE * 10);
    }
    public void teleport(int map, int col, int row) {
        gp.setCurrentMap(map);
        gp.getPlayer().setWorldX(CommonConstant.TILE_SIZE * col);
        gp.getPlayer().setWorldY(CommonConstant.TILE_SIZE * row);
        eventRect[map][col][row].eventDone = true;
        gp.playSoundEffect(SoundUtility.STAIRS);
    }

}
