package com.project.RabbitRun.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class handles keyboard inputs for controlling player movement and game state changes.
 * It listens for key events and updates corresponding movement flags based on the keys pressed or released.
 */
public class KeyHandler implements KeyListener {

    /** Flags indicating the direction keys' states (pressed or released) */
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    /** Reference to the main GamePanel to access and modify game state */
    GamePanel gamePanel;

    /**
     * Constructs a KeyHandler with the specified GamePanel.
     * Initializes the direction flags to false.
     *
     * @param gamePanel the GamePanel instance that this KeyHandler controls
     */
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
    }

    /**
     * Invoked when a key is typed. This implementation is empty as typed events are not used.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**
     * Invoked when a key is pressed. Updates movement flags and handles pause/unpause functionality.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        // Toggle between play and pause states when 'P' is pressed
        if (key == KeyEvent.VK_P) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState;
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
        if(gamePanel.gameState == gamePanel.guideState) {
            if(key == KeyEvent.VK_B) {
                gamePanel.gameState = gamePanel.menuState;
            }
        }
    }

    /**
     * Invoked when a key is released. Updates movement flags accordingly.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = false;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
