package com.project.RabbitRun.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    GamePanel gamePanel;

    Font openSans;
    Font ariel;

    BufferedImage points;
    BufferedImage menuPage;
    BufferedImage youWonPage;
    BufferedImage youLostPage;

    Color messageColor = Color.WHITE;

    boolean dispMessage = false;
    String message = "";
    int messageTimer = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        openSans = new Font("Open Sans", Font.BOLD, 20);
        ariel = new Font("Ariel", Font.BOLD, 15);
        try {
            points = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/onScreenIcons/XP.png")));
            menuPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/MenuPage.png")));
            youLostPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/YOULOST.png")));
            youWonPage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/States/YOUWON.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String message,Color color) {
        this.message = message;
        dispMessage = true;
        this.messageColor = color;
        messageTimer = 0;
    }
    public void draw(Graphics g2) {

        g2.setFont(openSans);
        g2.setColor(Color.white);
        if(gamePanel.gameState == gamePanel.menuState){
            drawMenuState(g2);
        }
        if(gamePanel.gameState == gamePanel.playState) {
            drawPlayState(g2);
        }
        if(gamePanel.gameState == gamePanel.pauseState) {
            drawPauseState(g2);
        }

        if(gamePanel.gameState == gamePanel.youWonState){
            drawYouWonState(g2);
        }
        if(gamePanel.gameState == gamePanel.youLostState){
            drawYouLostState(g2);
        }
    }

    public void drawMenuState(Graphics g2) {
        g2.drawImage(menuPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
    }

    public void drawPlayState(Graphics g2) {
        g2.drawImage(points, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " + gamePanel.player.points, 74, 60);

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
    }

    public void drawPauseState(Graphics g2) {
        g2.drawImage(points, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " + gamePanel.player.points, 74, 60);
        String message = "PAUSED";
        String toUnPause = "Press 'P' to unpause";
        int x = getScreenCentreX(message,g2);
        int y = gamePanel.screenHeight / 2;
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString(message, x, y);

        g2.setFont(ariel);
        g2.drawString(toUnPause, x+5, y+25);
    }
    public void drawYouWonState(Graphics g2) {
        g2.drawImage(youWonPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
    }

    public void drawYouLostState(Graphics g2) {
        g2.drawImage(youLostPage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
    }

    public int getScreenCentreX(String s ,Graphics g2 ){
        int length = (int)g2.getFontMetrics().getStringBounds(s, g2).getWidth();
        return gamePanel.screenWidth / 2 - length;
    }
}
