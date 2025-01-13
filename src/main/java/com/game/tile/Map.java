package com.game.tile;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{

    GamePanel gp;
    BufferedImage[] worldMap;
    public boolean miniMapOn = false;

    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;

        createWorldMap();
    }
    public void createWorldMap() {
        worldMap = new BufferedImage[CommonConstant.MAX_MAP];
        int worldMapWidth  = CommonConstant.TILE_SIZE * CommonConstant.MAX_WORLD_COL;
        int worldMapHeight = CommonConstant.TILE_SIZE * CommonConstant.MAX_WORLD_ROW;
        for (int i = 0; i < CommonConstant.MAX_MAP; i++) {

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();

            int col = 0, row = 0;
            while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {

                int tileNum = getMapTileNum()[i][row][col];
                int x = CommonConstant.TILE_SIZE*col;
                int y = CommonConstant.TILE_SIZE*row;
                g2.drawImage(getTiles()[tileNum].image, x, y, null);

                col++;
                if (col == CommonConstant.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }

            }
        }

    }
    public void drawFullMapScreen(Graphics2D g2) {

        // BACKGROUND
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.getScreenWidth2(), gp.getScreenHeight2());

        // DRAW MAP
        int width  = 500;
        int height = 500;
        int x      = gp.getScreenWidth2()/2 - width/2;
        int y      = gp.getScreenHeight2()/2 - height/2;
        g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height, null);

    }
}
