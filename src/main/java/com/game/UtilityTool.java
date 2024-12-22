package com.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    // SET-UP FONT
    public static Font getFont(String fontPath) {
        try {
            InputStream is = UtilityTool.class.getResourceAsStream(fontPath);
            return Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return new Font("Arial", Font.PLAIN, 28);
    }
}
