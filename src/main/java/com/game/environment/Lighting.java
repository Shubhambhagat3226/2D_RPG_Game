package com.game.environment;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        this.gp = gp;

        // CREATE A BUFFERED IMAGE
        darknessFilter = new BufferedImage(gp.getScreenWidth2(), gp.getScreenHeight2(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // GET THE CENTER X AND Y OF THE LIGHT CIRCLE
        int centerX = gp.getPlayer().getScreenX() + (CommonConstant.TILE_SIZE/2);
        int centerY = gp.getPlayer().getScreenY() + (CommonConstant.TILE_SIZE/2);

        // CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE
        int variant      = 12;
        Color[] color    = new Color[variant];
        float[] fraction = new float[variant];

        int i = 0;
        color[i] = new Color(0,0,0, 0.1f); i++;
        color[i] = new Color(0,0,0, 0.42f); i++;
        color[i] = new Color(0,0,0, 0.52f); i++;
        color[i] = new Color(0,0,0, 0.62f); i++;
        color[i] = new Color(0,0,0, 0.69f); i++;
        color[i] = new Color(0,0,0, 0.76f); i++;
        color[i] = new Color(0,0,0, 0.82f); i++;
        color[i] = new Color(0,0,0, 0.87f); i++;
        color[i] = new Color(0,0,0, 0.91f); i++;
        color[i] = new Color(0,0,0, 0.94f); i++;
        color[i] = new Color(0,0,0, 0.96f); i++;
        color[i] = new Color(0,0,0, 0.98f); i++;

        i = 0;
        fraction[i] = 0f; i++;
        fraction[i] = 0.4f; i++;
        fraction[i] = 0.5f; i++;
        fraction[i] = 0.6f; i++;
        fraction[i] = 0.65f; i++;
        fraction[i] = 0.7f; i++;
        fraction[i] = 0.75f; i++;
        fraction[i] = 0.8f; i++;
        fraction[i] = 0.85f; i++;
        fraction[i] = 0.9f; i++;
        fraction[i] = 0.95f; i++;
        fraction[i] = 1f; i++;

        // CREATE A GRADATION PAINT SETTING FOR THE LIGHT CIRCLE
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
        // SET GRADIENT DATA ON G2
        g2.setPaint(gPaint);

        g2.fillRect(0, 0, gp.getScreenWidth2(), gp.getScreenHeight2());
        g2.dispose();


    }
    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}










