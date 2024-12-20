package com.game.object;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.ObjectName;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    protected BufferedImage image;
    protected ObjectName name;
    protected boolean collision;
    protected int worldX, worldY;
    protected Rectangle solidArea = new Rectangle(0, 0, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
    protected int solidArea_Default_X = 0;
    protected int solidArea_Default_Y = 0;

    // DRAW OBJECT ITEMS
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

            g2.drawImage(image, screenX, screenY, null);
        }
    }

    // GETTER METHODS
    public int getWorldX() {return worldX;}
    public int getWorldY() {return worldY;}
    public boolean isCollision() {return collision;}
    public ObjectName getName() {return name;}
    public BufferedImage getImage() {return image;}
    public int getSolidArea_Default_X() {return solidArea_Default_X;}
    public int getSolidArea_Default_Y() {return solidArea_Default_Y;}
    public Rectangle getSolidArea() {return solidArea;}

    // SETTER METHODS
    public void setWorldX(int worldX) {this.worldX = worldX;}
    public void setWorldY(int worldY) {this.worldY = worldY;}
}
