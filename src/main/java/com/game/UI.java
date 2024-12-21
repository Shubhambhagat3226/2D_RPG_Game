package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    private final GamePanel gp;
    private final Font arial_40, arial_80B;
    private final BufferedImage keyImage;
    private boolean messageOn;
    private String message = "";
    private int messageCounter = 0;
    private boolean gameFinished;
    private double playTime;
    private DecimalFormat dFormat;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        keyImage = UtilityTool.setImage(ImageUtility.KEY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        dFormat = new DecimalFormat("#0.00");
    }

    // SET MESSAGE THAT WE HAVE TO SHOW
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // DRAW THE UI CONTENT TO PANEL
    public void draw(Graphics2D g2) {
        if (gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            String text;
            int textLength;
            int x, y;

            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = CommonConstant.SCREEN_WIDTH/2 - textLength/2;
            y = CommonConstant.SCREEN_HEIGHT/2 - (CommonConstant.TILE_SIZE * 3);
            g2.drawString(text, x, y);

            text = "Your Time is: "+ dFormat.format(playTime) +"!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = CommonConstant.SCREEN_WIDTH/2 - textLength/2;
            y = CommonConstant.SCREEN_HEIGHT/2 + (CommonConstant.TILE_SIZE * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text = "Congratulation!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = CommonConstant.SCREEN_WIDTH/2 - textLength/2;
            y = CommonConstant.SCREEN_HEIGHT/2 + (CommonConstant.TILE_SIZE * 2);
            g2.drawString(text, x, y);

            gp.setGameThread(null);

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, CommonConstant.TILE_SIZE / 2, CommonConstant.TILE_SIZE / 2, null);
            g2.drawString("x " + gp.getPlayer().getHasKey(), 74, 65);

            // TIMER
            playTime += (double) 1/CommonConstant.FPS;
            g2.drawString("Time:" + dFormat.format(playTime) + "s", CommonConstant.TILE_SIZE*11, 65);

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

    // SETTER METHODS
    public void setGameFinished(boolean gameFinished) {this.gameFinished = gameFinished;}
}
