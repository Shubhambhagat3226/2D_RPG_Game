package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;

public class CollisionChecker {
    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entity_Left_WorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entity_Right_WorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entity_Top_WorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entity_Bottom_WorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entity_Left_Col = entity_Left_WorldX / CommonConstant.TILE_SIZE;
        int entity_Right_Col = entity_Right_WorldX / CommonConstant.TILE_SIZE;
        int entity_Top_Row = entity_Top_WorldY / CommonConstant.TILE_SIZE;
        int entity_Bottom_Row = entity_Bottom_WorldY / CommonConstant.TILE_SIZE;

        int tile1, tile2;
        switch (entity.getDirection()) {
            case NORTH : {
                entity_Top_Row = (entity_Top_WorldY - entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMap()[entity_Top_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMap()[entity_Top_Row][entity_Right_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case SOUTH : {
                entity_Bottom_Row = (entity_Bottom_WorldY + entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMap()[entity_Bottom_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMap()[entity_Bottom_Row][entity_Right_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case WEST : {
                entity_Left_Col = (entity_Left_WorldX - entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMap()[entity_Top_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMap()[entity_Bottom_Row][entity_Left_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case EAST : {
                entity_Right_Col = (entity_Right_WorldX + entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMap()[entity_Top_Row][entity_Right_Col];
                tile2 = gp.getTileM().getMap()[entity_Bottom_Row][entity_Right_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
        }
    }
}
