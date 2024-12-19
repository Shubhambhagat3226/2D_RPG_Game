package com.game.constants;

public enum Direction {
    EAST("right"), WEST("left"), NORTH("up"), SOUTH("down");

    private Direction(final String side) {
        this.side = side;
    }

    private String side;

    public String getSide() {  return side;  }
}
