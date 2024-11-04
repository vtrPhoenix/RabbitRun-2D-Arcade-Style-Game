package com.project.RabbitRun.main;

import com.project.RabbitRun.Object.ObjBonusReward;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The UI class is responsible for rendering all the user interface components in the RabbitRun game.
 * It includes the main menu, play state, pause state, win, and lose screens, as well as the points and messages display.
 */
public class UI {
    private final GamePanel gamePanel;

    private Font openSans;
    private Font ariel;

    private BufferedImage points;
    private BufferedImage clover;
    private BufferedImage menuPage;
    private BufferedImage guidePage;
    private BufferedImage youWonPage;
    private BufferedImage youLostPage;

    private Color messageColor = Color.WHITE;
    private boolean dispMessage = false;
    private String message = "";
    private int messageTimer = 0;
    public long startTime;
    private long endTime;

    /**
     * Constructs the UI, initializing fonts and loading images for the menu, win, and lose screens.
     *
     * @param gamePanel The GamePanel instance that this UI is part of.
     */
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        openSans = new Font("Open Sans", Font.BOLD, 20);
        ariel = new Font("Ariel", Font.BOLD, 15);
        try {
            points = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/onScreenIcons/XP.png")));
            clover = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Reward/clover.png")));
            menuPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/MenuPage.png")));
            guidePage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/Guide.png")));
            youLostPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/YOULOST.png")));
            youWonPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/YOUWON.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a temporary message on the screen in the specified color.
     *
     * @param message The message to display.
     * @param color The color of the message text.
     */
    public void showMessage(String message, Color color) {
        this.message = message;
        dispMessage = true;
        this.messageColor = color;
        messageTimer = 0;
    }

    /**
     * Draws the UI components based on the current game state.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void draw(Graphics g2) {
        g2.setFont(openSans);
        g2.setColor(Color.white);
        if (gamePanel.gameState == gamePanel.menuState) {
            drawMenuState(g2);
        }
        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayState(g2);
        }
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseState(g2);
        }
        if (gamePanel.gameState == gamePanel.youWonState) {
            drawYouWonState(g2);
        }
        if (gamePanel.gameState == gamePanel.youLostState) {
            drawYouLostState(g2);
        }
        if(gamePanel.gameState == gamePanel.guideState) {
            drawGuideState(g2);
        }
    }

    /**
     * Draws the menu screen.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawMenuState(Graphics g2) {
        g2.drawImage(menuPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
    }

    /**
     * Draws the Guide screen.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawGuideState(Graphics g2) {
        g2.drawImage(guidePage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
    }
    /**
     * Draws the play screen, including the player's points and elapsed time.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawPlayState(Graphics g2) {
        g2.drawImage(points, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " + gamePanel.player.points, 74, 60);
        g2.drawImage(clover,gamePanel.screenWidth / 2 - 60, gamePanel.tileSize / 2 , gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x "+ gamePanel.player.hasClover, gamePanel.screenWidth / 2 , 60 );
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;  // Convert to seconds
        g2.drawString("Time Elapsed: " + elapsedTime + "s", gamePanel.tileSize * 12, 60);  // Adjust position as needed
        endTime = elapsedTime;

        if (dispMessage) {
            g2.setFont(g2.getFont().deriveFont(15f));
            g2.setColor(messageColor);
            g2.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 6);

            messageTimer++;

            if (messageTimer > 70) {
                messageTimer = 0;
                dispMessage = false;
            }
        }

        if (elapsedTime == 5) {
            //gamePanel.object[2] = new ObjBonusReward();
            gamePanel.object[2].worldX = 24 * gamePanel.tileSize;
            gamePanel.object[2].worldY = 6 * gamePanel.tileSize;
        }
        if (elapsedTime == 20) {
            gamePanel.object[2] = null;
        }
        if (elapsedTime == 15) {
            //gamePanel.object[7] = new ObjBonusReward();
            gamePanel.object[7].worldX = 11 * gamePanel.tileSize;
            gamePanel.object[7].worldY = 33 * gamePanel.tileSize;
        }
        if (elapsedTime == 30) {
            gamePanel.object[7] = null;
        }
        if (elapsedTime == 25) {
            //gamePanel.object[11] = new ObjBonusReward();
            gamePanel.object[11].worldX = 38 * gamePanel.tileSize;
            gamePanel.object[11].worldY = 8 * gamePanel.tileSize;
        }
        if (elapsedTime == 40) {
            gamePanel.object[11] = null;
        }
        if (elapsedTime == 35) {
            //gamePanel.object[11] = new ObjBonusReward();
            gamePanel.object[15].worldX = 25 * gamePanel.tileSize;
            gamePanel.object[15].worldY = 12 * gamePanel.tileSize;
        }
        if (elapsedTime == 50) {
            gamePanel.object[15] = null;
        }

    }

    /**
     * Draws the pause screen, displaying "PAUSED" and instructions to resume the game.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawPauseState(Graphics g2) {
        g2.drawImage(points, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " + gamePanel.player.points, 74, 60);
        g2.drawImage(clover,gamePanel.screenWidth / 2 - 60, gamePanel.tileSize / 2 , gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x "+ gamePanel.player.hasClover, gamePanel.screenWidth / 2 , 60 );
        g2.drawString("Time Elapsed: " + endTime + "s", gamePanel.tileSize * 12, 60);  // Adjust position as needed
        String message = "PAUSED";
        String toUnPause = "Press 'P' to unpause";
        int x = getScreenCentreX(message, g2);
        int y = gamePanel.screenHeight / 2;
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString(message, x, y);

        g2.setFont(ariel);
        g2.drawString(toUnPause, x + 5, y + 25);
    }

    /**
     * Draws the "You Won" screen, displaying the player's points and the total elapsed time.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawYouWonState(Graphics g2) {
        g2.drawImage(youWonPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
        int x = getScreenCentreX("h", g2);
        g2.drawImage(points, x - 48, 221, gamePanel.tileSize, gamePanel.tileSize, null);

        g2.setFont(g2.getFont().deriveFont(25f));
        g2.drawString("x " + gamePanel.player.points, x - 48 + gamePanel.tileSize, 221 + 38);
        g2.drawString("Time Elapsed: " + endTime + "s", getScreenCentreX("Time Elapsed: " + endTime + "s", g2) + 90, 221 + 38 + gamePanel.tileSize);
    }

    /**
     * Draws the "You Lost" screen, displaying the player's points and the total elapsed time.
     *
     * @param g2 The Graphics object used for drawing.
     */
    public void drawYouLostState(Graphics g2) {
        g2.drawImage(youLostPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
        int x = getScreenCentreX("h", g2);
        g2.drawImage(points, x - 48, 221, gamePanel.tileSize, gamePanel.tileSize, null);

        g2.setFont(g2.getFont().deriveFont(25f));
        g2.drawString("x " + gamePanel.player.points, x - 48 + gamePanel.tileSize, 221 + 38);
        g2.drawString("Time Elapsed: " + endTime + "s", getScreenCentreX("Time Elapsed: " + endTime + "s", g2) + 90, 221 + 38 + gamePanel.tileSize);
    }

    /**
     * Calculates the x-coordinate to center a given string horizontally on the screen.
     *
     * @param s The string to center.
     * @param g2 The Graphics object used for calculating string width.
     * @return The x-coordinate to center the string.
     */
    public int getScreenCentreX(String s, Graphics g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
        return gamePanel.screenWidth / 2 - length;
    }

    /**
     * Resets the UI for a new game, resetting the start time and clearing messages.
     */
    public void restart() {
        startTime = System.currentTimeMillis();
        dispMessage = false;
        message = "";
        messageTimer = 0;
    }
}
