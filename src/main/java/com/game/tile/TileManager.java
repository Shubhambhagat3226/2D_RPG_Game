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
        map = new int[CommonConstant.MAX_SCREEN_ROW][CommonConstant.MAX_SCREEN_COL];

       loadTileImages();
       loadMap("/maps/maps.map0.txt");
    }

    // LOAD THE TILES
    public void loadTileImages() {
        for (int i = 0; i < 3; i++) {
            tiles[i] = new Tile();
        }
        tiles[0].image = ImageUtility.GRASS;
        tiles[1].image = ImageUtility.WALL;
        tiles[2].image = ImageUtility.WATER;
    }

    // DRAW TILES
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < CommonConstant.MAX_SCREEN_COL && row < CommonConstant.MAX_SCREEN_ROW) {

            int tileNum = map[row][col];

            BufferedImage image = tiles[tileNum].image;
            g2.drawImage(image, x, y, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);

            col++;
            x += CommonConstant.TILE_SIZE;
            if (col == CommonConstant.MAX_SCREEN_COL) {
                col = 0;
                x = 0;
                row++;
                y += CommonConstant.TILE_SIZE;
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

            while (col < CommonConstant.MAX_SCREEN_COL && row < CommonConstant.MAX_SCREEN_ROW) {
                String line = br.readLine();
                while (col < CommonConstant.MAX_SCREEN_COL) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    map[row][col] = num;
                    col++;
                }
                if (col == CommonConstant.MAX_SCREEN_COL) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
