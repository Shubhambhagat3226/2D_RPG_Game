package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.event_handler.KeyHandler;

import java.awt.*;

public class Player extends Entity{
    final private GamePanel gp;
    final private KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {

        if (keyH.isUpPressed()) {
            y -= speed;
        }
        if (keyH.isDownPressed()) {
            y += speed;
        }
        if (keyH.isLeftPressed()) {
            x -= speed;
        }
        if (keyH.isRightPressed()) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);
        g2.fillRect(x,y, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);

    }
}
