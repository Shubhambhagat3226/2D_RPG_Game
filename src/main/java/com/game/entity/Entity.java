package com.game.entity;

import com.game.GamePanel;
import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.Direction;
import com.game.constants.ObjectName;
import com.game.constants.Type;
import com.game.sound.SoundUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    protected GamePanel gp;
    protected BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    protected BufferedImage attackUp_1, attackUp_2, attackDown_1, attackDown_2,
            attackLeft_1, attackLeft_2, attackRight_1, attackRight_2;
    protected Rectangle solidArea;
    protected Rectangle attackArea;
    protected int solidArea_Default_X, solidArea_Default_Y;
    protected String[] dialogue;

    // STATE
    protected int worldX, worldY;
    protected int width, height;
    protected Direction direction = Direction.SOUTH;
    protected int spiritNum       = 1;
    protected int dialogueIndex   = 0;
    protected boolean collisionOn;
    protected boolean invincible;
    protected boolean attacking;
    protected boolean alive;
    protected boolean dying;
    protected boolean hpBarOn;
    protected boolean onPath = false;
    protected boolean knockBack = false;

    // COUNTER
    protected int spiritCounter     = 0;
    protected int actionCounter     = 0;
    protected int invincibleCounter = 0;
    protected int dyingCounter      = 0;
    protected int hpBarCounter      = 0;
    protected int shotAvailableCounter = 0;
    protected int knockBackCounter = 0;

    // CHARACTER STATUS
    protected int type; // 0-player 1-npc 2-monster
    protected ObjectName name;
    protected int defaultSpeed;
    protected int maxLife;
    protected int life;
    protected int maxMana;
    protected int mana;
    protected int speed;
    protected int level;
    protected int strength;
    protected int dexterity;
    protected int attack;
    protected int defence;
    protected int exp;
    protected int nextLevelExp;
    protected int coin;
    protected SuperItem currentWeapon;
    protected SuperItem currentShield;
    protected Projectile projectile;

    // DESCRIPTION
    protected String description;
    protected int useCost;

    protected ArrayList<Entity> inventory;
    protected final int maxInventorySize = 20;
    protected int price;
    protected int knowBackPower = 0;
    protected boolean stackable = false;
    protected int amount = 1;

    public Entity(GamePanel gp) {
        this.gp    = gp;
        solidArea  = new Rectangle(0, 0, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        attackArea = new Rectangle(0, 0, 0, 0);
        width      = CommonConstant.TILE_SIZE;
        height     = CommonConstant.TILE_SIZE;
        alive      = true;
    }

    // SPEAK
    public void speak() {
        if (dialogueIndex == dialogue.length-1 || dialogue[dialogueIndex] == null) dialogueIndex=0;
        gp.getUi().setCurrentDialogue(dialogue[dialogueIndex]);
        dialogueIndex++;

        switch (gp.getPlayer().direction) {
            case NORTH -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.NORTH;
            case WEST  -> direction = Direction.EAST;
            case EAST  -> direction = Direction.WEST;
        }
    }
    public boolean use(Entity entity) {return false;}
    // SET-ACTION DEFINE IN SUN-CLASS
    public void setAction() {}
    // DAMAGE REACTION
    public void damageReaction() {}
    // DROP ITEM
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {

        for (int i = 0; i < gp.getObjects().length; i++) {
            if (gp.getObjects()[gp.getCurrentMap()][i] == null) {
                gp.getObjects()[gp.getCurrentMap()][i] = droppedItem;
                gp.getObjects()[gp.getCurrentMap()][i].worldX = worldX; // DEAD MONSTER's WORLD X
                gp.getObjects()[gp.getCurrentMap()][i].worldY = worldY; // DEAD MONSTER's WORLD Y
                break;
            }
        }
    }

    public void interact() {}
    public int getDetected(Entity user, Entity[][] target, ObjectName targetName) {
        int index = 999;

        // CHECK SURROUNDING OBJECT
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        switch (user.direction) {
            case NORTH -> nextWorldY = user.getTopY()-1;
            case SOUTH -> nextWorldY = user.getBottomY()+1;
            case WEST  -> nextWorldX = user.getLeftX()-1;
            case EAST  -> nextWorldX = user.getRightX()+1;
        }
        int col = nextWorldX/CommonConstant.TILE_SIZE;
        int row = nextWorldY/CommonConstant.TILE_SIZE;
        for (int i = 0; i < target[1].length; i++) {
            Entity t = target[gp.getCurrentMap()][i];
            if (t != null) {
                if (t.getCol() == col && t.getRow() == row && t.name == targetName) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
    // CHECK ALL COLLISION
    public void checkCollision() {
        collisionOn = false;
        gp.getChecker().checkTile(this);
        gp.getChecker().checkObject(this, false);
        gp.getChecker().checkEntity(this, gp.getNpc());
        gp.getChecker().checkEntity(this, gp.getMonster());
        gp.getChecker().checkEntity(this, gp.getiTile());
        boolean contactPlayer = gp.getChecker().checkPlayer(this);

        if (this.type == Type.MONSTER && contactPlayer) {

            damagePlayer(attack);
        }
    }
    // UPDATE METHOD DEFAULT FOR ENTITY
    public void update() {

        if (knockBack) {
            checkCollision();
            if (collisionOn || knockBackCounter == 6) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else {
                switch (gp.getPlayer().direction) {
                    case NORTH -> worldY -= speed;
                    case SOUTH -> worldY += speed;
                    case WEST  -> worldX -= speed;
                    case EAST  -> worldX += speed;
                }
            }
            knockBackCounter++;
        }
        else {
            setAction();
            checkCollision();
            // IF COLLISION IS FALSE, THEN PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case NORTH -> worldY -= speed;
                    case SOUTH -> worldY += speed;
                    case WEST  -> worldX -= speed;
                    case EAST  -> worldX += speed;
                }
            }

        }

        // TO CHANGE FROM OTHER IMAGE
        spiritCounter++;
        if (spiritCounter > 24) {
            if (spiritNum == 1) {
                spiritNum = 2;
            } else {
                spiritNum = 1;
            }
            spiritCounter = 0;
        }

        //  THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 10) {
                invincible        = false;
                invincibleCounter = 0;
            }
        }

        // SHOT AVAILABLE
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.getPlayer().invincible) {
            // GIVE DAMAGE
            gp.playSoundEffect(SoundUtility.DAMAGE_RECEIVE);

            int damage  = attack - gp.getPlayer().defence;
            if (damage < 0) {
                damage  = 0;
            }
            gp.getPlayer().life      -= damage;
            gp.getPlayer().invincible = true;
        }
    }
    // DRAW METHOD BY DEFAULT
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

            BufferedImage image  = switch (direction) {
                case NORTH -> {
                    if (spiritNum == 1) yield  up_1;
                    else if (spiritNum == 2) yield  up_2;
                    yield null; // or some default value
                }
                case SOUTH -> {
                    if (spiritNum == 1) yield  down_1;
                    else if (spiritNum == 2) yield  down_2;
                    yield null; // or some default value
                }
                case WEST -> {
                    if (spiritNum == 1) yield  left_1;
                    else if (spiritNum == 2) yield  left_2;
                    yield null; // or some default value
                }
                case EAST -> {
                    if (spiritNum == 1) yield  right_1;
                    else if (spiritNum == 2) yield  right_2;
                    yield null; // or some default value
                }
                default -> null;
            };
            // MONSTER HP BAR
            if (type == 2 && hpBarOn) {
                double oneScale   = (double) width/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, width + 2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > CommonConstant.FPS*10) {
                    hpBarCounter = 0;
                    hpBarOn      = false;
                }
            }

            // FLINCH THE PLAYER AT INVINCIBLE
            if (invincible) {
                hpBarCounter = 0;
                hpBarOn      = true;
                changeAlpha(g2, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null);

            changeAlpha(g2, 1);
        }
    }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 10;
        if (dyingCounter <= i) changeAlpha(g2, 0);
        else if (dyingCounter <= i * 2) changeAlpha(g2, 1);
        else if (dyingCounter <= i * 3) changeAlpha(g2, 0);
        else if (dyingCounter <= i * 4) changeAlpha(g2, 1);
        else if (dyingCounter <= i * 5) changeAlpha(g2, 0);
        else if (dyingCounter <= i * 6) changeAlpha(g2, 1);
        else if (dyingCounter <= i * 7) changeAlpha(g2, 0);
        else if (dyingCounter <= i * 8) changeAlpha(g2, 1);
        else {
//            dying = false;
            alive = false;
        }

    }

    public void changeAlpha(Graphics2D g2, float f) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f));
    }

    public BufferedImage getImage(String imagePath) {
        return UtilityTool.setImage(imagePath, width, height);
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x)/CommonConstant.TILE_SIZE;
        int startRow = (worldY + solidArea.y)/CommonConstant.TILE_SIZE;
        gp.getpFinder().setNode(startCol, startRow, goalCol, goalRow);

        if (gp.getpFinder().search()) {
            // NEXT WORLD-X AND Y
            int nextX = gp.getpFinder().pathList.get(0).getCol() * CommonConstant.TILE_SIZE;
            int nextY = gp.getpFinder().pathList.get(0).getRow() * CommonConstant.TILE_SIZE;
            // ENTITY'S SOLID AREA POSITION
            int enLeftX   = worldX + solidArea.x;
            int enRightX  = enLeftX + solidArea.width;
            int enTopY    = worldY + solidArea.y;
            int enBottomY = enTopY + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + CommonConstant.TILE_SIZE) {
                direction = Direction.NORTH;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + CommonConstant.TILE_SIZE) {
                direction = Direction.SOUTH;
            } else if (enTopY >= nextY && enBottomY < nextY + CommonConstant.TILE_SIZE) {
                if (enLeftX > nextX) {
                    direction = Direction.WEST;
                }
                if (enLeftX < nextX) {
                    direction = Direction.EAST;

                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = Direction.NORTH;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.WEST;
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = Direction.NORTH;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.EAST;
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = Direction.SOUTH;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.WEST;
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = Direction.SOUTH;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.EAST;
                }
            }

            // IF REACHES THE GOAL, STOP THE SEARCH
//            int nextCol = gp.getpFinder().pathList.get(0).getCol();
//            int nextRow = gp.getpFinder().pathList.get(0).getRow();
//            if (nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
//            }
        }
    }

    public int searchItemInInventory(ObjectName itemName) {
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name == itemName) {
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        // CHECK IF STACKABLE
        if (item.stackable) {
            int index = searchItemInInventory(item.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;

            }
            else { // NEW ITEM SO NEED TO CHECK VACANCY
                if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else {
            if (inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;

    }

    // PARTICLE
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0; // 6PX
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size    = generator.getParticleSize();
        int speed   = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.getParticleList().add(p1);
        gp.getParticleList().add(p2);
        gp.getParticleList().add(p3);
        gp.getParticleList().add(p4);
    }

    // COMMON
    public int getLeftX() {return worldX + solidArea.x;}
    public int getRightX() {return worldX + solidArea.x + solidArea.width;}
    public int getTopY() {return worldY + solidArea.y;}
    public int getBottomY() {return worldY + solidArea.y + solidArea.height;}
    public int getCol() {return (worldX + solidArea.x)/CommonConstant.TILE_SIZE;}
    public int getRow() {return (worldY + solidArea.y)/CommonConstant.TILE_SIZE;}

    // GETTER METHOD ONLY
    public int getWorldX() {  return worldX;  }
    public int getWorldY() {  return worldY;  }
    public Direction getDirection() {  return direction;  }
    public int getSpeed() {  return speed;  }
    public int getSolidArea_Default_X() {  return solidArea_Default_X;  }
    public int getSolidArea_Default_Y() {  return solidArea_Default_Y;  }
    public ObjectName getName() {return name;}
    public boolean isAlive() {return alive;}
    public boolean isDying() {return dying;}
    public int getLevel() {return level;}
    public int getStrength() {return strength;}
    public int getDexterity() {return dexterity;}
    public int getExp() {return exp;}
    public int getNextLevelExp() {return nextLevelExp;}
    public int getCoin() {return coin;}
    public SuperItem getCurrentWeapon() {return currentWeapon;}
    public SuperItem getCurrentShield() {return currentShield;}
    public BufferedImage getDown_1(){return down_1;}
    public String getDescription() {return description;}
    public int getMana() {return mana;}
    public int getMaxMana() {return maxMana;}
    public int getType() {return type;}
    public ArrayList<Entity> getInventory() {return inventory;}
    public int getPrice() {return price;}
    public int getMaxInventorySize() {return maxInventorySize;}
    public int getAmount() {return amount;}

    // SETTER AND GETTER
    public Rectangle getSolidArea() {return solidArea;}
    public void setSolidArea(Rectangle solidArea) {this.solidArea = solidArea;}

    public boolean isCollisionOn() {return collisionOn;}
    public void setCollisionOn(boolean collisionOn) {this.collisionOn = collisionOn;}

    public int getMaxLife() {return maxLife;}
    public void setMaxLife(int maxLife) {this.maxLife = maxLife;}

    public int getLife() {return life;}
    public void setLife(int life) {this.life = life;}

    public void setWorldX(int worldX) {this.worldX = worldX;}
    public void setWorldY(int worldY) {this.worldY = worldY;}

    public void setMana(int mana) {this.mana = mana;}
    public void setCoin(int coin) {this.coin = coin;}

    public void setAmount(int amount) {this.amount = amount;}
}
