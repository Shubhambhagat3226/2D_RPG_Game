package com.game.entity;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ImageUtility;
import com.game.constants.ObjectName;
import com.game.event_handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    private final GamePanel gp;
    private final KeyHandler keyH;

    private final int screenX;
    private final int screenY;

    private int hasKey;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = CommonConstant.SCREEN_WIDTH / 2 - (CommonConstant.TILE_SIZE / 2);
        screenY = CommonConstant.SCREEN_HEIGHT / 2 - (CommonConstant.TILE_SIZE / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
        solidArea.width = 31;
        solidArea.height = 32;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        worldX = CommonConstant.TILE_SIZE * 23;
        worldY = CommonConstant.TILE_SIZE * 21;
        speed = 4;
        direction = Direction.SOUTH;
    }

    // LOAD PLAYERS IMAGES
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

    // UPDATE ALL SETTING FOR PLAYER LIKE --
    // DIRECTION
    // TILE COLLISION HAPPEN
    // OBJECT COLLISION HAPPEN
    // IMAGES UPDATION
    public void update() {

        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
                direction = Direction.NORTH;
            }
            if (keyH.isDownPressed()) {
                direction = Direction.SOUTH;
            }
            if (keyH.isLeftPressed()) {
                direction = Direction.WEST;
            }
            if (keyH.isRightPressed()) {
                direction = Direction.EAST;
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.getChecker().checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.getChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case NORTH : {
                        worldY -= speed;
                        break;
                    }
                    case SOUTH : {
                        worldY += speed;
                        break;
                    }
                    case WEST : {
                        worldX -= speed;
                        break;
                    }
                    case EAST : {
                        worldX += speed;
                        break;
                    }
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

    }

    // WHAT TO DO WHEN OBJECT COLLIED
    public void pickUpObject(int i) {
        if (i != 999) {
            System.out.println(i);
            ObjectName objName = gp.getObjects()[i].getName();
            switch (objName) {
                case KEY: {
                    hasKey++;
                    gp.getObjects()[i] = null;
                    System.out.println("Key: "+hasKey);
                    break;
                }
                case DOOR: {
                    if (hasKey > 0) {
                        gp.getObjects()[i] = null;
                        hasKey--;
                        System.out.println("Key: " + hasKey);
                    }
                    break;
                }
                case BOOTS: {
                    speed += 1;
                    gp.getObjects()[i] = null;
                    break;
                }
            }
        }
    }

    // DRAW PLAYER IMAGE
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

        g2.drawImage(image, screenX, screenY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);

    }

    // GETTER METHODS
    public int getScreenX() {  return screenX;  }
    public int getScreenY() {  return screenY;  }
}
