package com.game.entity;

import com.game.GamePanel;
import com.game.constants.Direction;

public class Projectile extends Entity{
    protected Entity user;
    protected int useCost;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, Direction direction, boolean alive, Entity user) {
        this.worldX    = worldX;
        this.worldY    = worldY;
        this.direction = direction;
        this.alive     = alive;
        this.life      = this.maxLife;
    }

    public void update() {
        switch (direction) {
            case NORTH -> worldY -= speed;
            case SOUTH -> worldY += speed;
            case WEST  -> worldX -= speed;
            case EAST  -> worldX += speed;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spiritCounter++;
        if (spiritCounter > 12) {
            if (spiritNum == 1) {
               spiritNum = 2;
            } else {
               spiritNum = 1;

            }
        }

    }
}
