package com.game.data;

import com.game.GamePanel;
import com.game.entity.Entity;
import com.game.entity.SuperItem;
import com.game.object.*;
import com.game.object.weapon.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoad {

    final GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;
        switch (itemName) {
            case "Key" -> obj = new OBJ_KEY(gp);
            case "Woodcutter's Axe" -> obj = new OBJ_Axe(gp);
            case "boot" -> obj = new OBJ_BOOTS(gp);
            case "Lantern" -> obj = new OBJ_Lantern(gp);
            case "Normal Sword" -> obj = new OBJ_Sword(gp);
            case "Wooden Shield" -> obj = new OBJ_Wooden_Shield(gp);
            case "Blue Shield" -> obj = new OBJ_Blue_Shield(gp);
            case "Tent" -> obj = new OBJ_Tent(gp);
            case "door" -> obj = new OBJ_DOOR(gp);
            case "Red Potion" -> obj = new OBJ_Red_Potion(gp);
            case "chest" -> obj = new OBJ_CHEST(gp, new OBJ_KEY(gp));
        }

        return obj;

    }
    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            DataStorage ds = new DataStorage();

            ds.level = gp.getPlayer().getLevel();
            ds.maxLife = gp.getPlayer().getMaxLife();
            ds.life = gp.getPlayer().getLife();
            ds.maxMana = gp.getPlayer().getMaxMana();
            ds.mana = gp.getPlayer().getMana();
            ds.strength = gp.getPlayer().getStrength();
            ds.dexterity = gp.getPlayer().getDexterity();
            ds.exp = gp.getPlayer().getExp();
            ds.nextLevelExp = gp.getPlayer().getNextLevelExp();
            ds.coin = gp.getPlayer().getCoin();

            // PLAYER INVENTORY
            for (int i = 0; i < gp.getPlayer().getInventory().size(); i++) {
                ds.itemName.add(gp.getPlayer().getInventory().get(i).getName().getName());
                ds.itemAmounts.add(gp.getPlayer().getInventory().get(i).getAmount());

            }
            // PLAYER EQUIPMENT
            ds.currentWeaponSlot = gp.getPlayer().getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.getPlayer().getCurrentShieldSlot();


            // WRITE THE DATA-STORAGE OBJECT
            oos.writeObject(ds);

        } catch (Exception e) {
            System.out.println("Save Exception!");
        }
    }
    public void load() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

            // READ THE DATA-STORAGE OBJECT
            DataStorage ds = (DataStorage) ois.readObject();

            gp.getPlayer().setLevel(ds.level);
            gp.getPlayer().setMaxLife(ds.maxLife);
            gp.getPlayer().setLife(ds.life);
            gp.getPlayer().setMaxMana(ds.maxMana);
            gp.getPlayer().setMana(ds.mana);
            gp.getPlayer().setStrength(ds.strength);
            gp.getPlayer().setDexterity(ds.dexterity);
            gp.getPlayer().setExp(ds.exp);
            gp.getPlayer().setNextLevelExp(ds.nextLevelExp);
            gp.getPlayer().setCoin(ds.coin);

            // PLAYER INVENTORY
            gp.getPlayer().getInventory().clear();
            for (int i = 0; i < ds.itemName.size(); i++) {
                gp.getPlayer().getInventory().add(getObject(ds.itemName.get(i)));
                gp.getPlayer().getInventory().get(i).setAmount(ds.itemAmounts.get(i));
            }
            // PLAYER EQUIPMENT
            gp.getPlayer().setCurrentWeapon((SuperItem) gp.getPlayer().getInventory().get(ds.currentWeaponSlot));
            gp.getPlayer().setCurrentShield((SuperItem) gp.getPlayer().getInventory().get(ds.currentShieldSlot));
            gp.getPlayer().getAttack();
            gp.getPlayer().getDefense();
            gp.getPlayer().getAttackImage();


        }
        catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
