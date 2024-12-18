package com.game;

import com.game.constants.CommonConstant;
import com.game.event_handler.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    private Thread gameThread;
    private KeyHandler keyH;

    // PLAYER DEFAULT POSITION
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // FOR BETTER RENDERING PERFORMANCE

        // KEY LISTENER
        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // DO FPS
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // DELTA LOOP
    @Override
    public void run() {
        double drawIntervals = (double) 1000000000 / CommonConstant.FPS; // 0.0166's
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawIntervals;
            lastTime = currentTime;

            if (delta >= 1) {
                // UPDATE INFORMATION SUCH AS CHARACTER POSITION
                update();

                // DRAW THE SCREEN WITH THE UPDATED INFORMATION
                repaint();

                delta--;
            }

        }
    }

    public void update() {
        if (keyH.isUpPressed()) {
            playerY -= playerSpeed;
        }
        if (keyH.isDownPressed()) {
            playerY += playerSpeed;
        }
        if (keyH.isLeftPressed()) {
            playerX -= playerSpeed;
        }
        if (keyH.isRightPressed()) {
            playerX += playerSpeed;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY, CommonConstant.TILE_SIZE, CommonConstant.TILE_SIZE);
        g2.dispose();
    }
}
