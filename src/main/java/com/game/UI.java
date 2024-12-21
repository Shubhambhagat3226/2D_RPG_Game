package com.game;

import com.game.constants.CommonConstant;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    private final GamePanel gp;
    private Graphics2D g2;
    private final Font arial_40, arial_80B;
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
        dFormat = new DecimalFormat("#0.00");
    }

    // SET MESSAGE THAT WE HAVE TO SHOW
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // DRAW THE UI CONTENT TO PANEL
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        switch (gp.getGameState()) {
            case PLAY -> {
                // DO PLAY-STATE STUFF
            }
            case PAUSE -> {
                drawPauseScreen();
            }
        }
    }
    // DRAW PAUSE SCREEN
    private void drawPauseScreen() {
        String text = "PAUSE";
        int x = getX_For_CenteredText(text);
        int y = CommonConstant.SCREEN_HEIGHT/2;
        g2.drawString(text, x, y);
    }
    // LENGTH OF THE TEXT AFTER COMPARING IT TO DISPLAY SIZE IN SCREEN
    private int getX_For_CenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return CommonConstant.SCREEN_WIDTH/2 - length/2;
    }

    // SETTER METHODS
    public void setGameFinished(boolean gameFinished) {this.gameFinished = gameFinished;}
}
