package com.game.entity;

import com.game.GamePanel;

import java.awt.image.BufferedImage;

public class SuperItem extends Entity {
    // ITEMS ATTRIBUTE
    protected int attackValue;
    protected int defenseValue;
    protected int value;

    public SuperItem(GamePanel gp) {
        super(gp);
    }

    public int getAttackValue() {return attackValue;}
    public void setAttackValue(int attackValue) {this.attackValue = attackValue;}

    public int getDefenseValue() {return defenseValue;}
    public void setDefenseValue(int defenseValue) {this.defenseValue = defenseValue;}

    public BufferedImage getImage() {
        return down_1;
    }
}
