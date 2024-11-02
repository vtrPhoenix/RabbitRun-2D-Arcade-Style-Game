package com.project.RabbitRun.Entity;

import com.project.RabbitRun.Object.ObjExitDoor;
import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the player character in the game, including attributes
 * for position, movement, score, and interactions with objects.
 */
public class Player extends Entity {

    /** Reference to the main game panel. */
    GamePanel gamePanel;

    /** Reference to the key handler for player input. */
    KeyHandler keyHandler;

    /** X-coordinate of the player on the screen. */
    public final int screenX;

    /** Y-coordinate of the player on the screen. */
    public final int screenY;

    /** Counter for tracking the number of clovers collected by the player. */
    int hasClover = 0;

    /** Counter for tracking the number of carrots collected by the player. */
    int hasCarrot = 0;

    /** Points scored by the player. */
    public int points = 0;

    /** Points required to open the exit door. */
    private final int winningPoints = 400;

    /** Represents the open exit door object. */
    private ObjExitDoor openDoor;

    /** Represents the closed exit door object. */
    private ObjExitDoor closeDoor;

    /**
     * Constructs a Player object with the specified game panel and key handler.
     *
     * @param gamePanel the main game panel
     * @param keyHandler the handler for managing player input
     */
    public Player(GamePanel gamePanel , KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 25;
        solidArea.height = 25;

        closeDoor = new ObjExitDoor(false);
        openDoor = new ObjExitDoor(true);
        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Resets the player to the default state, clearing points and collected items.
     */
    public void restart() {
        setDefaultValues();
        points = 0;
        hasClover = 0;
        hasCarrot = 0;
    }

    /**
     * Sets the default values for the player's starting position, speed, and direction.
     */
    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 10;
        worldY = gamePanel.tileSize * 6;
        speed = 4;
        direction = "left";
    }

    /**
     * Loads the images for the player’s movement animations.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up1.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left1.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down1.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up2.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left2.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down2.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the player’s state, including movement, collision detection, and interaction with objects.
     */
    public void update() {
        if (keyHandler.upPressed || keyHandler.leftPressed || keyHandler.downPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) direction = "up";
            else if (keyHandler.downPressed) direction = "down";
            else if (keyHandler.leftPressed) direction = "left";
            else direction = "right";

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickObject(objIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            sprintCounter++;
            if (sprintCounter > 13) {
                spriteNumber = spriteNumber == 1 ? 2 : 1;
                sprintCounter = 0;
            }

            if (points < 0) {
                gamePanel.gameState = gamePanel.youLostState;
            }

            // Checks if points threshold is met to open the exit door
            if (points >= winningPoints) {
                gamePanel.object[5] = openDoor;
                gamePanel.object[5].worldX = 38 * gamePanel.tileSize;
                gamePanel.object[5].worldY = 32 * gamePanel.tileSize;
            } else {
                gamePanel.object[5] = closeDoor;
                gamePanel.object[5].worldX = 38 * gamePanel.tileSize;
                gamePanel.object[5].worldY = 32 * gamePanel.tileSize;
            }
        }
    }

    /**
     * Handles picking up an object in the game, adjusting points and
     * updating messages based on the type of object collected.
     *
     * @param index the index of the object to interact with
     */
    public void pickObject(int index) {
        if (index != 999) {
            String objName = gamePanel.object[index].name;

            switch (objName) {
                case "Clover" -> {
                    hasClover++;
                    gamePanel.object[index] = null;
                    points += 50;
                    gamePanel.ui.showMessage("YOU GOT A REWARD!", Color.green);
                }
                case "Carrot" -> {
                    hasCarrot++;
                    gamePanel.object[index] = null;
                    points += 100;
                    gamePanel.ui.showMessage("YOU GOT A BONUS REWARD!", Color.green);
                }
                case "Mushroom" -> {
                    gamePanel.object[index] = null;
                    points -= 100;
                    gamePanel.ui.showMessage("YOU FOUND A POISON MUSHROOM!", Color.red);
                }
                case "ExitDoor" -> {
                    if (points >= winningPoints && hasClover == 8) {
                        gamePanel.gameState = gamePanel.youWonState;
                    }
                    else if (points < winningPoints) {
                        gamePanel.ui.showMessage("YOU NEED MORE POINTS TO WIN!", Color.red);
                    }
                    else if (hasClover < 8) {
                        int remainingClover = 8 - hasClover;
                        gamePanel.ui.showMessage("YOU NEED " + remainingClover + " MORE CLOVERS TO EXIT!", Color.red);
                    }
                }
            }
        }
    }

    /**
     * Draws the player with the appropriate sprite image based on the
     * direction and animation frame.
     *
     * @param g2 the graphics context used to draw the player
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "left" -> (spriteNumber == 1) ? left1 : left2;
            case "right" -> (spriteNumber == 1) ? right1 : right2;
            case "up" -> (spriteNumber == 1) ? up1 : up2;
            case "down" -> (spriteNumber == 1) ? down1 : down2;
            default -> null;
        };

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
