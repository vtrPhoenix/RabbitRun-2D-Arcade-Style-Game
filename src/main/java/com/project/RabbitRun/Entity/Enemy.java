package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.CollisionChecker;
import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy extends Entity{

    GamePanel gamePanel;

    private int directionCooldown = 0;
    private int stuckCounter = 0;
    private Random random = new Random();
    private final int initialX, initialY;

    public Enemy(GamePanel gamePanel, int startX, int startY) {
        this.gamePanel = gamePanel;
        this.initialX = startX; // Store initial positions for restart
        this.initialY = startY;
        this.worldX = startX;
        this.worldY = startY;

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

    public static List<Enemy> initializeEnemies(GamePanel gamePanel) {
        int[][] enemyPositions = {
                {gamePanel.tileSize * 15, gamePanel.tileSize * 20},
                {gamePanel.tileSize * 20, gamePanel.tileSize * 30},
                {gamePanel.tileSize * 25, gamePanel.tileSize * 15}
        };

        List<Enemy> enemies = new ArrayList<>();
        for (int[] pos : enemyPositions) {
            enemies.add(new Enemy(gamePanel, pos[0], pos[1]));
        }
        return enemies;
    }

    public void restart() {
        // Reset position and attributes to default values
        this.worldX = initialX;
        this.worldY = initialY;
        setDefaultValues();
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

        int deltaX = player.worldX - this.worldX;
        int deltaY = player.worldY - this.worldY;

        if (directionCooldown == 0) {

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

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        if (collisionWithPlayer(player)) {
            gamePanel.gameState = gamePanel.youLostState;
        }

        if (collisionOn || collisionWithEnemy()) {
            alternateDirection(deltaX, deltaY);
            directionCooldown = 60;
            stuckCounter++;
        } else {
            stuckCounter = 0; // reset stuck counter if moving successfully
        }


        // move enemy in the current direction if no collision
        if (!collisionOn && !collisionWithEnemy()) {
            currentDirection();
        }

        if (stuckCounter > 5) {
            applyOffset();
            stuckCounter = 0; // reset stuck counter after offset is applied
        }

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

    private void currentDirection() {
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

    private void alternateDirection(int deltaX, int deltaY) {
        // check if primary direction was horizontal and change to vertical if blocked
        if (direction.equals("left") || direction.equals("right")) {
            if (deltaY > 0) {
                direction = "down";
            } else {
                direction = "up";
            }
        } else if (direction.equals("up") || direction.equals("down")) {
            if (deltaX > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
        }

        // check collision on the new direction and adjust if still blocked
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        if (collisionOn) {
            // if still blocked, reverse the direction
            changeDirection();
        }

    }

    public void changeDirection() {
        // reverse current direction when a collision occurs
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
    }

    private void applyOffset() {
        int offsetX = random.nextInt(31) - 15;
        int offsetY = random.nextInt(31) - 15;

        worldX += offsetX;
        worldY += offsetY;
    }

    public boolean collisionWithPlayer(Player player) {
        Rectangle enemyBounds = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle playerBounds = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

        return enemyBounds.intersects(playerBounds);
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
