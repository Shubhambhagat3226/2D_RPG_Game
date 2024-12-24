package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.GameState;

public class EventHandler {
    private final GamePanel gp;
    private EventRect[][] eventRect;

    public EventHandler(GamePanel gp) {
        this.gp   = gp;
        eventRect = new EventRect[CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

        int col   = 0;
        int row   = 0;
        while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
            eventRect[col][row]              = new EventRect(23, 23, 2, 2);
            eventRect[col][row].x           += CommonConstant.TILE_SIZE * col;
            eventRect[col][row].y           += CommonConstant.TILE_SIZE * row;
//            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
//            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == CommonConstant.MAX_WORLD_COL) {
                row++;
                col = 0;
            }
        }

    }

    public void checkEvent() {
        if (hit(26, 16, Direction.EAST))  {damagePit(26, 16, GameState.DIALOGUE);}
//        if (hit(23, 12, Direction.ANY))  {damagePit(23, 12, GameState.DIALOGUE);}
//        if (hit(26, 16, Direction.EAST))  {teleport(GameState.DIALOGUE);}
        if (hit(23, 12, Direction.NORTH)) {healingPool(23, 12, GameState.DIALOGUE);}
    }
    // CAN TOUCH
    private void eventDone(int col , int row) {
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - eventRect[col][row].x);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - eventRect[col][row].y);
        int distance = Math.max(xDistance, yDistance);
        if (distance > CommonConstant.TILE_SIZE) {
            eventRect[col][row].eventDone = false;
        }
    }
    // CHECK IF PLAYER HIT THE EVENT
    public boolean hit(int col, int row, Direction reqDirection) {
        boolean hit                      = false;

        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
//            eventRect[col][row].x           += CommonConstant.TILE_SIZE * col;
//            eventRect[col][row].y           += CommonConstant.TILE_SIZE * row;
        eventDone(col,row);
        if (gp.getPlayer().getSolidArea().intersects(eventRect[col][row]) &&
            !eventRect[col][row].eventDone) {
            if (reqDirection.equals(Direction.ANY) || gp.getPlayer().getDirection().equals(reqDirection)) {
                hit = true;
            }
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidArea_Default_X();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidArea_Default_Y();
//        eventRect[col][row].x           = eventRect[col][row].eventRectDefaultX;
//        eventRect[col][row].y           = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    // DAMAGE OCCUR IF HIT
    public void damagePit(int col, int row, GameState gameState) {
        gp.setGameState(gameState);
        gp.playSoundEffect(6);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        eventRect[col][row].eventDone = true;
    }
    // HEALING OCCUR IF HIT
    public void healingPool(int col, int row, GameState gameState) {

        if (gp.getPlayer().getLife() < gp.getPlayer().getMaxLife()
                && gp.getKeyH().isEnteredPressed()) {
            gp.getPlayer().attackCanceled = true;
            gp.playSoundEffect(2);
            gp.setGameState(gameState);
            gp.getUi().setCurrentDialogue("You drink the water.\nYour life has been recovered.");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
        }
    }
    // TELEPORT OCCUR
    public void teleport(GameState gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("Teleport!");
        gp.getPlayer().setWorldX(CommonConstant.TILE_SIZE * 37);
        gp.getPlayer().setWorldY(CommonConstant.TILE_SIZE * 10);
    }

}
