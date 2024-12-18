package com.game;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        // TITLE OF THE GAME
        super("2D Adventure");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ADD GAME_PANEL
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);

        pack();
        setLocationRelativeTo(null);

        // START THE GAME THREAD FOR SCREEN FRESH
        gamePanel.startGameThread();
    }
}
