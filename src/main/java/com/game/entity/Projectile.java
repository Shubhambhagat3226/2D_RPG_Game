package com.game.entity;

import com.game.GamePanel;
import com.game.constants.Direction;
import com.game.constants.Type;

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
        this.user           = user;
    }

    public void update() {

        if (user == gp.getPlayer()) {

            int monsterIndex = gp.getChecker().checkEntity(this, gp.getMonster());
            if (monsterIndex != 999) {
                gp.getPlayer().damageMonster(monsterIndex, attack);
                alive = false;
            }
        } else {

            boolean contactPlayer = gp.getChecker().checkPlayer(this);
            if (!gp.getPlayer().invincible && contactPlayer) {
                damagePlayer(attack);
                alive = false;
            }
        }

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
