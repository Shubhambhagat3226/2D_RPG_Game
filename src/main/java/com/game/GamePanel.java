package com.game;

import com.game.constants.CommonConstant;
import com.game.constants.GameState;
import com.game.entity.Entity;
import com.game.entity.Player;
import com.game.event_handler.EventHandler;
import com.game.event_handler.KeyHandler;
import com.game.sound.Sound;
import com.game.tile.TileManager;
import com.game.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // FOR FULL SCREEN
    int screenWidth2  = CommonConstant.SCREEN_WIDTH;
    int screenHeight2 = CommonConstant.SCREEN_HEIGHT;
    BufferedImage tempScreen;
    Graphics2D g2;
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
    private InteractiveTile[] iTile;
    ArrayList<Entity> projectileList;
    ArrayList<Entity> particleList;
    ArrayList<Entity> entities;

    // GAME STATE
    private GameState gameState;
    boolean fullScreenOn = false;

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
        // INTERACTIVE SET
        iTile = new InteractiveTile[50];
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
        objects = new Entity[20];
        // NPC
        npc = new Entity[10];
        // MONSTER
        monster = new Entity[20];
        // ALL ENTITIES
        entities = new ArrayList<>();
        // FIREBALL ARRAY
        projectileList = new ArrayList<>();
        // PARTICLES LIST
        particleList = new ArrayList<>();
    }

    public void setupObject() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = GameState.TITLE;

        tempScreen = new BufferedImage(CommonConstant.SCREEN_WIDTH, CommonConstant.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2         = (Graphics2D) tempScreen.getGraphics();

//        setFullScreen();
    }

    // FULL SCREEN
    public void setFullScreen() {

        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd      = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(GameFrame.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2  = GameFrame.window.getWidth();
        screenHeight2 = GameFrame.window.getHeight();
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
                drawToTempScreen(); //DRAW EVERYTHING TO BUFFERED-IMAGE
                drawToScreen();     //DRAW BUFFERED-IMAGE TO SCREEN

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
                for (int i = 0; i < monster.length; i++) {
                    if (monster[i] != null) {
                        if (monster[i].isAlive()) {
                            monster[i].update();
                        }
                        else {
                            monster[i].checkDrop();
                            monster[i] = null;
                        }
                    }
                }
                // FIREBALL
                for (int i = 0; i < projectileList.size(); i++) {
                    if (projectileList.get(i) != null) {
                        if (projectileList.get(i).isAlive()) {
                            projectileList.get(i).update();
                        }
                        else {
                            projectileList.remove(i);
                        }
                    }
                }
                // PARTICLE LIST
                for (int i = 0; i < particleList.size(); i++) {
                    if (particleList.get(i) != null) {
                        if (particleList.get(i).isAlive()) {
                            particleList.get(i).update();
                        }
                        else {
                            particleList.remove(i);
                        }
                    }
                }
                // INTERACTIVE TREE
                for (int i = 0; i < iTile.length; i++) {
                    if (iTile[i] != null) {
                        iTile[i].update();
                    }
                }

            }
            case PAUSE -> { }
        }

    }

    public void drawToTempScreen() {
        // DEBUG
        long drawStart = 0;
        if (keyH.isShowDebugTest()) {
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

            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

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
            // ADD FIREBALL
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entities.add(projectileList.get(i));
                }

            }
            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entities.add(particleList.get(i));
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
        if (keyH.isShowDebugTest()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int x          = 10;
            int y          = 400;
            int lineHeight = 20;

            g2.drawString("WorldX: " + player.getWorldX(), x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.getWorldY(), x, y); y += lineHeight;
            g2.drawString("Col: " + (player.getWorldX() + player.getSolidArea().x)/CommonConstant.TILE_SIZE, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.getWorldY() + player.getSolidArea().y)/CommonConstant.TILE_SIZE, x, y); y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y);
        }

    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(URL sound) {
        music.setFile(sound);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(URL soundUrl) {
        se.setFile(soundUrl);
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
    public AssetSetter getaSetter() {return aSetter;}
    public ArrayList<Entity> getProjectileList() {return projectileList;}
    public InteractiveTile[] getiTile() {return iTile;}
    public ArrayList<Entity> getParticleList() {return particleList;}

    // SETTER METHODS
    public void setGameThread(Thread gameThread) {  this.gameThread = gameThread;  }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
}
