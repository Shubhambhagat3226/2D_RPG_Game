package com.game;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame rpgGame = new GameFrame();
            rpgGame.setVisible(true);
        });
    }
}
