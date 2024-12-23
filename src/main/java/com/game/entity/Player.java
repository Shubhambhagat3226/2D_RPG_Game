package com.game.entity;

import com.game.GamePanel;
import com.game.constants.*;
import com.game.event_handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private final KeyHandler keyH;

    private final int screenX;
    private final int screenY;

    private int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = CommonConstant.SCREEN_WIDTH / 2 - (CommonConstant.TILE_SIZE / 2);
        screenY = CommonConstant.SCREEN_HEIGHT / 2 - (CommonConstant.TILE_SIZE / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        worldX = CommonConstant.TILE_SIZE * 23;
        worldY = CommonConstant.TILE_SIZE * 21;
        speed = 4;
        direction = Direction.SOUTH;

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    // LOAD PLAYERS IMAGES
    private void loadImage() {
        up_1       = getImage(ImageUtility.PLAYER_UP_1);
        up_2       = getImage(ImageUtility.PLAYER_UP_2);
        down_1     = getImage(ImageUtility.PLAYER_DOWN_1);
        down_2     = getImage(ImageUtility.PLAYER_DOWN_2);
        left_1     = getImage(ImageUtility.PLAYER_LEFT_1);
        left_2     = getImage(ImageUtility.PLAYER_LEFT_2);
        right_1    = getImage(ImageUtility.PLAYER_RIGHT_1);
        right_2    = getImage(ImageUtility.PLAYER_RIGHT_2);
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

                // CHECK NPC COLLISION
                int npcIndex = gp.getChecker().checkEntity(this, gp.getNpc());
                interactNPC(npcIndex);

                // CHECK MONSTER COLLISION
                int monsterIndex = gp.getChecker().checkEntity(this, gp.getMonster());
                contactMonster(monsterIndex);

                // CHECK EVENT
                gp.getEventH().checkEvent();

                keyH.setEnteredPressed(false);

                // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
                if (!collisionOn) {
                    switch (direction) {
                        case NORTH -> worldY -= speed;
                        case SOUTH -> worldY += speed;
                        case WEST -> worldX -= speed;
                        case EAST -> worldX += speed;
                    }
                }
                // TO CHANGE FROM OTHER IMAGE
                spiritCounter++;
                if (spiritCounter > 10) {
                    if (spiritNum == 1) {
                        spiritNum = 2;
                    } else {
                        spiritNum = 1;
                    }
                    spiritCounter = 0;
                }
            } else {
            standCounter++;

            if (standCounter == 14) {
                spiritNum = 1;
                standCounter=0;
            }

        }

            //  THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > CommonConstant.FPS) {
                invincible        = false;
                invincibleCounter = 0;
            }
        }

    }

    // WHAT TO DO WHEN OBJECT COLLIED
    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }
    // INTERACTION WITH NPC
    public void interactNPC(int i) {
        if (i != 999) {
            if (keyH.isEnteredPressed()) {
                gp.setGameState(GameState.DIALOGUE);
                gp.getNpc()[i].speak();
            }
        }

    }
    // CONTACT MONSTER
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                this.life -= 1;
                invincible = true;
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
        // FLINCH THE PLAYER AT INVINCIBLE
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        // DEBUG
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }

    // GETTER METHODS
    public int getScreenX() {  return screenX;  }
    public int getScreenY() {  return screenY;  }
}
