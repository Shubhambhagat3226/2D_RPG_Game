package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean upPressed, downPressed , leftPressed, rightPressed;
    // DEBUG
    private boolean checkDrawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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
            switch (gp.getGameState()) {
                case PLAY -> gp.setGameState(GameState.PAUSE);
                case PAUSE -> gp.setGameState(GameState.PLAY);
            }
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
}
