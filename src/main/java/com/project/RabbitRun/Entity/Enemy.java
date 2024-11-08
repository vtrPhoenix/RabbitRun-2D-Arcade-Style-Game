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

/**
 * Represents an enemy entity in the Rabbit Run game.
 * The enemy can move in four directions and detects collisions with tiles, players, and other enemies.
 *
 * @author Vivien Li
 * @version 1.0
 */
public class Enemy extends Entity{

    /** The game panel in which the enemy exists. */
    GamePanel gamePanel;

    /** Cooldown timer to prevent changing directions too frequently. */
    private int directionCooldown = 0;

    /** Counter to track how long the enemy has been stuck. */
    private int stuckCounter = 0;

    /** Random number generator for applying offsets when the enemy is stuck. */
    private Random random = new Random();

    /** The initial X-coordinate of the enemy. */
    private final int initialX;

    /** The initial Y-coordinate of the enemy. */
    private final int initialY;

    /**
     * Constructor to initialize the enemy with its starting position.
     *
     * @param gamePanel The game panel in which the enemy exists.
     * @param startX The initial X-coordinate of the enemy.
     * @param startY The initial Y-coordinate of the enemy.
     */
    public Enemy(GamePanel gamePanel, int startX, int startY) {
        this.gamePanel = gamePanel;
        this.initialX = startX;
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

    /**
     * Sets the default values for the enemy's speed and direction.
     */
    public void setDefaultValues() {
        speed = 2;
        direction = "left";
    }

    /**
     * Initializes a list of enemies at predetermined positions.
     *
     * @param gamePanel The game panel in which the enemies exist.
     * @return A list of enemies.
     */
    public static List<Enemy> initializeEnemies(GamePanel gamePanel) {
        int[][] enemyPositions = {
                {gamePanel.getTileSize() * 15, gamePanel.getTileSize() * 20},
                {gamePanel.getTileSize() * 20, gamePanel.getTileSize() * 30},
                {gamePanel.getTileSize() * 25, gamePanel.getTileSize() * 15}
        };

        List<Enemy> enemies = new ArrayList<>();
        for (int[] pos : enemyPositions) {
            enemies.add(new Enemy(gamePanel, pos[0], pos[1]));
        }
        return enemies;
    }

    /**
     * Resets the enemy's position and attributes to their default values.
     */
    public void restart() {
        this.worldX = initialX;
        this.worldY = initialY;
        setDefaultValues();
    }

    /**
     * Loads the images for the enemy's movement in different directions.
     */
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

    /**
     * Updates the enemy's movement and collision checks based on the player's position.
     *
     * @param player The player to track and interact with.
     */
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
            gamePanel.setGameState(gamePanel.youLostState);
        }

        if (collisionOn || collisionWithEnemy()) {
            alternateDirection(deltaX, deltaY);
            directionCooldown = 60;
            stuckCounter++;
        } else {
            stuckCounter = 0;
        }

        if (!collisionOn && !collisionWithEnemy()) {
            currentDirection();
        }

        if (stuckCounter > 5) {
            applyOffset();
            stuckCounter = 0;
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

    /**
     * Checks if this enemy is colliding with another enemy.
     *
     * @return True if there is a collision, false otherwise.
     */
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

    /**
     * Moves the enemy in the current direction.
     */
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

    /**
     * Changes the direction if the enemy encounters an obstacle.
     *
     * @param deltaX Difference between the player's X position and enemy's X position.
     * @param deltaY Difference between the player's Y position and enemy's Y position.
     */
    private void alternateDirection(int deltaX, int deltaY) {
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

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        if (collisionOn) {
            changeDirection();
        }
    }

    /**
     * Reverses the current direction of the enemy.
     */
    public void changeDirection() {
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

    /**
     * Applies a small random offset to the enemy's position when it is stuck.
     */
    private void applyOffset() {
        int offsetX = random.nextInt(31) - 15;
        int offsetY = random.nextInt(31) - 15;

        worldX += offsetX;
        worldY += offsetY;
    }

    /**
     * Checks if this enemy is colliding with the player.
     *
     * @param player The player to check collision against.
     * @return True if there is a collision, false otherwise.
     */
    public boolean collisionWithPlayer(Player player) {
        Rectangle enemyBounds = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle playerBounds = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

        return enemyBounds.intersects(playerBounds);
    }

    /**
     * Draws the enemy on the screen.
     *
     * @param g The graphics object used to draw the enemy.
     */
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

        int screenX = this.worldX - gamePanel.player.worldX + gamePanel.player.getScreenX();
        int screenY = this.worldY - gamePanel.player.worldY + gamePanel.player.getScreenY();

        g.drawImage(image, screenX, screenY ,gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
