package com.project.RabbitRun.main;

import com.project.RabbitRun.Entity.Player;
import com.project.RabbitRun.Entity.Enemy;
import com.project.RabbitRun.Object.SuperObject;
import com.project.RabbitRun.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main game panel that handles game rendering, updating, and main game loop.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    /** Original tile size before scaling. */
    final int originalTileSize = 16; // 32x32
    /** Scaling factor for tile size. */
    final int scale = 3;
    /** Scaled tile size. */
    public final int tileSize = originalTileSize * scale; // 64
    /** Maximum number of columns on screen. */
    public final int maxScreenCol = 16;
    /** Maximum number of rows on screen. */
    public final int maxScreenRow = 12;
    /** Width of the screen in pixels. */
    public final int screenWidth = maxScreenCol * tileSize;
    /** Height of the screen in pixels. */
    public final int screenHeight = maxScreenRow * tileSize;

    // World Settings
    /** Maximum columns in the world map. Adjust to change map size. */
    public final int maxWorldCol = 50;
    /** Maximum rows in the world map. Adjust to change map size. */
    public final int maxWorldRow = 40;
    /** Width of the world in pixels. */
    public final int worldWidth = maxWorldCol * tileSize;
    /** Height of the world in pixels. */
    public final int worldHeight = maxWorldRow * tileSize;

    // Game States
    /** Current state of the game. */
    public int gameState;
    /** Menu state identifier. */
    public final int menuState = 0;
    /** Play state identifier. */
    public final int playState = 1;
    /** Pause state identifier. */
    public final int pauseState = 2;
    /** Winning state identifier. */
    public final int youWonState = 3;
    /** Losing state identifier. */
    public final int youLostState = 4;
    /** Guide state identifier. */
    public final int guideState = 5;

    /** Frames per second target for game loop. */
    int FPS = 60;

    // System Components
    /** Manages game tiles for rendering and collision. */
    TileManager tileM = new TileManager(this);
    /** Sets and manages asset objects. */
    public AssetSetter aSetter = new AssetSetter(this);
    /** Array to hold game objects like items or obstacles. */
    public SuperObject[] object = new SuperObject[20];
    /** Handles key inputs for player movement and actions. */
    KeyHandler keyHandler = new KeyHandler(this);
    /** Handles mouse inputs in menu and other UI interactions. */
    com.project.RabbitRun.main.mouseListener mouseListener = new mouseListener(this);
    /** Checks for collisions between player, enemies, and objects. */
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyHandler);

    Sound gameMusic = new Sound();
    Sound soundEffect = new Sound();

    /** Player character for the game. */
    public Player player = new Player(this, keyHandler);
    /** List of enemies in the game. */
    public List<Enemy> enemies = new ArrayList<>();
    /** User interface manager for game status and messages. */
    public UI ui = new UI(this);
    /** Thread to run the game loop. */
    Thread gameThread;

    /**
     * Initializes the game panel with default settings.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        enemies = Enemy.initializeEnemies(this);
        this.addMouseListener(mouseListener);
    }

    /**
     * Sets up the initial state of the game, including assets and game state.
     */
    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
        gameState = menuState;
    }

    /**
     * Restarts the game, resetting player, enemies, and other elements to default.
     */
    public void restartGame() {
        player.restart();
        aSetter.setObject();
        ui.restart();
        enemies.forEach(Enemy::restart);
        gameState = playState;
    }

    /**
     * Starts the game thread to run the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Main game loop to control game updates and rendering.
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates the game elements based on the current game state.
     */
    public void update() {
        if (gameState == playState) {
            player.update();
            // Update each enemy
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).updateEnemy(player);
            }
        }
    }

    /**
     * Paints and draws all game elements on the screen based on the game state.
     *
     * @param g the graphics object used to draw game elements
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw UI based on the game state
        if (gameState == menuState || gameState == youLostState || gameState == youWonState
                || gameState == pauseState || gameState == guideState) {
            ui.draw(g2);
        }

        // Draw game elements when in play state
        if (gameState == playState) {
            tileM.draw(g2);
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g2);
            }
            for (int i = 0; i < object.length; i++) {
                if (object[i] != null) {
                    object[i].draw(g2, this);
                }
            }
            player.draw(g2);
            ui.draw(g2);
        }
        g2.dispose();
    }

    public void playMusic (int index) {
        gameMusic.setFile(index);
        gameMusic.play();
        gameMusic.loop();
    }

    public void stopMusic() {
        gameMusic.stop();
    }

    public void playSoundEffect (int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }

}
