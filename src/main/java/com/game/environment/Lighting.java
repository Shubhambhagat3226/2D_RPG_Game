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

        // CREATE A SCREEN-SIZE RECTANGLE AREA
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.getScreenWidth2(), gp.getScreenHeight2()));

        // GET THE CENTER X AND Y OF THE LIGHT CIRCLE
        int centerX = gp.getPlayer().getScreenX() + (CommonConstant.TILE_SIZE/2);
        int centerY = gp.getPlayer().getScreenY() + (CommonConstant.TILE_SIZE/2);

        // GET THE TOP LEFT X AND Y IF THE LIGHT CIRCLE
        double x = centerX - (circleSize/2);
        double y = centerY - (circleSize/2);

        // CREATE A LIGHT CIRCLE SHAPE
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // CREATE A LIGHT CIRCLE AREA
        Area lightArea = new Area(circleShape);

        // SUBTRACT THE LIGHT CIRCLE FORM THE SCREEN RECTANGLE
        screenArea.subtract(lightArea);

        // SET A COLOR (BLACK) TO DRAW THE RECTANGLE
        g2.setColor(new Color(0, 0, 0, 0.95f));

        // DRAW THE SCREEN RECTANGLE WITHOUT THE LIGHT CIRCLE AREA
        g2.fill(screenArea);

        g2.dispose();


    }
    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}










