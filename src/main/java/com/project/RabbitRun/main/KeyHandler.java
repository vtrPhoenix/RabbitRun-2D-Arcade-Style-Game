package com.project.RabbitRun.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (key == KeyEvent.VK_P) {
            if(gamePanel.gameState == gamePanel.playState){
                gamePanel.gameState = gamePanel.pauseState;
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = false;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
