package com.project.RabbitRun.Object;

import com.project.RabbitRun.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw (Graphics2D g2, GamePanel gamePanel) {

        int screenX = worldX;
        int screenY = worldY;

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
