package com.project.RabbitRun.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom MouseListener for handling button clicks in the RabbitRun game.
 * This listener manages the main menu, game end options, and transitions between game states
 * based on button interactions.
 */
public class mouseListener extends MouseAdapter {
    private final GamePanel gamePanel;

    // Button bounds for menu and end-screen actions
    private final Rectangle guideButtonBounds = new Rectangle(50, 254, 185, 35);
    private final Rectangle startButtonBounds = new Rectangle(300, 254, 185, 35);
    private final Rectangle quitButtonBounds = new Rectangle(550, 254, 185, 35);
    private final Rectangle playAgainButtonBounds = new Rectangle(101, 512, 230, 44);
    private final Rectangle quitOverButtonBounds = new Rectangle(459, 512, 230, 44);

    /**
     * Constructs a mouseListener for handling button clicks within the game.
     *
     * @param gamePanel The GamePanel instance that this listener interacts with.
     */
    public mouseListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Responds to mouse button presses, determining the current game state and handling clicks
     * on specific buttons accordingly.
     *
     * @param e MouseEvent generated by a mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point clickPoint = e.getPoint();

        // Check actions in the main menu state
        if (gamePanel.gameState == gamePanel.menuState) {
            handleMenuClick(clickPoint);
        }

        // Check actions in the end game states
        if (gamePanel.gameState == gamePanel.youWonState || gamePanel.gameState == gamePanel.youLostState) {
            handleEndScreenClick(clickPoint);
        }
    }

    /**
     * Handles button clicks on the main menu screen.
     *
     * @param clickPoint The point where the mouse was clicked.
     */
    private void handleMenuClick(Point clickPoint) {
        if (guideButtonBounds.contains(clickPoint)) {
            // Display the guide information
            gamePanel.playSoundEffect(5);
            gamePanel.gameState = gamePanel.guideState;
        } else if (startButtonBounds.contains(clickPoint)) {
            // Start the game and initialize the timer
            gamePanel.playSoundEffect(5);
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.startTime = System.currentTimeMillis();
        } else if (quitButtonBounds.contains(clickPoint)) {
            // Exit the application
            gamePanel.playSoundEffect(5);
            System.exit(0);
        }
    }

    /**
     * Handles button clicks on the end game screen (You Won or You Lost states).
     *
     * @param clickPoint The point where the mouse was clicked.
     */
    private void handleEndScreenClick(Point clickPoint) {
        if (playAgainButtonBounds.contains(clickPoint)) {
            // Restart the game
            gamePanel.restartGame();
        } else if (quitOverButtonBounds.contains(clickPoint)) {
            // Exit the application
            System.exit(0);
        }
    }
}
