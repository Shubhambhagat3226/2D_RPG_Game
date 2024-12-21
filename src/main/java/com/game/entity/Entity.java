package com.game.entity;

import com.game.GamePanel;
import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected final GamePanel gp;
    protected int worldX, worldY;
    protected int speed;

    protected BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    protected Direction direction;

    protected int spiritCounter = 0;
    protected int spiritNum = 1;

    // INVISIBLE AREA FOR CHECK COLLISION
    protected Rectangle solidArea;
    protected int solidArea_Default_X, solidArea_Default_Y;
    protected boolean collisionOn;

    public Entity(GamePanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(0, 0, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
    }

    public BufferedImage setImage(String imagePath) {
        return UtilityTool.setImage(imagePath, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
    }

    // GETTER METHOD ONLY
    public int getWorldX() {  return worldX;  }
    public int getWorldY() {  return worldY;  }
    public Direction getDirection() {  return direction;  }
    public int getSpeed() {  return speed;  }
    public int getSolidArea_Default_X() {  return solidArea_Default_X;  }
    public int getSolidArea_Default_Y() {  return solidArea_Default_Y;  }

    // SETTER AND GETTER
    public Rectangle getSolidArea() {return solidArea;}
    public void setSolidArea(Rectangle solidArea) {this.solidArea = solidArea;}

    public boolean isCollisionOn() {return collisionOn;}
    public void setCollisionOn(boolean collisionOn) {this.collisionOn = collisionOn;}
}
