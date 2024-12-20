package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Entity;
import com.game.object.SuperObject;

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
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (SuperObject obj : gp.getObjects()) {
            if (obj != null) {
                // GET ENTITY'S SOLID AREA POSITION
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();
                // GET THE OBJECT'S SOLID AREA POSITION
                obj.getSolidArea().x += obj.getWorldX();
                obj.getSolidArea().y += obj.getWorldY();

                switch (entity.getDirection()) {
                    case NORTH: {
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            System.out.println("up collision");
                        }
                        break;
                    }
                    case SOUTH: {
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            System.out.println("down collision");
                        }
                        break;
                    }
                    case WEST: {
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            System.out.println("left collision");
                        }
                        break;
                    }
                    case EAST: {
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            System.out.println("right collision");
                        }
                        break;
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
        }

        return index;
    }
}
