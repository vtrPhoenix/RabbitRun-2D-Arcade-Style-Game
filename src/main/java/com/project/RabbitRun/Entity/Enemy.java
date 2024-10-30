package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.CollisionChecker;
import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity{

    GamePanel gamePanel;

    CollisionChecker collisionChecker;
    private int directionCooldown = 0;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 25;

        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "left";
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

    public void updateEnemy(Player player) {

        if (directionCooldown > 0) {
            directionCooldown--;
        }

        if (directionCooldown == 0) {
            // Calculate direction based on the player's position
            int deltaX = player.worldX - this.worldX;
            int deltaY = player.worldY - this.worldY;

            // Set direction toward the player
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX > 0) {
                    direction = "right";
                } else {
                    direction = "left";
                }
            } else {
                if (deltaY > 0) {
                    direction = "down";
                } else {
                    direction = "up";
                }
            }
        }

        // Reset collision flag at the start of each update
        collisionOn = false;
        // Check for collision using checkTile
        gamePanel.collisionChecker.checkTile(this);

        if (collisionOn || collisionWithEnemy()){
            changeDirection();
            directionCooldown = 60;

        }

        // Move the enemy in the current direction if there's no collision
        if (!collisionOn && !collisionWithEnemy()) {
            moveInCurrentDirection();
        }

        // Toggle sprites for animation
        sprintCounter++;
        if (sprintCounter > 13) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            sprintCounter = 0;
        }
    }

    public boolean collisionWithEnemy() {
        for (int i = 0; i < gamePanel.enemies.size(); i++) {
            Enemy otherEnemy = gamePanel.enemies.get(i);

            if (otherEnemy != this) {
                Rectangle otherBounds = new Rectangle(
                        otherEnemy.worldX + otherEnemy.solidArea.x,
                        otherEnemy.worldY + otherEnemy.solidArea.y,
                        otherEnemy.solidArea.width,
                        otherEnemy.solidArea.height
                );

                Rectangle thisBounds = new Rectangle(
                        this.worldX + this.solidArea.x,
                        this.worldY + this.solidArea.y,
                        this.solidArea.width,
                        this.solidArea.height
                );

                if (thisBounds.intersects(otherBounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void moveInCurrentDirection() {
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }
    }

    public void changeDirection() {
        // Reverse current direction when a collision occurs
        switch (direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

        // Apply a repulsion offset to push enemies apart
        for (int i = 0; i < gamePanel.enemies.size(); i++) {
            Enemy otherEnemy = gamePanel.enemies.get(i);

            if (otherEnemy != this) {
                int dx = this.worldX - otherEnemy.worldX;
                int dy = this.worldY - otherEnemy.worldY;

                // Adjust position based on relative x and y
                if (Math.abs(dx) < gamePanel.tileSize && Math.abs(dy) < gamePanel.tileSize) {
                    if (dx > 0) {
                        this.worldX += 10;  // Move right by 10 pixels
                    } else {
                        this.worldX -= 10;  // Move left by 10 pixels
                    }

                    if (dy > 0) {
                        this.worldY += 10;  // Move down by 10 pixels
                    } else {
                        this.worldY -= 10;  // Move up by 10 pixels
                    }
                }
            }
        }
    }

    public boolean collisionWithPlayer(Player player) {
        Rectangle enemyBounds = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle playerBounds = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

        return enemyBounds.intersects(playerBounds);
    }

    public void handleCollision() {

    }

    public void draw(Graphics g) {
        BufferedImage image = null;

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
                    image = left2;
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
            default:
                break;
        }

        // calculate screen position based on the player position
        int screenX = this.worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = this.worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g.drawImage(image, screenX, screenY ,gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
