package com.game.constants;

public enum ObjectName {
    KEY("key"),
    DOOR("door"),
    CHEST("chest"),
    BOOTS("boot"),
    HEART("life"),
    GREEN_SLIME("Green Slime"),
    NORMAL_SWORD("sword"),
    WOODEN_SHIELD("shield");

    private ObjectName(final String name) {
        this.name = name;
    }

    private String name;

    public String getName() {  return name;  }
}
