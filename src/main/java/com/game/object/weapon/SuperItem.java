package com.game.object.weapon;

import com.game.GamePanel;
import com.game.entity.Entity;

public class SuperItem extends Entity {
    // ITEMS ATTRIBUTE
    protected int attackValue;
    protected int defenseValue;

    public SuperItem(GamePanel gp) {
        super(gp);
    }

    public int getAttackValue() {return attackValue;}
    public void setAttackValue(int attackValue) {this.attackValue = attackValue;}

    public int getDefenseValue() {return defenseValue;}
    public void setDefenseValue(int defenseValue) {this.defenseValue = defenseValue;}
}
