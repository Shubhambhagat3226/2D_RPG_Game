package com.game.entity;

import com.game.GamePanel;
import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ObjectName;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected GamePanel gp;
    protected int worldX, worldY;
    protected int speed;

    protected int width, height;
    protected BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    protected Direction direction = Direction.SOUTH;

    protected int spiritCounter = 0;
    protected int spiritNum     = 1;
    protected int actionCounter = 0;

    protected boolean invincible;
    protected int invincibleCounter = 0;

    protected String[] dialogue;
    protected int dialogueIndex = 0;

    // INVISIBLE AREA FOR CHECK COLLISION
    protected Rectangle solidArea;
    protected int solidArea_Default_X, solidArea_Default_Y;
    protected boolean collisionOn;

    // CHARACTER STATUS
    protected int maxLife;
    protected int life;
    protected int type; // 0-player 1-npc 2-monster

    // OBJECT THING
    protected ObjectName name;

    public Entity(GamePanel gp) {
        this.gp   = gp;
        solidArea = new Rectangle(0, 0, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        width     = CommonConstant.TILE_SIZE;
        height    = CommonConstant.TILE_SIZE;
    }

    // SPEAK
    public void speak() {
        if (dialogueIndex == dialogue.length-1 || dialogue[dialogueIndex] == null) dialogueIndex=0;
        gp.getUi().setCurrentDialogue(dialogue[dialogueIndex]);
        dialogueIndex++;

        switch (gp.getPlayer().direction) {
            case NORTH -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.NORTH;
            case WEST  -> direction = Direction.EAST;
            case EAST  -> direction = Direction.WEST;
        }
    }

    // SET-ACTION DEFINE IN SUN-CLASS
    public void setAction() {}

    // UPDATE METHOD DEFAULT FOR ENTITY
    public void update() {
        setAction();

        collisionOn = false;
        gp.getChecker().checkTile(this);
        gp.getChecker().checkObject(this, false);
        gp.getChecker().checkEntity(this, gp.getNpc());
        gp.getChecker().checkEntity(this, gp.getMonster());
        boolean contactPlayer = gp.getChecker().checkPlayer(this);

        if (this.type == 2 && contactPlayer) {
           if (!gp.getPlayer().invincible) {
               // GIVE DAMAGE
               gp.getPlayer().life      -= 1;
               gp.getPlayer().invincible = true;
           }
        }
        // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
        if (!collisionOn) {
            switch (direction) {
                case NORTH -> worldY -= speed;
                case SOUTH -> worldY += speed;
                case WEST  -> worldX -= speed;
                case EAST  -> worldX += speed;
            }
        }

        // TO CHANGE FROM OTHER IMAGE
        spiritCounter++;
        if (spiritCounter > 12) {
            if (spiritNum == 1) {
                spiritNum = 2;
            } else {
                spiritNum = 1;
            }
            spiritCounter = 0;
        }
    }
    // DRAW METHOD BY DEFAULT
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

            BufferedImage image  = switch (direction) {
                case NORTH -> {
                    if (spiritNum == 1) {
                        yield  up_1;
                    } else if (spiritNum == 2) {
                        yield  up_2;
                    }  else yield null; // or some default value
                }
                case SOUTH -> {
                    if (spiritNum == 1) {
                        yield  down_1;
                    }
                    if (spiritNum == 2) {
                        yield  down_2;
                    } else yield null; // or some default value
                }
                case WEST -> {
                    if (spiritNum == 1) {
                        yield  left_1;
                    }
                    if (spiritNum == 2) {
                        yield  left_2;
                    } else yield null; // or some default value
                }
                case EAST -> {
                    if (spiritNum == 1) {
                        yield  right_1;
                    }
                    if (spiritNum == 2) {
                        yield  right_2;
                    } else yield null; // or some default value
                }
                default -> null;
            };
            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public BufferedImage getImage(String imagePath) {
        return UtilityTool.setImage(imagePath, width, height);
    }

    // GETTER METHOD ONLY
    public int getWorldX() {  return worldX;  }
    public int getWorldY() {  return worldY;  }
    public Direction getDirection() {  return direction;  }
    public int getSpeed() {  return speed;  }
    public int getSolidArea_Default_X() {  return solidArea_Default_X;  }
    public int getSolidArea_Default_Y() {  return solidArea_Default_Y;  }
    public ObjectName getName() {return name;}

    // SETTER AND GETTER
    public Rectangle getSolidArea() {return solidArea;}
    public void setSolidArea(Rectangle solidArea) {this.solidArea = solidArea;}

    public boolean isCollisionOn() {return collisionOn;}
    public void setCollisionOn(boolean collisionOn) {this.collisionOn = collisionOn;}

    public int getMaxLife() {return maxLife;}
    public void setMaxLife(int maxLife) {this.maxLife = maxLife;}

    public int getLife() {return life;}
    public void setLife(int life) {this.life = life;}

    public void setWorldX(int worldX) {this.worldX = worldX;}
    public void setWorldY(int worldY) {this.worldY = worldY;}
}
