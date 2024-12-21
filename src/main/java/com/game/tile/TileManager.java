package com.game.tile;

import com.game.GamePanel;
import com.game.UtilityTool;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tiles;
    private final int[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[50];
        map = new int[CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

       loadTileImages();
       loadMap("/maps/Map/world01.txt");
    }

    // LOAD THE TILES
    public void loadTileImages() {

        // PLACEHOLDER
        setUp(0, ImageUtility.GRASS_0, false);
        setUp(1, ImageUtility.WALL, true);
        setUp(2, ImageUtility.WATER_1, true);
        setUp(3, ImageUtility.EARTH, false);
        setUp(4, ImageUtility.TREE, true);
        setUp(5, ImageUtility.SAND, false);
        setUp(6, ImageUtility.GRASS_0, false);
        setUp(7, ImageUtility.GRASS_0, false);
        setUp(8, ImageUtility.GRASS_0, false);
        setUp(9, ImageUtility.GRASS_0, false);

        // USING TILES
        setUp(10, ImageUtility.GRASS_0, false);
        setUp(11, ImageUtility.GRASS_1, false);
        setUp(12, ImageUtility.WATER_0, true);
        setUp(13, ImageUtility.WATER_1, true);
        setUp(14, ImageUtility.WATER_2, true);
        setUp(15, ImageUtility.WATER_3, true);
        setUp(16, ImageUtility.WATER_4, true);
        setUp(17, ImageUtility.WATER_5, true);
        setUp(18, ImageUtility.WATER_6, true);
        setUp(19, ImageUtility.WATER_7, true);
        setUp(20, ImageUtility.WATER_8, true);
        setUp(21, ImageUtility.WATER_9, true);
        setUp(22, ImageUtility.WATER_10, true);
        setUp(23, ImageUtility.WATER_11, true);
        setUp(24, ImageUtility.WATER_12, true);
        setUp(25, ImageUtility.WATER_13, true);
        setUp(26, ImageUtility.ROAD_0, false);
        setUp(27, ImageUtility.ROAD_1, false);
        setUp(28, ImageUtility.ROAD_2, false);
        setUp(29, ImageUtility.ROAD_3, false);
        setUp(30, ImageUtility.ROAD_4, false);
        setUp(31, ImageUtility.ROAD_5, false);
        setUp(32, ImageUtility.ROAD_6, false);
        setUp(33, ImageUtility.ROAD_7, false);
        setUp(34, ImageUtility.ROAD_8, false);
        setUp(35, ImageUtility.ROAD_9, false);
        setUp(36, ImageUtility.ROAD_10, false);
        setUp(37, ImageUtility.ROAD_11, false);
        setUp(38, ImageUtility.ROAD_12, false);
        setUp(39, ImageUtility.EARTH, false);
        setUp(40, ImageUtility.WALL, true);
        setUp(41, ImageUtility.TREE, true);

    }
    public void setUp(int index, String imagePath, boolean collision) {
        tiles[index] = new Tile();
        tiles[index].collision = collision;
        tiles[index].image = UtilityTool.setImage(imagePath, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);

    }


    // DRAW TILES
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < CommonConstant.MAX_WORLD_COL && worldRow < CommonConstant.MAX_WORLD_ROW) {

            int tileNum = map[worldRow][worldCol];

            int worldX = worldCol * CommonConstant.TILE_SIZE;
            int worldY = worldRow * CommonConstant.TILE_SIZE;
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                    worldX - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                    worldY + CommonConstant.TILE_SIZE > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                    worldY - CommonConstant.TILE_SIZE < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY() ) {

                BufferedImage image = tiles[tileNum].image;
                g2.drawImage(image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == CommonConstant.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // LOAD MAP
    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
                String line = br.readLine();
                while (col < CommonConstant.MAX_WORLD_COL) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    map[row][col] = num;
                    col++;
                }
                if (col == CommonConstant.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // GETTER METHODS
    public int[][] getMap() {  return map;  }
    public Tile[] getTiles() {  return tiles;  }
}
