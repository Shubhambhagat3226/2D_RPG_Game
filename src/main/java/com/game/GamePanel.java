package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.GameState;
import com.game.entity.Entity;
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
    private final Sound music;
    private final Sound se;
    private final UI ui;

    // ENTITY AND OBJECTS
    private final Player player;
    private SuperObject[] objects;
    private Entity[] npc;

    // GAME STATE
    private GameState gameState;

    public GamePanel() {
        this.setPreferredSize(new Dimension(CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // FOR BETTER RENDERING PERFORMANCE

        // SYSTEM INITIALIZE
        // KEY LISTENER
        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        // SOUND
        music = new Sound();
        se = new Sound();
        // TILE-MANAGER SET
        tileM = new TileManager(this);
        // COLLISION-CHECKER OBJECT
        checker = new CollisionChecker(this);
        // ASSET-SETTER
        aSetter = new AssetSetter(this);
        // UI
        ui = new UI(this);

        // ENTITY AND OBJECT INITIALIZE--
        // PLAYER SET-UP
        player = new Player(this, keyH);
        // OBJECT ELEMENT
        objects = new SuperObject[10];
        // NPC
        npc = new Entity[10];
    }

    public void setupObject() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = GameState.TITLE;
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

        switch (gameState) {
            case PLAY -> {
                // PLAYER
                player.update();
                // NPC
                for (Entity entity: npc) {
                    if (entity != null) {
                        entity.update();
                    }
                }
            }
            case PAUSE -> { }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.isCheckDrawTime()) {
            drawStart = System.nanoTime();
        }

        // TITLE
        if (gameState == GameState.TITLE) {
            ui.draw(g2);
        }
        // OTHER
        else {

            // TILE
            tileM.draw(g2);

            // OBJECT ITEMS
            for (SuperObject obj : objects) {
                if (obj != null) {
                    obj.draw(g2, this);
                }
            }

            // NPC
            for (Entity npc : npc) {
                if (npc != null) {
                    npc.draw(g2);
                }
            }

            // PLAYER
            player.draw(g2);

            // DRAW UI
            ui.draw(g2);

        }

        // DEBUG
        if (keyH.isCheckDrawTime()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int i) {
        se.setFile(i);
        se.play();
    }

    // GETTER METHOD
    public Player getPlayer() { return player; }
    public CollisionChecker getChecker() { return checker; }
    public TileManager getTileM() {  return tileM;  }
    public SuperObject[] getObjects() {  return objects;  }
    public Sound getMusic() {return music;}
    public UI getUi() {return ui;}
    public GameState getGameState() {return gameState;}
    public Entity[] getNpc() {return npc;}

    // SETTER METHODS
    public void setGameThread(Thread gameThread) {  this.gameThread = gameThread;  }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
}
