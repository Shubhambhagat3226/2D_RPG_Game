package com.game.constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtility {
    // PLAYER IMAGES
    public static final BufferedImage PLAYER_UP_1 = setImage("/Player/Walking sprites/boy_up_1.png");
    public static final BufferedImage PLAYER_UP_2 = setImage("/Player/Walking sprites/boy_up_2.png");
    public static final BufferedImage PLAYER_DOWN_1 = setImage("/Player/Walking sprites/boy_down_1.png");
    public static final BufferedImage PLAYER_DOWN_2 = setImage("/Player/Walking sprites/boy_down_2.png");
    public static final BufferedImage PLAYER_LEFT_1 = setImage("/Player/Walking sprites/boy_left_1.png");
    public static final BufferedImage PLAYER_LEFT_2 = setImage("/Player/Walking sprites/boy_left_2.png");
    public static final BufferedImage PLAYER_RIGHT_1 = setImage("/Player/Walking sprites/boy_right_1.png");
    public static final BufferedImage PLAYER_RIGHT_2 = setImage("/Player/Walking sprites/boy_right_2.png");

    // TILE IMAGES
    public static final BufferedImage GRASS_0 = setImage("/Tiles/New version/grass00.png");
    public static final BufferedImage GRASS_1 = setImage("/Tiles/New version/grass01.png");
    public static final BufferedImage WALL = setImage("/Tiles/New version/wall.png");
    public static final BufferedImage WATER_0 = setImage("/Tiles/New version/water00.png");
    public static final BufferedImage WATER_1 = setImage("/Tiles/New version/water01.png");
    public static final BufferedImage WATER_2 = setImage("/Tiles/New version/water02.png");
    public static final BufferedImage WATER_3 = setImage("/Tiles/New version/water03.png");
    public static final BufferedImage WATER_4 = setImage("/Tiles/New version/water04.png");
    public static final BufferedImage WATER_5 = setImage("/Tiles/New version/water05.png");
    public static final BufferedImage WATER_6 = setImage("/Tiles/New version/water06.png");
    public static final BufferedImage WATER_7 = setImage("/Tiles/New version/water07.png");
    public static final BufferedImage WATER_8 = setImage("/Tiles/New version/water08.png");
    public static final BufferedImage WATER_9 = setImage("/Tiles/New version/water09.png");
    public static final BufferedImage WATER_10 = setImage("/Tiles/New version/water10.png");
    public static final BufferedImage WATER_11 = setImage("/Tiles/New version/water11.png");
    public static final BufferedImage WATER_12 = setImage("/Tiles/New version/water12.png");
    public static final BufferedImage WATER_13 = setImage("/Tiles/New version/water13.png");

    public static final BufferedImage ROAD_0 = setImage("/Tiles/New version/road00.png");
    public static final BufferedImage ROAD_1 = setImage("/Tiles/New version/road01.png");
    public static final BufferedImage ROAD_2 = setImage("/Tiles/New version/road02.png");
    public static final BufferedImage ROAD_3 = setImage("/Tiles/New version/road03.png");
    public static final BufferedImage ROAD_4 = setImage("/Tiles/New version/road04.png");
    public static final BufferedImage ROAD_5 = setImage("/Tiles/New version/road05.png");
    public static final BufferedImage ROAD_6 = setImage("/Tiles/New version/road06.png");
    public static final BufferedImage ROAD_7 = setImage("/Tiles/New version/road07.png");
    public static final BufferedImage ROAD_8 = setImage("/Tiles/New version/road08.png");
    public static final BufferedImage ROAD_9 = setImage("/Tiles/New version/road09.png");
    public static final BufferedImage ROAD_10 = setImage("/Tiles/New version/road10.png");
    public static final BufferedImage ROAD_11 = setImage("/Tiles/New version/road11.png");
    public static final BufferedImage ROAD_12 = setImage("/Tiles/New version/road12.png");

    public static final BufferedImage SAND = setImage("/Tiles/Old version/sand.png");
    public static final BufferedImage TREE = setImage("/Tiles/New version/tree.png");
    public static final BufferedImage EARTH = setImage("/Tiles/New version/earth.png");

    // OBJECTS IMAGES
    public static final BufferedImage KEY = setImage("/Object/key.png");
    public static final BufferedImage CHEST = setImage("/Object/chest.png");
    public static final BufferedImage WOODEN_DOOR = setImage("/Object/door.png");
    public static final BufferedImage BOOTS = setImage("/Object/boots.png");
    public static final BufferedImage IRON_DOOR = setImage("/Object/door_iron.png");

    // SET-UP IMAGE FROM PATH
    private static BufferedImage setImage(String imagePath) {
        try {
            BufferedImage original = ImageIO.read(ImageUtility.class.getResource(imagePath));
            BufferedImage scaledImage = new BufferedImage(CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, original.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(original, 0, 0, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);
            g2.dispose();
            return scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
