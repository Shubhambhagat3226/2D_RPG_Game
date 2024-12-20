package com.game;

import com.game.constants.CommonConstant;
import com.game.entity.Player;
import com.game.event_handler.KeyHandler;
import com.game.object.SuperObject;
import com.game.sound.Sound;
import com.game.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SYSTEM
    private Thread gameThread;
    private final KeyHandler keyH;
    private final TileManager tileM;
    private final CollisionChecker checker;
    private AssetSetter aSetter;
    private final Sound sound;

    // ENTITY AND OBJECTS
    private final Player player;
    private SuperObject objects[];

    public GamePanel() {
        this.setPreferredSize(new Dimension(CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // FOR BETTER RENDERING PERFORMANCE

        // SYSTEM INITIALIZE
        // KEY LISTENER
        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);
        // SOUND
        sound = new Sound();
        // TILE-MANAGER SET
        tileM = new TileManager(this);
        // COLLISION-CHECKER OBJECT
        checker = new CollisionChecker(this);
        // ASSET-SETTER
        aSetter = new AssetSetter(this);

        // ENTITY AND OBJECT INITIALIZE--
        // PLAYER SET-UP
        player = new Player(this, keyH);
        // OBJECT ELEMENT
        objects = new SuperObject[10];
    }

    public void setupObject() {
        aSetter.setObject();
        playMusic(0);
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
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        // OBJECT ITEMS
        for (SuperObject obj : objects) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {
        sound.stop();
    }
    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }

    // GETTER METHOD
    public Player getPlayer() { return player; }
    public CollisionChecker getChecker() { return checker; }
    public TileManager getTileM() {  return tileM;  }
    public SuperObject[] getObjects() {  return objects;  }
    public Sound getSound() {return sound;}
}
