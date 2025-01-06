package com.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static Frame window;
    public GameFrame() {
        // TITLE OF THE GAME
        super("2D Adventure");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setUndecorated(true);

        // ADD GAME_PANEL
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);

        pack();
        setLocationRelativeTo(null);

        window = this;
        // SETUP OBJECTS IN FRAME
        gamePanel.setupObject();

        // START THE GAME THREAD FOR SCREEN FRESH
        gamePanel.startGameThread();
    }
}
