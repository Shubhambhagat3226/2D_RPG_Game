package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.GameState;
import com.game.entity.Entity;
import com.game.entity.Player;
import com.game.event_handler.EventHandler;
import com.game.event_handler.KeyHandler;
import com.game.sound.Sound;
import com.game.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private final EventHandler eventH;

    // ENTITY AND OBJECTS
    private final Player player;
    private Entity[] objects;
    private Entity[] npc;
    private Entity[] monster;
    ArrayList<Entity> entities;

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
        // EVENT-HANDLER
        eventH = new EventHandler(this);

        // ENTITY AND OBJECT INITIALIZE--
        // PLAYER SET-UP
        player = new Player(this, keyH);
        // OBJECT ELEMENT
        objects = new Entity[10];
        // NPC
        npc = new Entity[10];
        // MONSTER
        monster = new Entity[20];
        // ALL ENTITIES
        entities = new ArrayList<>();
    }

    public void setupObject() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
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
                // MONSTER
                for (Entity entity: monster) {
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

            // ADD ALL ENTITIES IN LIST
            // ADD PLAYER
            entities.add(player);
            // ADD NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entities.add(npc[i]);
                }
            }
            // ADD MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entities.add(monster[i]);
                }

            }

            // ADD OBJECTS
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null) {
                    entities.add(objects[i]);
                }
            }
            // SORT
            Collections.sort(entities, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return Integer.compare(o1.getWorldY(), o2.getWorldY());
                }
            });
            // DRAW ENTITIES
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2);
            }
            // EMPTY THE LIST
            entities.clear();

            // DRAW UI
            ui.draw(g2);

        }

        // DEBUG
        if (keyH.isCheckDrawTime()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + (double)passed/100000000);
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
    public Entity[] getObjects() {  return objects;  }
    public Sound getMusic() {return music;}
    public UI getUi() {return ui;}
    public GameState getGameState() {return gameState;}
    public Entity[] getNpc() {return npc;}
    public EventHandler getEventH() {return eventH;}
    public KeyHandler getKeyH() {return keyH;}
    public Entity[] getMonster() {return monster;}

    // SETTER METHODS
    public void setGameThread(Thread gameThread) {  this.gameThread = gameThread;  }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
}
