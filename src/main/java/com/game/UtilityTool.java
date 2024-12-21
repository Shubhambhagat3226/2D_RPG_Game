package com.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UtilityTool {
    // SET-UP IMAGE FROM PATH
    public static BufferedImage setImage(String imagePath, int width, int height) {
        try {
            BufferedImage original = ImageIO.read(UtilityTool.class.getResource(imagePath));

            BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();
            return scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
