package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.GameState;

import java.awt.*;

public class EventHandler {
    private final GamePanel gp;
    private Rectangle eventRect;
    private int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp           = gp;
        eventRect         = new Rectangle(23, 23, 2, 2);
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if (hit(26, 16, Direction.EAST)) {damagePit(GameState.DIALOGUE);}

    }
    // CHECK IF PLAYER HIT THE EVENT
    public boolean hit(int eventCol, int eventRow, Direction reqDirection) {
        boolean hit                      = false;

        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
        eventRect.x                     += CommonConstant.TILE_SIZE * eventCol;
        eventRect.y                     += CommonConstant.TILE_SIZE * eventRow;

        if (gp.getPlayer().getSolidArea().intersects(eventRect)) {
            if (gp.getPlayer().getDirection().equals(reqDirection)
                    || reqDirection.equals(Direction.ANY)) {
                hit = true;
            }
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidArea_Default_X();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidArea_Default_Y();
        eventRect.x                     = eventRectDefaultX;
        eventRect.y                     = eventRectDefaultY;

        return hit;
    }
    // DAMAGE OCCUR IF HIT
    public void damagePit(GameState gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
    }

}
