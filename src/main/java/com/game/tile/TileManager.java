package com.game.tile;

import com.game.GamePanel;
import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private final GamePanel gp;
    private Tile[] tiles;
    private int map[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];
        map = new int[CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

       loadTileImages();
       loadMap("/maps/Map/world01.txt");
    }

    // LOAD THE TILES
    public void loadTileImages() {

        setUp(0, ImageUtility.GRASS, false);
        setUp(1, ImageUtility.WALL, true);
        setUp(2, ImageUtility.WATER, true);
        setUp(3, ImageUtility.EARTH, false);
        setUp(4, ImageUtility.TREE, true);
        setUp(5, ImageUtility.SAND, false);

    }
    public void setUp(int index, BufferedImage image, boolean collision) {
        tiles[index] = new Tile();
        tiles[index].image = image;
        tiles[index].collision = collision;
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
