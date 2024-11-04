package com.project.RabbitRun.Object;

import com.project.RabbitRun.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a generic game object in the "Rabbit Run" game.
 * This class serves as a superclass for various objects that appear on the game board,
 * including rewards, punishments, and obstacles.
 */
public class SuperObject {
    /** Image representing this object. */
    public BufferedImage image;
    /** Name of the object, typically used for identification. */
    public String name;
    /** Determines if the object has collision properties. */
    public boolean collision = false;
    /** X & Y -coordinates of the object in the game world. */
    public int worldX, worldY;
    /** Defines the object's solid area for collision detection. */
    public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    /** Default X-coordinate of the solid area relative to the object. */
    public int solidAreaDefaultX = 0;
    /** Default Y-coordinate of the solid area relative to the object. */
    public int solidAreaDefaultY = 0;

    /**
     * Draws the object on the game screen if it is within the visible screen area.
     *
     * @param g2         the {@code Graphics2D} context used for drawing
     * @param gamePanel  the game panel containing the game state and player information
     */
    public void draw (Graphics2D g2, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
