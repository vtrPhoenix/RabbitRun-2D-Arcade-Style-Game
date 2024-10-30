package com.project.RabbitRun.main;

import com.project.RabbitRun.Entity.Player;
import com.project.RabbitRun.Entity.Enemy;
import com.project.RabbitRun.Object.SuperObject;
import com.project.RabbitRun.tile.TileManager;

import javax.swing.*;
import java.awt.*;

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
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    //GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 6;


    int FPS = 60;


    //System
    TileManager tileM = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject[] object = new SuperObject[15];
    KeyHandler keyHandler = new KeyHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyHandler);
    public Enemy enemy = new Enemy(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        //settting up as playstate for now
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
            enemy.updateEnemy(player);
        }
        if(gameState == pauseState) {
        //nothing
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < object.length; i++) {
            if (object[i] != null) {
                object[i].draw(g2, this);
            }
        }

        player.draw(g2);
        enemy.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }
}
