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
    private String currentDialogue = "";


    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
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
            case DIALOGUE -> {
                drawDialogueScreen();
            }
        }
    }
    // DRAW PAUSE SCREEN
    private void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getX_For_CenteredText(text);
        int y = CommonConstant.SCREEN_HEIGHT/2;
        g2.drawString(text, x, y);
    }
    // LENGTH OF THE TEXT AFTER COMPARING IT TO DISPLAY SIZE IN SCREEN
    private int getX_For_CenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return CommonConstant.SCREEN_WIDTH/2 - length/2;
    }

    // DRAW DIALOGUE STATE
    public void drawDialogueScreen() {
        // WINDOW
        int x      = CommonConstant.TILE_SIZE*2;
        int y      = CommonConstant.TILE_SIZE/2;
        int width  = CommonConstant.SCREEN_WIDTH - (CommonConstant.TILE_SIZE*4);
        int height = CommonConstant.TILE_SIZE*4;

        drawSubWindow(x, y, width, height);
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(225, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    // SETTER METHODS
    public void setGameFinished(boolean gameFinished) {this.gameFinished = gameFinished;}
}
