package com.game.event_handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed , leftPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // UP MOVEMENT
        if (KeyEvent.VK_W == code) {
            upPressed = true;
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code) {
            downPressed = true;
        }
        // LEFT MOVEMENT
        if (KeyEvent.VK_A == code) {
            leftPressed = true;
        }
        // RIGHT MOVEMENT
        if (KeyEvent.VK_D == code) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // UP MOVEMENT
        if (KeyEvent.VK_W == code) {
            upPressed = false;
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code) {
            downPressed = false;
        }
        // LEFT MOVEMENT
        if (KeyEvent.VK_A == code) {
            leftPressed = false;
        }
        // RIGHT MOVEMENT
        if (KeyEvent.VK_D == code) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
}
