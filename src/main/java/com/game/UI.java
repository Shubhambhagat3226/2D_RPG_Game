package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    private final GamePanel gp;
    private Graphics2D g2;
    private BufferedImage heartFull, heartHalf, heartBlank;
    private final Font maruMonica, ancientFont;
    private boolean messageOn;
    private String message = "";
    private int messageCounter = 0;
    private boolean gameFinished;
    private String currentDialogue = "";
    private int commandNum=0;


    public UI(GamePanel gp) {
        this.gp     = gp;
        maruMonica  = UtilityTool.getFont("/font/x12y16pxMaruMonica.ttf");
        ancientFont = UtilityTool.getFont("/font/AncientModernTales-a7Po.ttf");

        heartFull   = UtilityTool.setImage(ImageUtility.HEART_FULL, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        heartHalf   = UtilityTool.setImage(ImageUtility.HEART_HALF, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        heartBlank  = UtilityTool.setImage(ImageUtility.HEART_BLANK, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);

    }

    // SET MESSAGE THAT WE HAVE TO SHOW
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // DRAW THE UI CONTENT TO PANEL
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        switch (gp.getGameState()) {
            case TITLE -> {
                drawTitleScreen();
            }
            case PLAY -> {
                drawPlayerLife();
            }
            case PAUSE -> {
                drawPlayerLife();
                drawPauseScreen();
            }
            case DIALOGUE -> {
                drawPlayerLife();
                drawDialogueScreen();
            }
        }
    }
    // PLAYER LIFE
    private void drawPlayerLife() {
        int x = CommonConstant.TILE_SIZE/2;
        int y = CommonConstant.TILE_SIZE/2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.getPlayer().getMaxLife()/2) {
            g2.drawImage(heartBlank, x, y, null);
            i++;
            x += CommonConstant.TILE_SIZE;
        }
        // RESET
        x = CommonConstant.TILE_SIZE/2;
        y = CommonConstant.TILE_SIZE/2;
        i = 0;
        // DRAW CURRENT LIFE
        while (i < gp.getPlayer().getLife()) {
            g2.drawImage(heartHalf, x,  y, null);
            i++;
            if (i < gp.getPlayer().getLife()) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += CommonConstant.TILE_SIZE;
        }
    }
    //DRAW TITLE-SCREEN
    private void drawTitleScreen() {
        // BACKGROUND COLOR
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT);
        // TITLE NAME
        g2.setFont(ancientFont.deriveFont(Font.BOLD, 96F));
        String text = "Blue-Boy Adventure";
        int x = getX_For_CenteredText(text);
        int y = CommonConstant.TILE_SIZE*3;
        // SHADOW TEXT
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);
        // MAIN TEXT
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // BLUE-BOY IMAGE
        x = CommonConstant.SCREEN_WIDTH/2 - CommonConstant.TILE_SIZE;
        y += CommonConstant.TILE_SIZE*2;
        g2.drawImage(UtilityTool.setImage(ImageUtility.PLAYER_DOWN_1,
                        CommonConstant.TILE_SIZE*2,
                        CommonConstant.TILE_SIZE*2),
                     x, y, null);

        // MENU
        g2.setFont(maruMonica.deriveFont(Font.BOLD, 42F));
        text = "NEW GAME";
        x = getX_For_CenteredText(text);
        y += (int) (CommonConstant.TILE_SIZE*3.5);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-CommonConstant.TILE_SIZE, y);
        }

        text = "LOAD GAME";
        x = getX_For_CenteredText(text);
        y += CommonConstant.TILE_SIZE;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-CommonConstant.TILE_SIZE, y);
        }

        text = "QUIT";
        x = getX_For_CenteredText(text);
        y += CommonConstant.TILE_SIZE;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x-CommonConstant.TILE_SIZE, y);
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += CommonConstant.TILE_SIZE;
        y += CommonConstant.TILE_SIZE;

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
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

    // GETTER METHODS
    public int getCommandNum() {return commandNum;}

    // SETTER METHODS
    public void setGameFinished(boolean gameFinished) {this.gameFinished = gameFinished;}
    public void setCurrentDialogue(String currentDialogue) {this.currentDialogue = currentDialogue;}
    public void setCommandNum(int commandNum) {this.commandNum = commandNum;}
}
