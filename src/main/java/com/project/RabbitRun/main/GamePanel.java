package com.project.RabbitRun.main;

import com.project.RabbitRun.Entity.Player;
import com.project.RabbitRun.Entity.Enemy;
import com.project.RabbitRun.Object.SuperObject;
import com.project.RabbitRun.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 16; // 32x32
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 64
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    //world settings/ change maxWorldCol and maxWorldRow to change the size of the map. also remember to change the map.txt
    //to fit the same dimensions
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 40;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    //GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int youWonState = 3;
    public final int youLostState = 4;


    int FPS = 60;


    //System
    TileManager tileM = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject[] object = new SuperObject[15];
    KeyHandler keyHandler = new KeyHandler(this);
    com.project.RabbitRun.main.mouseListener mouseListener = new mouseListener(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyHandler);

    public List<Enemy> enemies = new ArrayList<>();
    public UI ui = new UI(this);
    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        initializeEnemies();
        this.addMouseListener(mouseListener);
    }

    public void setupGame() {
        aSetter.setObject();

        gameState = menuState;
    }

    public void restartGame() {
        player.restart();
        aSetter.setObject();
        ui.restart();
        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
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

    public void update() {


        if(gameState == playState) {
            player.update();
            // Update each enemy
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).updateEnemy(player);
            }
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(gameState == menuState) {
            ui.draw(g2);
        }
        if(gameState == youLostState) {
            ui.draw(g2);
        }
        if(gameState == youWonState) {
            ui.draw(g2);
        }
        if(gameState == playState){
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

    private void initializeEnemies() {
        int[][] enemyPositions = {
                {tileSize * 22, tileSize * 16},
                {tileSize * 8, tileSize * 18},
                {tileSize * 22, tileSize * 30},
                {tileSize * 20, tileSize * 12}
        };

        for (int i = 0; i < enemyPositions.length; i++) {
            int[] pos = enemyPositions[i];
            Enemy enemy = new Enemy(this);
            enemy.worldX = pos[0];
            enemy.worldY = pos[1];
            enemies.add(enemy);
        }
    }
}
