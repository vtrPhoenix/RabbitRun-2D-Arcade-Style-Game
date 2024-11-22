package com.project.RabbitRun.object;

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
    protected BufferedImage image;
    /** Name of the object, typically used for identification. */
    protected String name;
    /** Determines if the object has collision properties. */
    private boolean collision = false;
    /** X & Y -coordinates of the object in the game world. */
    private int worldX, worldY;
    /** Defines the object's solid area for collision detection. */
    protected Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    /** Default X-coordinate of the solid area relative to the object. */
    private int solidAreaDefaultX = 0;
    /** Default Y-coordinate of the solid area relative to the object. */
    private int solidAreaDefaultY = 0;

    /**
     * Draws the object on the game screen if it is within the visible screen area.
     *
     * @param g2         the {@code Graphics2D} context used for drawing
     * @param gamePanel  the game panel containing the game state and player information
     */
    public void draw (Graphics2D g2, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.getScreenX();
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.getScreenY();

        if (worldX + gamePanel.getTileSize() > gamePanel.player.getWorldX() - gamePanel.player.getScreenX() &&
            worldX - gamePanel.getTileSize() < gamePanel.player.getWorldX() + gamePanel.player.getScreenX() &&
            worldY + gamePanel.getTileSize() > gamePanel.player.getWorldY() - gamePanel.player.getScreenY() &&
            worldY - gamePanel.getTileSize() < gamePanel.player.getWorldY() + gamePanel.player.getScreenY()) {

            g2.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isCollision() { return collision; }

    public void setCollision(boolean collision) { this.collision = collision; }

    public int getWorldX() { return worldX; }

    public void setWorldX(int worldX) { this.worldX = worldX; }

    public int getWorldY() { return worldY; }

    public void setWorldY(int worldY) { this.worldY = worldY; }

    public Rectangle getSolidArea() { return solidArea; }

    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }

    public int getSolidAreaX() { return solidArea.x; }

    public void setSolidAreaX(int x) { this.solidArea.x = x; }

    public int getSolidAreaY() { return solidArea.y; }

    public void setSolidAreaY(int y) { this.solidArea.y = y; }

    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }

    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }
}
