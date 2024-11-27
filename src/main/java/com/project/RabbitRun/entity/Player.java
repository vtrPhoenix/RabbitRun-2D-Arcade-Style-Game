package com.project.RabbitRun.entity;

import com.project.RabbitRun.exceptions.ImageLoadingException;
import com.project.RabbitRun.object.ObjExitDoor;
import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.eventHandlers.KeyHandler;

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
    private final int screenX;

    /** Y-coordinate of the player on the screen. */
    private final int screenY;

    /** Counter for tracking the number of clovers collected by the player. */
    private int hasClover = 0;

    /** Counter for tracking the number of carrots collected by the player. */
    private int hasCarrot = 0;

    /** Points scored by the player. */
    private int points = 0;

    /** Points required to open the exit door. */
    private final int winningPoints = 400;

    /** Clovers requires to open the exit door*/
    private final int winningClovers = 8;

    /** Represents the open exit door object. */
    private final ObjExitDoor openDoor;

    /** Represents the closed exit door object. */
    private final ObjExitDoor closeDoor;

    /**
     * Constructs a Player object with the specified game panel and key handler.
     *
     * @param gamePanel the main game panel
     * @param keyHandler the handler for managing player input
     */
    public Player(GamePanel gamePanel , KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);
        /**
         * sets solid area rectangle which defines the bounds of the player character for collision with other blocks
         * **/
        solidArea = new Rectangle();
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
        worldX = gamePanel.getTileSize() * 10;
        worldY = gamePanel.getTileSize() * 6;
        speed = 4;
        direction = "left";
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 25;
        solidArea.height = 25;
    }



    /**
     * Loads the images for the player’s movement animations.
     */
    public void getPlayerImage() {
        try {
            up1 = loadImage("/player/up1.png");
            left1 = loadImage("/player/left1.png");
            down1 = loadImage("/player/down1.png");
            right1 = loadImage("/player/right1.png");
            up2 = loadImage("/player/up2.png");
            left2 = loadImage("/player/left2.png");
            down2 = loadImage("/player/down2.png");
            right2 = loadImage("/player/right2.png");
        } catch (ImageLoadingException e) {
            System.err.println("Exception caught while trying to load Player Images");
            throw e;
        }
    }

    /**
     * Helper method to load an image and include the path in exceptions.
     *
     * @param path The path of the image to load.
     * @return The loaded image.
     * @throws ImageLoadingException If the image cannot be loaded.
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (NullPointerException e) {
            throw new ImageLoadingException("Image not found: " + path, e, path);
        } catch (IOException e) {
            throw new ImageLoadingException("Failed to load image: " + path, e, path);
        }
    }

    /**
     * Checks if the player is moving (any key is pressed).
     */
    private boolean isMoving() {
        return keyHandler.isUpPressed() || keyHandler.isLeftPressed() || keyHandler.isDownPressed() || keyHandler.isRightPressed();
    }

    /**
     * Handles player movement based on input.
     */
    private void handleMovement() {
        if (keyHandler.isUpPressed()) direction = "up";
        else if (keyHandler.isDownPressed()) direction = "down";
        else if (keyHandler.isLeftPressed()) direction = "left";
        else if (keyHandler.isRightPressed()) direction = "right";

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        int objIndex = gamePanel.collisionCheckerObject.checkObject(this, true);
        pickObject(objIndex);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }

    /**
     * Updates the player's sprite for animation.
     */
    private void updateSprite() {
        sprintCounter++;
        if (sprintCounter > 13) {
            spriteNumber = spriteNumber == 1 ? 2 : 1;
            sprintCounter = 0;
        }
    }

    /**
     * Updates the player’s state, including movement, collision detection, and interaction with objects.
     */
    public void update() {

        if (isMoving()) {
            handleMovement();
            updateSprite();
        }
        checkGameStats();
    }

    /**
     * Checks if the player has lost and updates the game state accordingly.
     */
    private void checkGameStats() {
        if (points < 0) {
            gamePanel.stopMusic();
            gamePanel.playSoundEffect(7);
            gamePanel.setGameState(gamePanel.youLostState);
        }

        updateExitDoorState();
    }

    /**
     * Updates the state of the exit door depending on the player's score and collected clovers.
     */
    private void updateExitDoorState() {
        if (points >= winningPoints && hasClover == winningClovers) {
            gamePanel.object[5] = openDoor;
        } else {
            gamePanel.object[5] = closeDoor;
        }
        gamePanel.object[5].setWorldX(38 * gamePanel.getTileSize());
        gamePanel.object[5].setWorldY(32 * gamePanel.getTileSize());
    }

    /**
     * Handles picking up an object in the game, adjusting points and
     * updating messages based on the type of object collected.
     *
     * @param index the index of the object to interact with
     */
    public void pickObject(int index) {
        if (index != 999) {
            String objName = gamePanel.object[index].getName();

            switch (objName) {
                case "Clover" -> handleClover(index);
                case "Carrot" -> handleCarrot(index);
                case "Mushroom" -> handleMushroom(index);
                case "ExitDoor" -> handleExitDoor();
            }
        }
    }

    /**
     * Handles the collection of a "Clover" object.
     * Plays a sound effect, increments the clover count, adds points,
     * and displays a reward message. If the player meets the winning
     * conditions, a special sound effect is played.
     *
     * @param index the index of the Clover object in the gamePanel's object array
     */
    private void handleClover(int index) {
        gamePanel.playSoundEffect(1);
        hasClover++;
        gamePanel.object[index] = null;
        points += 50;
        gamePanel.ui.showMessage("YOU GOT A REWARD!", Color.green);

        if (hasClover == winningClovers && points >= winningPoints) {
            gamePanel.playSoundEffect(4);
        }
    }

    /**
     * Handles the collection of a "Carrot" object.
     * Plays a bonus sound effect, increments the carrot count, adds points,
     * and displays a bonus reward message.
     *
     * @param index the index of the Carrot object in the gamePanel's object array
     */
    private void handleCarrot(int index) {
        gamePanel.playSoundEffect(2);
        hasCarrot++;
        gamePanel.object[index] = null;
        points += 100;
        gamePanel.ui.showMessage("YOU GOT A BONUS REWARD!", Color.green);
    }

    /**
     * Handles the collection of a "Mushroom" object.
     * Plays a penalty sound effect, reduces points, removes the object,
     * and displays a warning message indicating a poison mushroom was found.
     *
     * @param index the index of the Mushroom object in the gamePanel's object array
     */
    private void handleMushroom(int index) {
        gamePanel.playSoundEffect(3);
        gamePanel.object[index] = null;
        points -= 100;
        gamePanel.ui.showMessage("YOU FOUND A POISON MUSHROOM!", Color.red);
    }

    /**
     * Handles interactions with the "ExitDoor" object.
     * Determines if the player has enough points and clovers to win:
     * - If conditions are met, stops the music, plays a winning sound,
     *   and transitions the game to the "You Won" state.
     * - If not enough points, displays a message indicating more points are needed.
     * - If not enough clovers, displays the number of clovers still required.
     */
    private void handleExitDoor() {
        if (points >= winningPoints && hasClover == winningClovers) {
            gamePanel.stopMusic();
            gamePanel.playSoundEffect(6);
            gamePanel.setGameState(gamePanel.youWonState);
        } else if (points < winningPoints) {
            gamePanel.ui.showMessage("YOU NEED MORE POINTS TO WIN!", Color.red);
        } else if (hasClover < winningClovers) {
            int remainingClover = winningClovers - hasClover;
            gamePanel.ui.showMessage("YOU NEED " + remainingClover + " MORE CLOVERS TO EXIT!", Color.red);
        }
    }

    /**
     * Draws the player with the appropriate sprite image based on the
     * direction and animation frame.
     *
     * @param g2 the graphics context used to draw the player
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = getSpriteImage();
        g2.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    /**
     * Returns the appropriate sprite image based on direction and animation frame.
     */
    private BufferedImage getSpriteImage() {
        return switch (direction) {
            case "left" -> (spriteNumber == 1) ? left1 : left2;
            case "right" -> (spriteNumber == 1) ? right1 : right2;
            case "up" -> (spriteNumber == 1) ? up1 : up2;
            case "down" -> (spriteNumber == 1) ? down1 : down2;
            default -> null;
        };
    }
    /**
     * Returns the screen's X position.
     *
     * @return the screen's X coordinate.
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * Returns the screen's Y position.
     *
     * @return the screen's Y coordinate.
     */
    public int getScreenY() {
        return screenY;
    }

    /**
     * Returns the number of clovers the character has.
     *
     * @return the number of clovers the character has.
     */
    public int getHasClover() {
        return hasClover;
    }

    /**
     * Sets the number of clovers the character has.
     *
     * @param hasClover the number of clovers the character should have.
     */
    public void setHasClover(int hasClover) {
        this.hasClover = hasClover;
    }

    /**
     * Returns the number of carrots the character has.
     *
     * @return the number of carrots the character has.
     */
    public int getHasCarrot() {
        return hasCarrot;
    }

    /**
     * Sets the number of carrots the character has.
     *
     * @param hasCarrot the number of carrots the character should have.
     */
    public void setHasCarrot(int hasCarrot) {
        this.hasCarrot = hasCarrot;
    }

    /**
     * Returns the points the character has earned.
     *
     * @return the points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points the character has earned.
     *
     * @param points the new points value.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Retrieves the object representing the open exit door in the game.
     *
     * @return the {@code ObjExitDoor} instance representing the open door.
     */
    public ObjExitDoor getOpenDoor() {
        return openDoor;
    }

}
