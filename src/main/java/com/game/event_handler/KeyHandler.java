package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean upPressed, downPressed , leftPressed, rightPressed, enteredPressed;
    // DEBUG
    private boolean checkDrawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.getGameState() == GameState.TITLE) {
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
        }
        // PLAY STATE
        else if (gp.getGameState() == GameState.PLAY) {
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

            // DEBUG
            if (KeyEvent.VK_T == code) {
                if (!checkDrawTime) {
                    checkDrawTime = true;
                } else {
                    checkDrawTime = false;
                }
            }
        }
        // PAUSE STATE
        else if (gp.getGameState() == GameState.PAUSE) {
            if (KeyEvent.VK_P == code) {
                gp.setGameState(GameState.PLAY);
            }
        }
        // DIALOGUE STATE
        else if (gp.getGameState() == GameState.DIALOGUE) {
            if (KeyEvent.VK_ENTER == code) {
                gp.setGameState(GameState.PLAY);
            }
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
    public boolean isCheckDrawTime() { return checkDrawTime; }
    public boolean isEnteredPressed() {return enteredPressed;}
    // SETTER METHODS
    public void setEnteredPressed(boolean enteredPressed) {this.enteredPressed = enteredPressed;}
}
