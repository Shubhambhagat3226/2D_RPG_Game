package com.game.constants;

public enum ObjectName {
    KEY("Key"),
    DOOR("door"),
    CHEST("chest"),
    BOOTS("boot"),
    HEART("life"),
    GREEN_SLIME("Green Slime"),
    NORMAL_SWORD("Normal Sword"),
    AXE("Woodcutter's Axe"),
    BLUE_SHIELD("Blue Shield"),
    POTION("Red Potion"),
    FIREBALL("Fireball"),
    COIN("Coin"),
    LANTERN("Lantern"),
    WOODEN_SHIELD("Wooden Shield");

    private ObjectName(final String name) {
        this.name = name;
    }

    private String name;

    public String getName() {  return name;  }
}
