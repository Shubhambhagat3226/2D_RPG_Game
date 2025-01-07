package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.GameState;
import com.game.constants.ImageUtility;
import com.game.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    private final GamePanel gp;
    private Graphics2D g2;
    private final BufferedImage heartFull, heartHalf, heartBlank;
    private final BufferedImage manaFull, manaBlank;
    private final Font maruMonica, ancientFont;
    private boolean messageOn;
//    private String message = "";
//    private int messageCounter = 0;
    ArrayList<String> message         = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    private boolean gameFinished;
    private String currentDialogue = "";
    private int commandNum=0;
    private int slotCol = 0;
    private int slotRow = 0;
    int subState;

    public UI(GamePanel gp) {
        this.gp     = gp;
        maruMonica  = UtilityTool.getFont("/font/x12y16pxMaruMonica.ttf");
        ancientFont = UtilityTool.getFont("/font/AncientModernTales-a7Po.ttf");

        heartFull   = UtilityTool.setImage(ImageUtility.HEART_FULL, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        heartHalf   = UtilityTool.setImage(ImageUtility.HEART_HALF, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        heartBlank  = UtilityTool.setImage(ImageUtility.HEART_BLANK, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);

        manaFull    = UtilityTool.setImage(ImageUtility.MANA_FULL, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        manaBlank   = UtilityTool.setImage(ImageUtility.MANA_EMPTY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
    }

    // SET MESSAGE THAT WE HAVE TO SHOW
    public void addMessage(String text) {
//        message = text;
//        messageOn = true;

        message.add(text);
        messageCounter.add(0);
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
                drawPlayerMana();
                drawMessage();
            }
            case PAUSE -> {
                drawPlayerLife();
                drawPlayerMana();
                drawPauseScreen();
            }
            case DIALOGUE -> {
                drawPlayerLife();
                drawPlayerMana();
                drawDialogueScreen();
            }
            case CHARACTER_STATUS -> {
                drawCharacterScreen();
                drawInventory();
            }
            case OPTION -> drawOptionScreen();
        }
    }
    // DRAW MESSAGE
    private void drawMessage() {
        int messageX = CommonConstant.TILE_SIZE;
        int messageY = CommonConstant.TILE_SIZE * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(message.get(i), messageX+2, messageY);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
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
    // DRAW PLAYER MANA
    private void drawPlayerMana() {
        int x = (CommonConstant.TILE_SIZE/2) - 4;
        int y = (int) (CommonConstant.TILE_SIZE*1.5);
        int i = 0;

        // DRAW MAX MANA
        while (i < gp.getPlayer().getMaxMana()) {
            g2.drawImage(manaBlank, x, y, null);
            i++;
            x +=35;
        }
        // RESET
        x = (CommonConstant.TILE_SIZE/2) - 4;
        y = (int) (CommonConstant.TILE_SIZE*1.5);
        i = 0;
        // DRAW CURRENT LIFE
        while (i < gp.getPlayer().getMana()) {
            g2.drawImage(manaFull, x,  y, null);
            i++;
            x += 35;
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

    // DRAW CHARACTER-SCREEN
    public void drawCharacterScreen() {

        // CREATE FRAME FOR STATUS
        final int frameX      = CommonConstant.TILE_SIZE*2;
        final int frameY      = CommonConstant.TILE_SIZE;
        final int frameWidth  = CommonConstant.TILE_SIZE*5;
        final int frameHeight = CommonConstant.TILE_SIZE*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + CommonConstant.TILE_SIZE;
        final int lineHeight = 35;

        // NAME
        String[] attribute = {"Level", "Life", "Mana", "Strength", "Dexterity", "Attack",
                "Defence", "Exp", "Next Level", "Coin", "Weapon", "Shield"};
        for (String s : attribute) {
            if (s.compareTo("Weapon") == 0) {
                textY += 10;
            } else if (s.compareTo("Shield") == 0) {
                textY += 15;
            }

            g2.drawString(s, textX, textY);
                textY += lineHeight;

        }

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // RESET Y
        textY = frameY + CommonConstant.TILE_SIZE;
        String value;

        // LEVEL
        value = String.valueOf(gp.getPlayer().getLevel());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getMana() + "/" + gp.getPlayer().getMaxMana());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getStrength());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getDexterity());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getAttack());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getDefense());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getExp());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getNextLevelExp());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getCoin());
        textX = getX_For_AlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        g2.drawImage(gp.getPlayer().getCurrentWeapon().getImage(), tailX - CommonConstant.TILE_SIZE, textY-24, null);
        textY += CommonConstant.TILE_SIZE;
        g2.drawImage(gp.getPlayer().getCurrentShield().getImage(), tailX - CommonConstant.TILE_SIZE, textY-24, null);

    }
    private int getX_For_AlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
    // DRAW INVENTORY
    public void drawInventory() {
        // FRAME
        int frameX      = CommonConstant.TILE_SIZE * 12;
        int frameY      = CommonConstant.TILE_SIZE;
        int frameWidth  = CommonConstant.TILE_SIZE * 6;
        int frameHeight = CommonConstant.TILE_SIZE * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotX_start = frameX + 20;
        final int slotY_start = frameY + 20;
        int slotX             = slotX_start;
        int slotY             = slotY_start;
        int slotSize          = CommonConstant.TILE_SIZE + 3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < gp.getPlayer().getInventory().size(); i++) {

            // EQUIP CURSOR
            if (gp.getPlayer().getInventory().get(i) == gp.getPlayer().getCurrentWeapon() ||
                    gp.getPlayer().getInventory().get(i) == gp.getPlayer().getCurrentShield()) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE, 10, 10);
            }

            g2.drawImage(gp.getPlayer().getInventory().get(i).getDown_1(), slotX, slotY, null);
            slotX += slotSize;
            if (i%5 == 4) {
                slotX  = slotX_start;
                slotY += slotSize;
            }
        }


        // CURSOR
        int cursorX      = slotX_start + (slotSize * slotCol);
        int cursorY      = slotY_start + (slotSize * slotRow);
        int cursorWidth  = CommonConstant.TILE_SIZE;
        int cursorHeight = CommonConstant.TILE_SIZE;
        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight,10, 10);

        // DESCRIPTION FRAME
        int dFrameX      = frameX;
        int dFrameY      = frameY + frameWidth;
        int dFrameWidth  = frameWidth;
        int dFrameHeight = CommonConstant.TILE_SIZE * 3;

        // DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + CommonConstant.TILE_SIZE;
        g2.setFont(maruMonica.deriveFont(28f));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gp.getPlayer().getInventory().size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line : gp.getPlayer().getInventory().get(itemIndex).getDescription().split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }
    public int getItemIndexOnSlot() {
        return (slotCol + (slotRow * 5));
    }

    // OPTION
    public void drawOptionScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // SUB-WINDOW
        int frameX      = CommonConstant.TILE_SIZE * 6;
        int frameY      = CommonConstant.TILE_SIZE;
        int frameWidth  = CommonConstant.TILE_SIZE * 8;
        int frameHeight = CommonConstant.TILE_SIZE * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
          case 0 -> options_top(frameX, frameY);
          case 1 -> options_fullScreenNotification(frameX, frameY);
          case 2 -> options_control(frameX, frameY);
          case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gp.getKeyH().setEnteredPressed(false);

    }
    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX       = getX_For_CenteredText(text);
        textY       = frameY + CommonConstant.TILE_SIZE;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + CommonConstant.TILE_SIZE;
        textY += CommonConstant.TILE_SIZE * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState        = 1;
            }
        }
        // Music
        textY += CommonConstant.TILE_SIZE;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }
        // SE
        textY += CommonConstant.TILE_SIZE;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX-25, textY);
        }
        // CONTROL
        textY += CommonConstant.TILE_SIZE;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 2;
                commandNum = 0;
            }
        }
        // END GAME
        textY += CommonConstant.TILE_SIZE;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 3;
                commandNum = 0;
            }
        }
        // BACK
        textY += CommonConstant.TILE_SIZE*2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                gp.setGameState(GameState.PLAY);
                commandNum = 0;
            }
        }


        // FULL SCREEN CHECK BOX
        textX = frameX + CommonConstant.TILE_SIZE*5;
        textY = frameY + CommonConstant.TILE_SIZE*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }
        // MUSIC VOLUME
        textY += CommonConstant.TILE_SIZE;
        g2.drawRect(textX, textY, 120, 24);  // 120/5 = 24
        int volumeWidth = 24 * gp.getMusic().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);
        // SE VOLUME
        textY += CommonConstant.TILE_SIZE;
        g2.drawRect(textX, textY, 120, 24);  // 120/5 = 24
        volumeWidth = 24 * gp.getSe().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

    }
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + CommonConstant.TILE_SIZE;
        int textY = frameY + CommonConstant.TILE_SIZE*3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + CommonConstant.TILE_SIZE*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 0;
            }
        }

    }
    private void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Control";
        textX       = getX_For_CenteredText(text);
        textY       = frameY + CommonConstant.TILE_SIZE;
        g2.drawString(text, textX, textY);

        textX  = frameX + CommonConstant.TILE_SIZE;
        textY += CommonConstant.TILE_SIZE;
        g2.drawString("Move", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("Confirm/Attack", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("Shoot/Cast", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("Character Screen", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("Pause", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("Options", textX, textY); textY += CommonConstant.TILE_SIZE;

        textX = frameX + CommonConstant.TILE_SIZE*6;
        textY = frameY + CommonConstant.TILE_SIZE*2;
        g2.drawString("W,A,S,D", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("ENTER", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("F", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("C", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("P", textX, textY); textY += CommonConstant.TILE_SIZE;
        g2.drawString("ESC", textX, textY); textY += CommonConstant.TILE_SIZE;

        // BACK
        textX  = frameX + CommonConstant.TILE_SIZE;
        textY  = frameY + CommonConstant.TILE_SIZE*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 0;
                commandNum = 3;
            }
        }

    }
    private void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + CommonConstant.TILE_SIZE;
        int textY = frameY + CommonConstant.TILE_SIZE*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // YES
        String text = "Yes";
        textX       = getX_For_CenteredText(text);
        textY      += CommonConstant.TILE_SIZE*3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 0;
                gp.setGameState(GameState.TITLE);
            }
        }
        // NO
        text = "No";
        textX       = getX_For_CenteredText(text);
        textY      += CommonConstant.TILE_SIZE;
        System.out.println(commandNum);
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX-25, textY);
            if (gp.getKeyH().isEnteredPressed()) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    // GETTER METHODS
    public int getCommandNum() {return commandNum;}
    public int getSlotCol() {return slotCol;}
    public int getSlotRow() {return slotRow;}
    public int getSubState() {return subState;}

    // SETTER METHODS
    public void setGameFinished(boolean gameFinished) {this.gameFinished = gameFinished;}
    public void setCurrentDialogue(String currentDialogue) {this.currentDialogue = currentDialogue;}
    public void setCommandNum(int commandNum) {this.commandNum = commandNum;}
    public void setSlotCol(int slotCol) {this.slotCol = slotCol;}
    public void setSlotRow(int slotRow) {this.slotRow = slotRow;}
}
