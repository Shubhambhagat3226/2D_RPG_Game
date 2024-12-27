package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean upPressed, downPressed , leftPressed, rightPressed, enteredPressed;
    // DEBUG
    private boolean showDebugTest;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gp.getGameState()) {
            case TITLE -> titleState(code);
            case PLAY -> playState(code);
            case PAUSE -> pauseState(code);
            case DIALOGUE -> dialogueState(code);
            case CHARACTER_STATUS -> characterState(code);
        }

    }
    // IN TITLE STATE
    private void titleState(int code) {
        // UP MOVEMENT
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()-1);
            if (gp.getUi().getCommandNum() < 0) {
                gp.getUi().setCommandNum(2);
            }
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()+1);
            if (gp.getUi().getCommandNum() > 2) {
                gp.getUi().setCommandNum(0);
            }
        }
        // EVENT ON OPTION
        if (KeyEvent.VK_ENTER == code) {
            if (gp.getUi().getCommandNum() == 0) {
                gp.setGameState(GameState.PLAY);
                gp.playMusic(0);
            }
            else if (gp.getUi().getCommandNum() == 1) {

            }
            else if (gp.getUi().getCommandNum() == 2) {
                System.exit(0);
            }
        }
    }
    // IN PLAY STATE
    private void playState(int code) {
        // UP MOVEMENT
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            upPressed = true;
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            downPressed = true;
        }
        // LEFT MOVEMENT
        if (KeyEvent.VK_A == code || KeyEvent.VK_LEFT == code) {
            leftPressed = true;
        }
        // RIGHT MOVEMENT
        if (KeyEvent.VK_D == code || KeyEvent.VK_RIGHT == code) {
            rightPressed = true;
        }
        // PAUSE SCREEN
        if (KeyEvent.VK_P == code) {
            gp.setGameState(GameState.PAUSE);
        }
        // TALK WITH NPC
        if (KeyEvent.VK_ENTER == code) {
            enteredPressed = true;
        }
        // TALK WITH NPC
        if (KeyEvent.VK_C == code) {
            gp.setGameState(GameState.CHARACTER_STATUS);
        }

        // DEBUG
        if (code == KeyEvent.VK_R) {
            gp.getTileM().loadMap("/maps/Map/worldV2.txt");
        }
        if (KeyEvent.VK_T == code) {
            if (!showDebugTest) {
                showDebugTest = true;
            } else {
                showDebugTest = false;
            }
        }
    }
    // IN PAUSE STATE
    private void pauseState(int code) {
        if (KeyEvent.VK_P == code) {
            gp.setGameState(GameState.PLAY);
        }
    }
    // IN DIALOGUE STATE
    private void dialogueState(int code) {
        if (KeyEvent.VK_ENTER == code) {
            gp.setGameState(GameState.PLAY);
        }
    }
    // IN CHARACTER STATE
    private void characterState(int code) {
        if (KeyEvent.VK_C == code) {
            gp.setGameState(GameState.PLAY);
        }
        // UP SIDE
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            gp.getUi().setSlotRow(gp.getUi().getSlotRow() - 1);
        }
        // DOWN SIDE
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            gp.getUi().setSlotRow(gp.getUi().getSlotRow() + 1);
        }
        // LEFT SIDE
        if (KeyEvent.VK_A == code || KeyEvent.VK_LEFT == code) {
            gp.getUi().setSlotCol(gp.getUi().getSlotCol() - 1);
        }
        // RIGHT SIDE
        if (KeyEvent.VK_D == code || KeyEvent.VK_RIGHT == code) {
            gp.getUi().setSlotCol(gp.getUi().getSlotCol() + 1);
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // UP MOVEMENT
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            upPressed = false;
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            downPressed = false;
        }
        // LEFT MOVEMENT
        if (KeyEvent.VK_A == code || KeyEvent.VK_LEFT == code) {
            leftPressed = false;
        }
        // RIGHT MOVEMENT
        if (KeyEvent.VK_D == code || KeyEvent.VK_RIGHT == code) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // GETTER METHODS
    public boolean isUpPressed() { return upPressed;}
    public boolean isDownPressed() { return downPressed; }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isShowDebugTest() { return showDebugTest; }
    public boolean isEnteredPressed() {return enteredPressed;}
    // SETTER METHODS
    public void setEnteredPressed(boolean enteredPressed) {this.enteredPressed = enteredPressed;}
}
