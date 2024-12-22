package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.GameState;

import java.awt.*;

public class EventHandler {
    private final GamePanel gp;
    private EventRect[][] eventRect;

    public EventHandler(GamePanel gp) {
        this.gp           = gp;
        eventRect = new EventRect[CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

        int col = 0;
        int row = 0;
        while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
            eventRect[col][row]                   = new EventRect(23, 23, 2, 2);
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
//        if (hit(26, 16, Direction.EAST))  {damagePit(GameState.DIALOGUE);}
        if (hit(26, 16, Direction.EAST))  {teleport(GameState.DIALOGUE);}
        if (hit(23, 12, Direction.NORTH)) {healingPool(GameState.DIALOGUE);}
    }
    // CHECK IF PLAYER HIT THE EVENT
    public boolean hit(int col, int row, Direction reqDirection) {
        boolean hit                      = false;

        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();

        if (gp.getPlayer().getSolidArea().intersects(eventRect[col][row])) {
            if (gp.getPlayer().getDirection().equals(reqDirection)
                    || reqDirection.equals(Direction.ANY)) {
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
    public void damagePit(GameState gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
    }
    // HEALING OCCUR IF HIT
    public void healingPool(GameState gameState) {
        if (gp.getKeyH().isEnteredPressed()) {
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
