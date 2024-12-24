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

        // DEFINE SOLID AREA FOR PLAYER
        solidArea.x         = 8;
        solidArea.y         = 16;
        solidArea_Default_X = solidArea.x;
        solidArea_Default_Y = solidArea.y;
        solidArea.width     = 32;
        solidArea.height    = 32;

        // ATTACK WIDTH AND HEIGHT
        attackArea.width  = 36;
        attackArea.height = 36;

        setDefaultValues();
        loadImage();
        getPlayerAttackImage();
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
    public void loadImage() {
        width      = CommonConstant.TILE_SIZE;
        height     = CommonConstant.TILE_SIZE;
        up_1       = getImage(ImageUtility.PLAYER_UP_1);
        up_2       = getImage(ImageUtility.PLAYER_UP_2);
        down_1     = getImage(ImageUtility.PLAYER_DOWN_1);
        down_2     = getImage(ImageUtility.PLAYER_DOWN_2);
        left_1     = getImage(ImageUtility.PLAYER_LEFT_1);
        left_2     = getImage(ImageUtility.PLAYER_LEFT_2);
        right_1    = getImage(ImageUtility.PLAYER_RIGHT_1);
        right_2    = getImage(ImageUtility.PLAYER_RIGHT_2);
    }
    // LOAD PLAYER ATTACK IMAGE
    public void getPlayerAttackImage() {
        width         = CommonConstant.TILE_SIZE;
        height        = CommonConstant.TILE_SIZE * 2;
        attackUp_1    = getImage(ImageUtility.PLAYER_SWORD_UP_1);
        attackUp_2    = getImage(ImageUtility.PLAYER_SWORD_UP_2);
        attackDown_1  = getImage(ImageUtility.PLAYER_SWORD_DOWN_1);
        attackDown_2  = getImage(ImageUtility.PLAYER_SWORD_DOWN_2);

        width         = CommonConstant.TILE_SIZE * 2;
        height        = CommonConstant.TILE_SIZE;
        attackLeft_1  = getImage(ImageUtility.PLAYER_SWORD_LEFT_1);
        attackLeft_2  = getImage(ImageUtility.PLAYER_SWORD_LEFT_2);
        attackRight_1 = getImage(ImageUtility.PLAYER_SWORD_RIGHT_1);
        attackRight_2 = getImage(ImageUtility.PLAYER_SWORD_RIGHT_2);

    }

    // UPDATE ALL SETTING FOR PLAYER LIKE --
    // DIRECTION
    // TILE COLLISION HAPPEN
    // OBJECT COLLISION HAPPEN
    // IMAGES UPDATION
    public void update() {
        if (attacking) {
            attacking();
        }

        if (keyH.isUpPressed() || keyH.isDownPressed()
                || keyH.isLeftPressed() || keyH.isRightPressed()
                || keyH.isEnteredPressed()) {

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

            // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
            if (!collisionOn && !keyH.isEnteredPressed()) {
                switch (direction) {
                    case NORTH -> worldY -= speed;
                    case SOUTH -> worldY += speed;
                    case WEST -> worldX -= speed;
                    case EAST -> worldX += speed;
                }
            }
            keyH.setEnteredPressed(false);
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

            if (standCounter == 16) {
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
    // ATTACKING
    public void attacking() {
        standCounter = 0;
        spiritCounter++;
        System.out.println(spiritNum + " " + spiritCounter);
        if (spiritCounter <= 5) {
            spiritNum     = 1;

        } else if (spiritCounter <= 14) {
            spiritNum     = 2;

            // SAVE CURRENT WORLD-X,Y AND SOLID-AREA
            int currentWorldX   = worldX;
            int currentWorldY   = worldY;
            int solidAreaWidth  = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST PLAYER'S WORLD-X,Y FOR THE ATTACK-AREA
            switch (direction) {
                case NORTH -> worldY -= attackArea.height;
                case SOUTH -> worldY += attackArea.height;
                case WEST  -> worldX -= attackArea.width;
                case EAST  -> worldX += attackArea.width;
            }
            // ATTACK SOLID AREA
            solidArea.width   = attackArea.width;
            solidArea.height  = attackArea.height;
            // CHECK MONSTER COLLIDE WITH UPDATED WORLD-X,Y AND SOLID-AREA
            int monsterIndex  = gp.getChecker().checkEntity(this, gp.getMonster());
            damageMonster(monsterIndex);
            // RESET THE VALUE
            worldX           = currentWorldX;
            worldY           =  currentWorldY;
            solidArea.width  = solidAreaWidth;
            solidArea.height =  solidAreaHeight;

        } else {
            spiritNum     = 1;
            spiritCounter = 0;
            attacking     = false;
        }
    }

    // WHAT TO DO WHEN OBJECT COLLIED
    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }
    // INTERACTION WITH NPC
    public void interactNPC(int i) {
        if (keyH.isEnteredPressed()) {
            if (i != 999) {
                gp.setGameState(GameState.DIALOGUE);
                gp.getNpc()[i].speak();

            } else {
                gp.playSoundEffect(7);
                attacking = true;
            }
        }

    }
    // CONTACT MONSTER
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                gp.playSoundEffect(6);
                this.life -= 1;
                invincible = true;
            }
        }
    }
    // DAMAGE TO MONSTER
    public void damageMonster(int i) {

        if (i != 999 ) {
           if (!gp.getMonster()[i].invincible) {

               gp.playSoundEffect(5);
               gp.playSoundEffect(7);
               gp.getMonster()[i].life -= 1;
               gp.getMonster()[i].invincible = true;

               if (gp.getMonster()[i].life <= 0) {
                   gp.getMonster()[i].invincible = false;
                   gp.getMonster()[i].dying = true;
               }
           }
        }
    }

    // DRAW PLAYER IMAGE
    public void draw(Graphics2D g2) {
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image  = switch (direction) {
            case NORTH -> {
                if (!attacking) {
                    if (spiritNum == 1) yield up_1;
                    else if (spiritNum == 2) yield up_2;
                } else {
                    tempScreenY -= CommonConstant.TILE_SIZE;
                    if (spiritNum == 1) yield attackUp_1;
                    else if (spiritNum == 2) yield attackUp_2;
                }
                yield null; // or some default value
            }
            case SOUTH -> {
                if (!attacking) {
                    if (spiritNum == 1) yield down_1;
                    else if (spiritNum == 2) yield down_2;
                } else {
                    if (spiritNum == 1) yield attackDown_1;
                    else if (spiritNum == 2) yield attackDown_2;
                }
                yield null; // or some default value
            }
            case WEST -> {
                if (!attacking) {
                    if (spiritNum == 1) yield left_1;
                    else if (spiritNum == 2) yield left_2;
                } else {
                    tempScreenX -= CommonConstant.TILE_SIZE;
                    if (spiritNum == 1) yield attackLeft_1;
                    else if (spiritNum == 2) yield attackLeft_2;
                }
                yield null; // or some default value
            }
            case EAST -> {
                if (!attacking) {
                    if (spiritNum == 1) yield right_1;
                    else if (spiritNum == 2) yield right_2;
                } else {
                    if (spiritNum == 1) yield attackRight_1;
                    else if (spiritNum == 2) yield attackRight_2;
                }
                yield null; // or some default value
            }
            default -> null;
        };
        // FLINCH THE PLAYER AT INVINCIBLE
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        // DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.WHITE);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }

    // GETTER METHODS
    public int getScreenX() {  return screenX;  }
    public int getScreenY() {  return screenY;  }
}
