package com.game;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/config")));

            // FULL SCREEN
            if (gp.fullScreenOn) bw.write("On");
            else bw.write("Off");
            bw.newLine();
            // MUSIC STATE
            bw.write(String.valueOf(gp.getMusic().getVolumeScale()));
            bw.newLine();
            // SE STATE
            bw.write(String.valueOf(gp.getSe().getVolumeScale()));
            bw.newLine();

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/config")));

            String s = br.readLine();
            // FULL SCREEN
            if (s.equals("On")) gp.fullScreenOn = true;
            else gp.fullScreenOn = false;
            // MUSIC STATE
            s = br.readLine();
            gp.getMusic().setVolumeScale(Integer.parseInt(s));
            // MUSIC STATE
            s = br.readLine();
            gp.getSe().setVolumeScale(Integer.parseInt(s));

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
