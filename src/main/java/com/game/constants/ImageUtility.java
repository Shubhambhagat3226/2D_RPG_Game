package com.game.constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtility {
    // PLAYER IMAGE
    public static final BufferedImage PLAYER_UP_1 = setImage("/Player/Walking sprites/boy_up_1.png");
    public static final BufferedImage PLAYER_UP_2 = setImage("/Player/Walking sprites/boy_up_2.png");
    public static final BufferedImage PLAYER_DOWN_1 = setImage("/Player/Walking sprites/boy_down_1.png");
    public static final BufferedImage PLAYER_DOWN_2 = setImage("/Player/Walking sprites/boy_down_2.png");
    public static final BufferedImage PLAYER_LEFT_1 = setImage("/Player/Walking sprites/boy_left_1.png");
    public static final BufferedImage PLAYER_LEFT_2 = setImage("/Player/Walking sprites/boy_left_2.png");
    public static final BufferedImage PLAYER_RIGHT_1 = setImage("/Player/Walking sprites/boy_right_1.png");
    public static final BufferedImage PLAYER_RIGHT_2 = setImage("/Player/Walking sprites/boy_right_2.png");


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
