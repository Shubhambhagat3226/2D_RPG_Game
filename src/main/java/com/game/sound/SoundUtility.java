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
    public static final URL  BLOCKED            = setSound("/Sound/blocked.wav");
    public static final URL  BURNING            = setSound("/Sound/burning.wav");
    public static final URL  CHIP_WALL          = setSound("/Sound/chipwall.wav");
    public static final URL  CURSOR             = setSound("/Sound/cursor.wav");
    public static final URL  CUT_TREE           = setSound("/Sound/cuttree.wav");
    public static final URL  DOOR_OPEN          = setSound("/Sound/dooropen.wav");
    public static final URL  DUNGEON            = setSound("/Sound/dungeon.wav");
    public static final URL  FINAL_BATTLE       = setSound("/Sound/FinalBattle.wav");
    public static final URL  GAME_OVER          = setSound("/Sound/gameover.wav");
    public static final URL  MERCHANT           = setSound("/Sound/Merchant.wav");
    public static final URL  SLEEP              = setSound("/Sound/sleep.wav");
    public static final URL  STAIRS             = setSound("/Sound/stairs.wav");
    public static final URL  SPEAK              = setSound("/Sound/speak.wav");

    public static URL setSound(String soundPath) {
        return SoundUtility.class.getResource(soundPath);
    }
}
