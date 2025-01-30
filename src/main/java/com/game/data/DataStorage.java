package com.game.data;

import com.game.entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // PLAYER STATS
    int level;
    int maxLife;
    int life;
    int mana;
    int maxMana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // PLAYER INVENTORY
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;
}
