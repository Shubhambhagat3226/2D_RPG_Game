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

    // CHECK OBJECT COLLISION
    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;
        int i=0;
        for (Entity obj : gp.getObjects()) {
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
    public int checkEntity(Entity entity, Entity[] targets) {
        int index = 999;
        int i=0;
        for (Entity target : targets) {
            if (target != null) {
                // GET ENTITY'S SOLID AREA POSITION
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();
                // GET THE OBJECT'S SOLID AREA POSITION
                target.getSolidArea().x += target.getWorldX();
                target.getSolidArea().y += target.getWorldY();

                switch (entity.getDirection()) {
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

    public void checkPlayer(Entity entity) {

        // GET ENTITY'S SOLID AREA POSITION
        entity.getSolidArea().x += entity.getWorldX();
        entity.getSolidArea().y += entity.getWorldY();
        // GET THE OBJECT'S SOLID AREA POSITION
        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();

        switch (entity.getDirection()) {
            case NORTH -> {
                entity.getSolidArea().y -= entity.getSpeed();
                if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case SOUTH -> {
                entity.getSolidArea().y += entity.getSpeed();
                if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case WEST -> {
                entity.getSolidArea().x -= entity.getSpeed();
                if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case EAST -> {
                entity.getSolidArea().x += entity.getSpeed();
                if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
        }
        // RESTART THE DEFAULT SOLID AREA
        // FOR ENTITY
        entity.getSolidArea().x = entity.getSolidArea_Default_X();
        entity.getSolidArea().y = entity.getSolidArea_Default_Y();
        // FOR OBJECTS
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidArea_Default_X();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidArea_Default_Y();
    }
}
