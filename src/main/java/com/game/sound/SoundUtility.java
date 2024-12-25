package com.game.sound;

import java.net.URL;

public class SoundUtility {
    public static final URL  BLUE_BOY_ADVENTURE = setSound("/Sound/BlueBoyAdventure.wav");
    public static final URL  COIN               = setSound("/Sound/coin.wav");
    public static final URL  FANFARE            = setSound("/Sound/fanfare.wav");
    public static final URL  POWER_UP           = setSound("/Sound/powerup.wav");
    public static final URL  UNLOCK             = setSound("/Sound/unlock.wav");
    public static final URL  HIT_MONSTER        = setSound("/Sound/hitmonster.wav");
    public static final URL  DAMAGE_RECEIVE     = setSound("/Sound/receivedamage.wav");
    public static final URL  SWING              = setSound("/Sound/parry.wav");
    public static final URL  LEVEL_UP           = setSound("/Sound/levelup.wav");

    public static URL setSound(String soundPath) {
        return SoundUtility.class.getResource(soundPath);
    }
}
