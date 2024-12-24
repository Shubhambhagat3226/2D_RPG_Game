package com.game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = SoundUtility.BLUE_BOY_ADVENTURE;
        soundURL[1] = SoundUtility.COIN;
        soundURL[2] = SoundUtility.POWER_UP;
        soundURL[3] = SoundUtility.UNLOCK;
        soundURL[4] = SoundUtility.FANFARE;
        soundURL[5] = SoundUtility.HIT_MONSTER;
        soundURL[6] = SoundUtility.DAMAGE_RECEIVE;
        soundURL[7] = SoundUtility.SWING;
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}















