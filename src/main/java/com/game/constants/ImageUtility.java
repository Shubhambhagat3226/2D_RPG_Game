package com.game.constants;

import javax.imageio.ImageIO;
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
    public static final BufferedImage GRASS = setImage("/Tiles/New version/grass01.png");
    public static final BufferedImage WALL = setImage("/Tiles/New version/wall.png");
    public static final BufferedImage WATER = setImage("/Tiles/New version/water01.png");
    public static final BufferedImage SAND = setImage("/Tiles/Old version/sand.png");
    public static final BufferedImage TREE = setImage("/Tiles/New version/tree.png");
    public static final BufferedImage EARTH = setImage("/Tiles/New version/earth.png");


    // SET-UP IMAGE FROM PATH
    private static BufferedImage setImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(ImageUtility.class.getResource(imagePath));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
