package com.game.constants;

public enum Direction {
    EAST("right"),
    WEST("left"),
    NORTH("up"),
    SOUTH("down"),
    ANY("all side");

    private Direction(final String side) {
        this.side = side;
    }

    private String side;

    public String getSide() {  return side;  }
}
