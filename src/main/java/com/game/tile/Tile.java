package com.game.tile;

import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage image;
    boolean collision;

    public boolean isCollision() { return collision; }
    public void setCollision(boolean collision) { this.collision = collision; }
}
