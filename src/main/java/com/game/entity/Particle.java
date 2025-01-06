package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.awt.*;

public class Particle extends Entity{
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        this.generator = generator;
        this.color     = color;
        this.xd        = xd;
        this.yd        = yd;
        this.size      = size;
        this.speed     = speed;
        this.maxLife   = maxLife;

        life       = maxLife;
        int offSet = (CommonConstant.TILE_SIZE/2) + (size/2);
        worldX     = generator.worldX + offSet;
        worldY     = generator.worldY + offSet;
    }
    public void update() {

        life--;
        if (life < maxLife/3) {
            yd++;
        }

        worldX += xd*speed;
        worldY += yd*speed;

        if (life == 0) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().getScreenY();

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
