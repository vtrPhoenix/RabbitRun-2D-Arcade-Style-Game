package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity{

    GamePanel gamePanel;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        worldX = 0; // will change
        worldY = 0; // will change
        speed = 4;
        direction = "down";
    }

    public void getEnemyImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Down2.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEnemy(int playerX, int playerY) {

        int deltaX = this.worldX;
        int deltaY = this.worldY;
        int moveX;
        int moveY;

        double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

        if (distance != 0) {
            moveX = (int) (speed * (deltaX / distance));
            moveY = (int) (speed * (deltaY / distance));

            this.worldX += moveX;
            this.worldY += moveY;

            // move horizontally
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX > 0) {
                    this.direction = "right";
                }
                else {
                    this.direction = "left";
                }
            }
            // move vertically
            else {
                if (deltaY > 0) {
                    this.direction = "down";
                }
                else {
                    this.direction = "up";
                }
            }
        }

    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        sprintCounter++;
        if (sprintCounter > 13) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            }
            else {
                spriteNumber = 1;
            }
            sprintCounter = 0;
        }

        switch(direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                }
                else {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                }
                else {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                }
                else {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                else {
                    image = right2;
                }
                break;
        }
    }
}
