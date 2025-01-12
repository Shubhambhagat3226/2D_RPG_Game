package com.game.tile;

import com.game.GamePanel;
import com.game.UtilityTool;
import com.game.constants.CommonConstant;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tiles;
    private int[][][] mapTileNum;
    public boolean drawPath = true;
    ArrayList<String> fileNames       = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        loadTileData();
        // INITIALIZE THE TITLE ARRAY BASED ON THE FILENAME
        tiles = new Tile[fileNames.size()];
        getTileImages();

        mapSize();
        loadMap("/Map (Tile editor version)/worldmap.txt", 0);
        loadMap("/Map (Tile editor version)/indoor01.txt", 1);

//       loadMap("/maps/Map/worldV3.txt", 0);
    }

    public void loadTileData() {
        // READ TILE DATA FILE
        InputStream is    = getClass().getResourceAsStream("/Map (Tile editor version)/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // GET TILE NAMES AND COLLISION INFO FROM THE FILE
        String line;
        try {
            while ((line = br.readLine()) != null){

                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mapSize() {

        InputStream is    = getClass().getResourceAsStream("/Map (Tile editor version)/worldmap.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            String line      = br.readLine();
            String[] maxTile = line.split(" ");

            CommonConstant.MAX_WORLD_COL = maxTile.length;
            CommonConstant.MAX_WORLD_ROW = maxTile.length;
            mapTileNum = new int[CommonConstant.MAX_MAP][CommonConstant.MAX_WORLD_ROW][CommonConstant.MAX_WORLD_COL];

            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // LOAD THE TILES
    public void getTileImages() {

        String fileName;
        boolean collision;
        for (int i = 0; i < fileNames.size(); i++) {

            fileName = "/Tiles/New version (with numbers)/"+fileNames.get(i);
            if (collisionStatus.get(i).equals("true")) {
                collision = true;
            }
            else {
                collision = false;
            }
            setUp(i, fileName, collision);

        }
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

            int tileNum = mapTileNum[gp.getCurrentMap()][worldRow][worldCol];

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

        if (drawPath) {
            g2.setColor(new Color(255, 0, 0, 70));
            for (int i = 0; i < gp.getpFinder().pathList.size(); i++) {

                int worldX = gp.getpFinder().pathList.get(i).getCol() * CommonConstant.TILE_SIZE;
                int worldY = gp.getpFinder().pathList.get(i).getRow() * CommonConstant.TILE_SIZE;
                int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
                int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

                g2.fillRect(screenX, screenY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
            }
        }
    }

    // LOAD MAP
    public void loadMap(String mapPath, int mapNo) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
                String line = br.readLine();
                while (col < CommonConstant.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[mapNo][row][col] = num;
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
    public int[][][] getMapTileNum() {  return mapTileNum;  }
    public Tile[] getTiles() {  return tiles;  }
}
