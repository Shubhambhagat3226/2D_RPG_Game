package com.game.data;

import com.game.GamePanel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoad {

    final GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
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

        }
        catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
