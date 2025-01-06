package com.game.constants;

public class CommonConstant {
    // SCREEN SETTING
    final public static int ORIGIN_TILE_SIZE = 16; // 16 * 16 TILE
    final public static int SCALE = 3;
    final public static int TILE_SIZE = ORIGIN_TILE_SIZE * SCALE; // 48 * 48 TILE
    final public static int MAX_SCREEN_COL = 20;
    final public static int MAX_SCREEN_ROW = 12;
    final public static int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 960 PX
    final public static int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 PX

    // WORLD SETTING
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = MAX_WORLD_COL * TILE_SIZE; // 2400 PX
    public static final int WORLD_HEIGHT = MAX_WORLD_ROW * TILE_SIZE; // 2400 PX

    final public static int FPS = 60;

}
