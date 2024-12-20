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
    private int standCounter = 0;
    private boolean moving;
    private int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = CommonConstant.SCREEN_WIDTH / 2 - (CommonConstant.TILE_SIZE / 2);
        screenY = CommonConstant.SCREEN_HEIGHT / 2 - (CommonConstant.TILE_SIZE / 2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

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

        if (!moving) {
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

                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.getChecker().checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.getChecker().checkObject(this, true);
                pickUpObject(objIndex);
            }else {
                standCounter++;
                if (standCounter == 20) {
                    spiritNum = 1;
                    standCounter = 0;
                }
            }
        } else {
            // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case NORTH: {
                        worldY -= speed;
                        break;
                    }
                    case SOUTH: {
                        worldY += speed;
                        break;
                    }
                    case WEST: {
                        worldX -= speed;
                        break;
                    }
                    case EAST: {
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

            pixelCounter += speed;

            if (pixelCounter == CommonConstant.TILE_SIZE) {
                moving = false;
                pixelCounter = 0;
            }

        }
    }

    // WHAT TO DO WHEN OBJECT COLLIED
    public void pickUpObject(int i) {
        if (i != 999) {
            ObjectName objName = gp.getObjects()[i].getName();
            switch (objName) {
                case KEY: {
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.getObjects()[i] = null;
                    gp.getUi().showMessage("You got a Key!");
                    break;
                }
                case DOOR: {
                    if (hasKey > 0) {
                        gp.playSoundEffect(3);
                        gp.getObjects()[i] = null;
                        hasKey--;
                        gp.getUi().showMessage("You opened the door!");
                    } else {
                        gp.getUi().showMessage("You need a key!");
                    }
                    break;
                }
                case BOOTS: {
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.getObjects()[i] = null;
                    gp.getUi().showMessage("Speed up!");
                    break;
                }
                case CHEST: {
                    gp.getUi().setGameFinished(true);
                    gp.stopMusic();
                    gp.playSoundEffect(4);
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

        g2.drawImage(image, screenX, screenY, null);

    }

    // GETTER METHODS
    public int getScreenX() {  return screenX;  }
    public int getScreenY() {  return screenY;  }
    public int getHasKey() {  return hasKey;  }
}
