package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.Direction;
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

        // USE A TEMPORAL DIRECTION WHEN IT'S BEING KNOCK-BACKED
        Direction direction = entity.getDirection();
        if (entity.isKnockBack()) {
            direction = entity.knockBackDirection;
        }

        int tile1, tile2;
        switch (direction) {
            case NORTH : {
                entity_Top_Row = (entity_Top_WorldY - entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Top_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Top_Row][entity_Right_Col];
                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case SOUTH : {
                entity_Bottom_Row = (entity_Bottom_WorldY + entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Bottom_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Bottom_Row][entity_Right_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case WEST : {
                entity_Left_Col = (entity_Left_WorldX - entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Top_Row][entity_Left_Col];
                tile2 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Bottom_Row][entity_Left_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
            case EAST : {
                entity_Right_Col = (entity_Right_WorldX + entity.getSpeed()) / CommonConstant.TILE_SIZE;
                tile1 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Top_Row][entity_Right_Col];
                tile2 = gp.getTileM().getMapTileNum()[gp.currentMap][entity_Bottom_Row][entity_Right_Col];

                if (gp.getTileM().getTiles()[tile1].isCollision()
                        || gp.getTileM().getTiles()[tile2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            }
        }
    }

    // CHECK OBJECT COLLISION
    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;
        int i=0;
        for (int j = 0; j < gp.getObjects()[1].length; j++) {

            Entity obj = gp.getObjects()[gp.currentMap][j];
            if (obj != null) {
                // GET ENTITY'S SOLID AREA POSITION
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();
                // GET THE OBJECT'S SOLID AREA POSITION
                obj.getSolidArea().x += obj.getWorldX();
                obj.getSolidArea().y += obj.getWorldY();

                switch (entity.getDirection()) {
                    case NORTH -> entity.getSolidArea().y -= entity.getSpeed();
                    case SOUTH -> entity.getSolidArea().y += entity.getSpeed();
                    case WEST  -> entity.getSolidArea().x -= entity.getSpeed();
                    case EAST  -> entity.getSolidArea().x += entity.getSpeed();
                }
                // COLLIDE
                if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                    if (obj.isCollisionOn()) {
                        entity.setCollisionOn(true);
                    }
                    if (isPlayer){
                        index = i;
                    }
                }
                // RESTART THE DEFAULT SOLID AREA
                // FOR ENTITY
                entity.getSolidArea().x = entity.getSolidArea_Default_X();
                entity.getSolidArea().y = entity.getSolidArea_Default_Y();
                // FOR OBJECTS
                obj.getSolidArea().x = obj.getSolidArea_Default_X();
                obj.getSolidArea().y = obj.getSolidArea_Default_Y();
            }
            i++;
        }

        return index;
    }

    // NPC AND MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[][] targets) {
        int index = 999;
        int i=0;

        // USE A TEMPORAL DIRECTION WHEN IT'S BEING KNOCK-BACKED
        Direction direction = entity.getDirection();
        if (entity.isKnockBack()) {
            direction = entity.knockBackDirection;
        }

        for (int j = 0; j < targets[1].length; j++) {

            Entity target = targets[gp.currentMap][j];
            if (target != null) {
                // GET ENTITY'S SOLID AREA POSITION
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();
                // GET THE OBJECT'S SOLID AREA POSITION
                target.getSolidArea().x += target.getWorldX();
                target.getSolidArea().y += target.getWorldY();

                switch (direction) {
                    case NORTH -> entity.getSolidArea().y -= entity.getSpeed();
                    case SOUTH -> entity.getSolidArea().y += entity.getSpeed();
                    case WEST -> entity.getSolidArea().x -= entity.getSpeed();
                    case EAST -> entity.getSolidArea().x += entity.getSpeed();
                }
                // COLLIDE
                if (entity.getSolidArea().intersects(target.getSolidArea())) {
                    if (target != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }
                // RESTART THE DEFAULT SOLID AREA
                // FOR ENTITY
                entity.getSolidArea().x = entity.getSolidArea_Default_X();
                entity.getSolidArea().y = entity.getSolidArea_Default_Y();
                // FOR OBJECTS
                target.getSolidArea().x = target.getSolidArea_Default_X();
                target.getSolidArea().y = target.getSolidArea_Default_Y();
            }
            i++;
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // GET ENTITY'S SOLID AREA POSITION
        entity.getSolidArea().x += entity.getWorldX();
        entity.getSolidArea().y += entity.getWorldY();
        // GET THE OBJECT'S SOLID AREA POSITION
        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();

        switch (entity.getDirection()) {
            case NORTH -> entity.getSolidArea().y -= entity.getSpeed();
            case SOUTH -> entity.getSolidArea().y += entity.getSpeed();
            case WEST  -> entity.getSolidArea().x -= entity.getSpeed();
            case EAST  -> entity.getSolidArea().x += entity.getSpeed();
        }
        // COLLIDE
        if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }
        // RESTART THE DEFAULT SOLID AREA
        // FOR ENTITY
        entity.getSolidArea().x = entity.getSolidArea_Default_X();
        entity.getSolidArea().y = entity.getSolidArea_Default_Y();
        // FOR OBJECTS
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidArea_Default_X();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidArea_Default_Y();

        return contactPlayer;
    }
}
