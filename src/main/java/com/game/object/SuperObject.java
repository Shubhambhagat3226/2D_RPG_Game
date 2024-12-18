package com.game.object;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected boolean collision;
    protected int worldX, worldY;

    // DRAW OBJECT ITEMS
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

            g2.drawImage(image, screenX, screenY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);
        }
    }

    // GETTER METHODS
    public int getWorldX() {return worldX;}
    public int getWorldY() {return worldY;}
    public boolean isCollision() {return collision;}
    public String getName() {return name;}
    public BufferedImage getImage() {return image;}

    // SETTER METHODS
    public void setWorldX(int worldX) {this.worldX = worldX;}
    public void setWorldY(int worldY) {this.worldY = worldY;}
}
