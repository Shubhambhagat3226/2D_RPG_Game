package com.game.environment;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    int dayCounter;
    float filterAlpha = 0f;

    // DAY STATE
    final int day   = 0;
    final int dusk  = 1;
    final int night = 2;
    final int dawn  = 3;
    int dayState    = day;

    public Lighting(GamePanel gp) {
        this.gp = gp;

        setLightSource();
    }
    public void setLightSource() {

        // CREATE A BUFFERED IMAGE
        darknessFilter = new BufferedImage(gp.getScreenWidth2(), gp.getScreenHeight2(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();


        if (gp.getPlayer().getCurrentLight() == null) {
            g2.setColor(new Color(0,0,0.1f,0.98f));
        } else {
            // GET THE CENTER X AND Y OF THE LIGHT CIRCLE
            int centerX = gp.getPlayer().getScreenX() + (CommonConstant.TILE_SIZE / 2);
            int centerY = gp.getPlayer().getScreenY() + (CommonConstant.TILE_SIZE / 2);

            // CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE
            int variant = 12;
            Color[] color = new Color[variant];
            float[] fraction = new float[variant];

            int i = 0;
            color[i] = new Color(0, 0, 0.1f, 0.1f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.42f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.52f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.62f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.69f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.76f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.82f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.87f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.91f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.94f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.96f);
            i++;
            color[i] = new Color(0, 0, 0.1f, 0.98f);
            i++;

            i = 0;
            fraction[i] = 0f;
            i++;
            fraction[i] = 0.4f;
            i++;
            fraction[i] = 0.5f;
            i++;
            fraction[i] = 0.6f;
            i++;
            fraction[i] = 0.65f;
            i++;
            fraction[i] = 0.7f;
            i++;
            fraction[i] = 0.75f;
            i++;
            fraction[i] = 0.8f;
            i++;
            fraction[i] = 0.85f;
            i++;
            fraction[i] = 0.9f;
            i++;
            fraction[i] = 0.95f;
            i++;
            fraction[i] = 1f;
            i++;

            // CREATE A GRADATION PAINT SETTING FOR THE LIGHT CIRCLE
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.getPlayer().getCurrentLight().lightRadius, fraction, color);
            // SET GRADIENT DATA ON G2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.getScreenWidth2(), gp.getScreenHeight2());
        g2.dispose();

    }
    public void update() {
        if (gp.getPlayer().lightUpdated) {
            setLightSource();
            gp.getPlayer().lightUpdated = false;
        }
        // CHECK THE STATE OF THE DAY
        switch (dayState) {
            case day -> {
                dayCounter++;
                if (dayCounter > 600) {
                    dayState   = dusk;
                    dayCounter = 0;
                }
            }
            case dusk -> {
                filterAlpha += 0.001f;
                if (filterAlpha > 1f) {

                    filterAlpha = 1f;
                    dayState    = night;
                }
            }
            case night -> {
                dayCounter++;
                if (dayCounter > 600) {

                    dayState   = dawn;
                    dayCounter = 0;
                }
            }
            case dawn -> {
                filterAlpha -= 0.001f;
                if (filterAlpha < 0f) {

                    filterAlpha = 0f;
                    dayState    = day;
                }
            }
        }
    }
    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
        String situation = switch (dayState) {
            case day   -> "Day";
            case dusk  -> "Dask";
            case night -> "Night";
            case dawn  -> "Dawn";
            default    -> "";
        };

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString(situation, 800, 500);
    }
}










