package com.game.event_handler;

import com.game.GamePanel;
import com.game.constants.GameState;
import com.game.sound.SoundUtility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean upPressed, downPressed , leftPressed, rightPressed, enteredPressed, shotKeyPressed;
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
            case OPTION -> optionState(code);
            case GAME_OVER -> gameOverState(code);
            case TRADE -> tradeState(code);
            case MINI_MAP -> miniMapState(code);
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
                gp.playMusic(SoundUtility.BLUE_BOY_ADVENTURE);
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
        // SHOT FIREBALL
        if (KeyEvent.VK_F == code) {
            shotKeyPressed = true;
        }
        // TALK WITH NPC
        if (KeyEvent.VK_C == code) {
            gp.setGameState(GameState.CHARACTER_STATUS);
        }
        // OPTION STATE
        if (KeyEvent.VK_ESCAPE == code) {
            gp.setGameState(GameState.OPTION);
        }
        // MINI-MAP STATE
        if (KeyEvent.VK_M == code) {
            gp.setGameState(GameState.MINI_MAP);
        }

        // DEBUG
        if (code == KeyEvent.VK_R) {
            switch (gp.getCurrentMap()) {
                case 0 -> gp.getTileM().loadMap("/maps/Map/worldV3.txt", 0);
                case 1 -> gp.getTileM().loadMap("/maps/Map/interior01.txt", 1);
            }

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
        // SELECT
        if (KeyEvent.VK_ENTER == code ) {
            gp.getPlayer().selectItem();
        }
        playerInventory(code);


    }
    // OPTION-STATE
    private void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.setGameState(GameState.PLAY);
        }
        if (code == KeyEvent.VK_ENTER) {
            enteredPressed = true;
        }
        int maxCommonNum = 0;
        switch (gp.getUi().getSubState()) {
            case 0 -> maxCommonNum = 5;
            case 3 -> maxCommonNum = 1;
        }
        // UP MOVEMENT
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()-1);
            gp.playSoundEffect(SoundUtility.CURSOR);
            if (gp.getUi().getCommandNum() < 0) {
                gp.getUi().setCommandNum(maxCommonNum);
            }
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()+1);
            gp.playSoundEffect(SoundUtility.CURSOR);
            if (gp.getUi().getCommandNum() > maxCommonNum) {
                gp.getUi().setCommandNum(0);
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.getUi().getSubState() == 0) {
                if (gp.getUi().getCommandNum() == 1 && gp.getMusic().getVolumeScale() > 0) {
                    gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale()-1);
                    gp.getMusic().checkVolume();
                    gp.playSoundEffect(SoundUtility.CURSOR);
                }
                if (gp.getUi().getCommandNum() == 2 && gp.getSe().getVolumeScale() > 0) {
                    gp.getSe().setVolumeScale(gp.getSe().getVolumeScale()-1);
                    gp.playSoundEffect(SoundUtility.CURSOR);
                }
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.getUi().getSubState() == 0) {
                if (gp.getUi().getCommandNum() == 1 && gp.getMusic().getVolumeScale() < 5) {
                    gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale()+1);
                    gp.getMusic().checkVolume();
                    gp.playSoundEffect(SoundUtility.CURSOR);
                }
                if (gp.getUi().getCommandNum() == 2 && gp.getSe().getVolumeScale() < 5) {
                    gp.getSe().setVolumeScale(gp.getSe().getVolumeScale()+1);
                    gp.playSoundEffect(SoundUtility.CURSOR);
                }
            }
        }

    }

    private void miniMapState(int code) {

        if (KeyEvent.VK_M == code) {
            gp.setGameState(GameState.PLAY);

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
        // FIREBALL
        if (KeyEvent.VK_F == code) {
            shotKeyPressed = false;
        }
    }

    // GAME OVER
    private void gameOverState(int code) {
        // UP MOVEMENT
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()-1);
            gp.playSoundEffect(SoundUtility.CURSOR);
            if (gp.getUi().getCommandNum() < 0) {
                gp.getUi().setCommandNum(1);
            }
        }
        // DOWN MOVEMENT
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum()+1);
            gp.playSoundEffect(SoundUtility.CURSOR);
            if (gp.getUi().getCommandNum() > 1) {
                gp.getUi().setCommandNum(0);

            }
        }
        // ENTER
        if (code == KeyEvent.VK_ENTER) {
            if (gp.getUi().getCommandNum() == 0) {
                gp.setGameState(GameState.PLAY);
                gp.retry();
                gp.playMusic(SoundUtility.BLUE_BOY_ADVENTURE);
            }
            else if (gp.getUi().getCommandNum() == 1) {
                gp.getUi().setCommandNum(0);
                gp.setGameState(GameState.TITLE);
                gp.reset();
            }
        }
    }

    // TRADE
    private void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enteredPressed = true;
        }
        if (gp.getUi().getSubState() == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum()-1);
                gp.playSoundEffect(SoundUtility.CURSOR);
                if (gp.getUi().getCommandNum() < 0) {
                    gp.getUi().setCommandNum(2);
                }
            }
            if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum()+1);
                gp.playSoundEffect(SoundUtility.CURSOR);
                if (gp.getUi().getCommandNum() > 2) {
                    gp.getUi().setCommandNum(0);
                }
            }
        }
        if (gp.getUi().getSubState() == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.getUi().setSubState(0);
            }
        }
        if (gp.getUi().getSubState() == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.getUi().setSubState(0);
            }
        }
    }

    private void playerInventory(int code) {
        // UP SIDE
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            if (gp.getUi().getPlayerSlotRow() != 0) {
                gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() - 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // DOWN SIDE
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            if (gp.getUi().getPlayerSlotRow() < 3) {
                gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() + 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // LEFT SIDE
        if (KeyEvent.VK_A == code || KeyEvent.VK_LEFT == code) {
            if (gp.getUi().getPlayerSlotCol() != 0) {
                gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() - 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // RIGHT SIDE
        if (KeyEvent.VK_D == code || KeyEvent.VK_RIGHT == code) {
            if (gp.getUi().getPlayerSlotCol() < 4) {
                gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() + 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
    }
    private void npcInventory(int code) {
        // UP SIDE
        if (KeyEvent.VK_W == code || KeyEvent.VK_UP == code) {
            if (gp.getUi().getNpcSlotRow() != 0) {
                gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() - 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // DOWN SIDE
        if (KeyEvent.VK_S == code || KeyEvent.VK_DOWN == code) {
            if (gp.getUi().getNpcSlotRow() < 3) {
                gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() + 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // LEFT SIDE
        if (KeyEvent.VK_A == code || KeyEvent.VK_LEFT == code) {
            if (gp.getUi().getNpcSlotCol() != 0) {
                gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() - 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
        }
        // RIGHT SIDE
        if (KeyEvent.VK_D == code || KeyEvent.VK_RIGHT == code) {
            if (gp.getUi().getNpcSlotCol() < 4) {
                gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() + 1);
                gp.playSoundEffect(SoundUtility.CURSOR);
            }
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
    public boolean isShotKeyPressed() {return shotKeyPressed;}

    // SETTER METHODS
    public void setEnteredPressed(boolean enteredPressed) {this.enteredPressed = enteredPressed;}
}
