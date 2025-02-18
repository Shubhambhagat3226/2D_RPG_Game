package com.game.entity;

import com.game.GamePanel;
import com.game.constants.*;
import com.game.event_handler.KeyHandler;
import com.game.object.project.OBJ_Fireball;
import com.game.object.weapon.OBJ_Sword;
import com.game.object.weapon.OBJ_Wooden_Shield;
import com.game.sound.SoundUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    private final KeyHandler keyH;

    private final int screenX;
    private final int screenY;

    private int standCounter = 0;
    public boolean attackCanceled;

    public boolean lightUpdated = false;

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

        inventory = new ArrayList<>();

        setDefaultValues();
    }

    public void setDefaultValues() {

        if (gp.getCurrentMap() == 0) {
            worldX = CommonConstant.TILE_SIZE * 23;
            worldY = CommonConstant.TILE_SIZE * 21;
        } else if (gp.getCurrentMap() == 1) {
            worldX = CommonConstant.TILE_SIZE * 12;
            worldY = CommonConstant.TILE_SIZE * 13;
        }
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = Direction.SOUTH;

        // PLAYER STATUS
        level         = 1;
        maxLife       = 6;
        life          = maxLife;
        maxMana       = 4;
        mana          = maxMana;
        strength      = 1;   // MORE STRENGTH, MORE DAMAGE
        dexterity     = 1;   // MORE DEX, MORE DEFENCE
        exp           = 0;
        nextLevelExp  = 5;
        coin          = 0;
        currentWeapon = new OBJ_Sword(gp);
        currentShield = new OBJ_Wooden_Shield(gp);
        projectile    = new OBJ_Fireball(gp);
        currentLight  = null;
        attack        = getAttack();  // TOTAL ATTACK CALCULATE BY STRENGTH AND WEAPON ATTACK-VALUE
        defence       = getDefense(); // TOTAL DEFENCE CALCULATE BY DEX AND WEAPON DEFENCE-VALUE


        loadImage();
        getGuardImage();
        getAttackImage();
        setItems();
    }

    public void setDefaultPosition() {
        worldX = CommonConstant.TILE_SIZE * 23;
        worldY = CommonConstant.TILE_SIZE * 21;
        direction = Direction.SOUTH;
    }
    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
        transparent = false;
        attacking = false;
        knockBack = false;
        lightUpdated = true;
    }
    // SET ITEMS
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    public int getAttack() {
        attackArea    = currentWeapon.attackArea;
        return attack = strength * currentWeapon.getAttackValue();
    }
    public int getDefense() {
        return defence = dexterity * currentShield.getDefenseValue();
    }
    public int getCurrentWeaponSlot() {
        int currentSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentSlot = i;
                break;
            }
        }
        return currentSlot;
    }
    public int getCurrentShieldSlot() {
        int currentSlot = 1;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentSlot = i;
                break;
            }
        }
        return currentSlot;
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
    public void getSleepingImage(BufferedImage image) {

        up_1       = image;
        up_2       = image;
        down_1     = image;
        down_2     = image;
        left_1     = image;
        left_2     = image;
        right_2    = image;
    }
    // LOAD PLAYER ATTACK IMAGE
    public void getAttackImage() {
        width         = CommonConstant.TILE_SIZE;
        height        = CommonConstant.TILE_SIZE * 2;
        if (currentWeapon.type == Type.SWORD) {
            attackUp_1 = getImage(ImageUtility.PLAYER_SWORD_UP_1);
            attackUp_2 = getImage(ImageUtility.PLAYER_SWORD_UP_2);
            attackDown_1 = getImage(ImageUtility.PLAYER_SWORD_DOWN_1);
            attackDown_2 = getImage(ImageUtility.PLAYER_SWORD_DOWN_2);

            width = CommonConstant.TILE_SIZE * 2;
            height = CommonConstant.TILE_SIZE;
            attackLeft_1 = getImage(ImageUtility.PLAYER_SWORD_LEFT_1);
            attackLeft_2 = getImage(ImageUtility.PLAYER_SWORD_LEFT_2);
            attackRight_1 = getImage(ImageUtility.PLAYER_SWORD_RIGHT_1);
            attackRight_2 = getImage(ImageUtility.PLAYER_SWORD_RIGHT_2);
        }
        if (currentWeapon.type == Type.AXE) {
            attackUp_1 = getImage(ImageUtility.PLAYER_AXE_UP_1);
            attackUp_2 = getImage(ImageUtility.PLAYER_AXE_UP_2);
            attackDown_1 = getImage(ImageUtility.PLAYER_AXE_DOWN_1);
            attackDown_2 = getImage(ImageUtility.PLAYER_AXE_DOWN_2);

            width = CommonConstant.TILE_SIZE * 2;
            height = CommonConstant.TILE_SIZE;
            attackLeft_1 = getImage(ImageUtility.PLAYER_AXE_LEFT_1);
            attackLeft_2 = getImage(ImageUtility.PLAYER_AXE_LEFT_2);
            attackRight_1 = getImage(ImageUtility.PLAYER_AXE_RIGHT_1);
            attackRight_2 = getImage(ImageUtility.PLAYER_AXE_RIGHT_2);
        }

    }
    // GUARD IMAGE
    public void getGuardImage() {
        guardUp = getImage(ImageUtility.PLAYER_GUARD_UP);
        guardDown = getImage(ImageUtility.PLAYER_GUARD_DOWN);
        guardLeft = getImage(ImageUtility.PLAYER_GUARD_LEFT);
        guardRight = getImage(ImageUtility.PLAYER_GUARD_RIGHT);
    }

    // UPDATE ALL SETTING FOR PLAYER LIKE --
    // DIRECTION
    // TILE COLLISION HAPPEN
    // OBJECT COLLISION HAPPEN
    // IMAGES UPDATION
    public void update() {
        if (knockBack) {

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.getChecker().checkTile(this);
            // CHECK OBJECT COLLISION
            gp.getChecker().checkObject(this, true);
            // CHECK NPC COLLISION
            gp.getChecker().checkEntity(this, gp.getNpc());
            // CHECK MONSTER COLLISION
            gp.getChecker().checkEntity(this, gp.getMonster());
            // CHECK INTERACTIVE TILE COLLISION
            gp.getChecker().checkEntity(this, gp.getiTile());

            if (collisionOn || knockBackCounter == 6) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else {
                switch (knockBackDirection) {
                    case NORTH -> worldY -= speed;
                    case SOUTH -> worldY += speed;
                    case WEST  -> worldX -= speed;
                    case EAST  -> worldX += speed;
                }
            }
            knockBackCounter++;

        }
        else if (attacking) {
            attacking();
        }
        else if (keyH.isSpaceKeyPressed()) {
            guarding = true;
            guardCounter++;
        }
        else if (keyH.isUpPressed() || keyH.isDownPressed()
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

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.getChecker().checkEntity(this, gp.getiTile());

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
            // CHECK ATTACK CANCELLED OR NOT
            if (keyH.isEnteredPressed() && !attackCanceled) {
                gp.playSoundEffect(SoundUtility.SWING);
                attacking     = true;
                spiritCounter = 0;
            }
            attackCanceled = false;
            keyH.setEnteredPressed(false);
            guarding = false;
            guardCounter=0;
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
            guarding = false;
            guardCounter=0;
        }

        if (gp.getKeyH().isShotKeyPressed() &&
                !projectile.alive &&
                shotAvailableCounter == 30 &&
                projectile.haveResource(this)) {

            // SET DEFAULT COORDINATION, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT COST (MANA, AMMO, ETC.)
            projectile.subtractResource(this);

            // ADD IT TO THE LIST
//            gp.getProjectile().add(projectile);
            // CHECK VACANCY
            for (int i = 0; i < gp.getProjectile()[1].length; i++) {
                if (gp.getProjectile()[gp.getCurrentMap()][i] == null) {
                    gp.getProjectile()[gp.getCurrentMap()][i] = projectile;
                    break;
                }
            }

            gp.playSoundEffect(SoundUtility.BURNING);
            shotAvailableCounter = 0;
        }

        //  THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > CommonConstant.FPS) {
                invincible        = false;
                transparent       = false;
                invincibleCounter = 0;
            }
        }
        // SHOT AVAILABLE
        if (shotAvailableCounter < 30) { shotAvailableCounter++; }

        // CORRECT VALUE
        if (life > maxLife) { life = maxLife; }
        if (mana > maxMana) { mana = maxMana; }

        if (life <= 0) {
            gp.setGameState(GameState.GAME_OVER);
            gp.getUi().setCommandNum(-1);
            gp.stopMusic();
            gp.playSoundEffect(SoundUtility.GAME_OVER);
        }

    }

    // WHAT TO DO WHEN OBJECT COLLIED
    public void pickUpObject(int i) {
        if (i != 999) {

            // PICKUP ONLY ITEM
            if (gp.getObjects()[gp.getCurrentMap()][i].type == Type.PICKUP_ONLY) {
                gp.getObjects()[gp.getCurrentMap()][i].use(this);
                gp.getObjects()[gp.getCurrentMap()][i] = null;

            }
            // OBSTACLE
            else if (gp.getObjects()[gp.getCurrentMap()][i].type == Type.OBSTACLE) {
                if (keyH.isEnteredPressed()) {
                    attackCanceled = true;
                    gp.getObjects()[gp.getCurrentMap()][i].interact();
                }
            }  else {


                // INVENTORY ITEMS
                String text;

                if (canObtainItem(gp.getObjects()[gp.getCurrentMap()][i])) {
                    gp.playSoundEffect(SoundUtility.COIN);
                    text = "Got a " + gp.getObjects()[gp.getCurrentMap()][i].name.getName() + "!";

                } else {
                    text = "Your inventory is full!";
                }
                gp.getUi().addMessage(text);
                gp.getObjects()[gp.getCurrentMap()][i] = null;
            }
        }
    }
    // INTERACTION WITH NPC
    public void interactNPC(int i) {
        if (keyH.isEnteredPressed()) {
            if (i != 999) {
                attackCanceled = true;
                gp.setGameState(GameState.DIALOGUE);
                gp.getNpc()[gp.getCurrentMap()][i].speak();
            }
        }

    }
    // CONTACT MONSTER
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.getMonster()[gp.getCurrentMap()][i].dying) {
                gp.playSoundEffect(SoundUtility.DAMAGE_RECEIVE);

                int damage  = gp.getMonster()[gp.getCurrentMap()][i].attack - defence;
                if (damage < 1) {
                    damage  = 1;
                }
                this.life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    // DAMAGE TO MONSTER
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {

        if (i != 999 ) {
           if (!gp.getMonster()[gp.getCurrentMap()][i].invincible) {

               gp.playSoundEffect(SoundUtility.HIT_MONSTER);

               if (knockBackPower > 0) {
                   setKnockBack(gp.getMonster()[gp.getCurrentMap()][i], attacker, knockBackPower);
               }

               if (gp.getMonster()[gp.getCurrentMap()][i].offBalance) {
                   attack *= 3;
               }

               int damage  = attack - gp.getMonster()[gp.getCurrentMap()][i].defence;
               if (damage <= 0) {
                   damage  = 0;
               }
               if (gp.getMonster()[gp.getCurrentMap()][i].alive) {
                   gp.getMonster()[gp.getCurrentMap()][i].life -= damage;
                   gp.getUi().addMessage(damage + " damage!");
               }
               gp.getMonster()[gp.getCurrentMap()][i].invincible = true;
               gp.getMonster()[gp.getCurrentMap()][i].damageReaction();

               if (gp.getMonster()[gp.getCurrentMap()][i].life <= 0) {
                   if (gp.getMonster()[gp.getCurrentMap()][i].alive && !gp.getMonster()[gp.getCurrentMap()][i].dying) {
                       gp.getUi().addMessage("Killed the " + gp.getMonster()[gp.getCurrentMap()][i].name.getName() + "!");
                       gp.getUi().addMessage("Exp + " + gp.getMonster()[gp.getCurrentMap()][i].exp);
                       exp += gp.getMonster()[gp.getCurrentMap()][i].exp;
                       checkLevelUp();
//                       gp.getMonster()[gp.getCurrentMap()][i].alive = false;
                   }
                   gp.getMonster()[gp.getCurrentMap()][i].dying = true;
               }
           }
        }
    }
    public void damageProjectTile(int i) {
        if (i != 999) {
            Entity projectile = gp.getProjectile()[gp.getCurrentMap()][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }
    public void checkLevelUp() {
        while (exp >= nextLevelExp) {
            level++;
            strength++;
            dexterity++;
            exp          -= nextLevelExp;
            nextLevelExp += nextLevelExp/2 + nextLevelExp%2;
            maxLife      += 2;
            maxMana      += 1;
            attack        = getAttack();
            defence       = getDefense();

            gp.playSoundEffect(SoundUtility.LEVEL_UP);
            gp.setGameState(GameState.DIALOGUE);
            gp.getUi().setCurrentDialogue("You are level " + level + " now\n" +
                    "You feel stronger!" );
        }
    }
    public void selectItem() {
        int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().getPlayerSlotCol(), gp.getUi().getPlayerSlotRow());

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == Type.SWORD || selectedItem.type == Type.AXE) {
                currentWeapon = (SuperItem) selectedItem;
                attack        = getAttack();
                getAttackImage();
            }
            if (selectedItem.type == Type.SHIELD) {
                currentShield = (SuperItem) selectedItem;
                defence       = getDefense();
            }
            if (selectedItem.type == Type.LIGHT) {
                if (currentLight == selectedItem) {
                    currentLight = null;

                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if (selectedItem.type == Type.CONSUMABLE) {
               if (selectedItem.use(this)) {
                   if (selectedItem.amount > 1) {
                       selectedItem.amount--;
                   } else {
                       inventory.remove(itemIndex);
                   }
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
                if (guarding) {
                    yield guardUp;
                }
                if (!attacking) {
                    if (spiritNum == 1) yield up_1;
                    else if (spiritNum == 2) yield up_2;
                }
                if (attacking){
                    tempScreenY -= CommonConstant.TILE_SIZE;
                    if (spiritNum == 1) yield attackUp_1;
                    else if (spiritNum == 2) yield attackUp_2;
                }
                yield null; // or some default value
            }
            case SOUTH -> {
                if (guarding) {
                    yield guardDown;
                }
                if (!attacking) {
                    if (spiritNum == 1) yield down_1;
                    else if (spiritNum == 2) yield down_2;
                }
                if (attacking) {
                    if (spiritNum == 1) yield attackDown_1;
                    else if (spiritNum == 2) yield attackDown_2;
                }
                yield null; // or some default value
            }
            case WEST -> {
                if (guarding) {
                    yield guardLeft;
                }
                if (!attacking) {
                    if (spiritNum == 1) yield left_1;
                    else if (spiritNum == 2) yield left_2;
                }
                if (attacking) {
                    tempScreenX -= CommonConstant.TILE_SIZE;
                    if (spiritNum == 1) yield attackLeft_1;
                    else if (spiritNum == 2) yield attackLeft_2;
                }
                yield null; // or some default value
            }
            case EAST -> {
                if (guarding) {
                    yield guardRight;
                }
                if (!attacking) {
                    if (spiritNum == 1) yield right_1;
                    else if (spiritNum == 2) yield right_2;
                }
                if (attacking) {
                    if (spiritNum == 1) yield attackRight_1;
                    else if (spiritNum == 2) yield attackRight_2;
                }
                yield null; // or some default value
            }
            default -> null;
        };
        // FLINCH THE PLAYER AT INVINCIBLE
        if (transparent) {
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

    // INTERACTIVE-TILE
    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.getiTile()[gp.getCurrentMap()][i].isDestructible() &&
                gp.getiTile()[gp.getCurrentMap()][i].isCorrectItem(this) && !gp.getiTile()[gp.getCurrentMap()][i].invincible) {

            gp.getiTile()[gp.getCurrentMap()][i].playSE();
            gp.getiTile()[gp.getCurrentMap()][i].life--;
            gp.getiTile()[gp.getCurrentMap()][i].invincible = true;

            // GENERATE PARTICLE
            generateParticle(gp.getiTile()[gp.getCurrentMap()][i], gp.getiTile()[gp.getCurrentMap()][i]);

            if (gp.getiTile()[gp.getCurrentMap()][i].life <= 0) {
                gp.getiTile()[gp.getCurrentMap()][i] = gp.getiTile()[gp.getCurrentMap()][i].getDestroyedForm();
            }
        }
    }
    // GETTER METHODS
    public int getScreenX() {  return screenX;  }
    public int getScreenY() {  return screenY;  }
}
