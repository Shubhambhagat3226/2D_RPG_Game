package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    private final GamePanel gp;
    private final Font arial_40;
    private final BufferedImage keyImage;
    private boolean messageOn;
    private String message = "";
    private int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        keyImage = ImageUtility.KEY;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, CommonConstant.TILE_SIZE/2, CommonConstant.TILE_SIZE/2,
                CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, null);
        g2.drawString("x " + gp.getPlayer().getHasKey(), 74, 65);

        // MESSAGE
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, CommonConstant.TILE_SIZE / 2, CommonConstant.TILE_SIZE * 5);

            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
