package com.game;

import com.game.constants.CommonConstant;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // FOR BETTER RENDERING PERFORMANCE

    }
}
