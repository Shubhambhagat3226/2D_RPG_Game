package com.game.entity;

import com.game.constants.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int worldX, worldY;
    protected int speed;

    protected BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    protected Direction direction;

    protected int spiritCounter = 0;
    protected int spiritNum = 1;

    // INVISIBLE AREA FOR CHECK COLLISION
    protected Rectangle solidArea;
    protected boolean collisionOn;


    // GETTER METHOD ONLY
    public int getWorldX() {  return worldX;  }
    public int getWorldY() {  return worldY;  }
    public Direction getDirection() {  return direction;  }
    public int getSpeed() {  return speed;  }

    // SETTER AND GETTER
    public Rectangle getSolidArea() {return solidArea;}
    public void setSolidArea(Rectangle solidArea) {this.solidArea = solidArea;}

    public boolean isCollisionOn() {return collisionOn;}
    public void setCollisionOn(boolean collisionOn) {this.collisionOn = collisionOn;}
}
