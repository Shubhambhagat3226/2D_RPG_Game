package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;
import com.game.event_handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    final private GamePanel gp;
    final private KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = Direction.SOUTH;
    }

    private void loadImage() {
        up_1 = ImageUtility.PLAYER_UP_1;
        up_2 = ImageUtility.PLAYER_UP_2;
        down_1 = ImageUtility.PLAYER_DOWN_1;
        down_2 = ImageUtility.PLAYER_DOWN_2;
        left_1 = ImageUtility.PLAYER_LEFT_1;
        left_2 = ImageUtility.PLAYER_LEFT_2;
        right_1 = ImageUtility.PLAYER_RIGHT_1;
        right_2 = ImageUtility.PLAYER_RIGHT_2;
    }

    public void update() {

        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
                direction = Direction.NORTH;
                y -= speed;
            }
            if (keyH.isDownPressed()) {
                direction = Direction.SOUTH;
                y += speed;
            }
            if (keyH.isLeftPressed()) {
                direction = Direction.WEST;
                x -= speed;
            }
            if (keyH.isRightPressed()) {
                direction = Direction.EAST;
                x += speed;
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

    }

    public void draw(Graphics2D g2) {

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

        g2.drawImage(image, x, y, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);

    }
}
