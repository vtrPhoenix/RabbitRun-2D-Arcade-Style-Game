package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.CollisionChecker;
import com.project.RabbitRun.main.GamePanel;
import org.w3c.dom.css.Rect;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity{

    GamePanel gamePanel;

    //public final int screenX;
    //public final int screenY;

    //CollisionChecker collisionChecker;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        //this.collisionChecker = collisionChecker;

        //screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
        //screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 20;; // will change
        worldY = gamePanel.tileSize * 20;; // will change
        speed = 4;
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

        int deltaX = player.worldX - this.worldX;
        int deltaY = player.worldY - this.worldY;
        int moveX = 0;
        int moveY = 0;

        double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        // Check for collision in the new position
        if (!collisionOn && distance != 0) {
            // Update position if no collision detected
            moveX = (int) (speed * (deltaX / distance));
            moveY = (int) (speed * (deltaY / distance));
            this.worldX += moveX;
            this.worldY += moveY;
        }

        if (collisionWithPlayer(player)){
            handleCollision();
        }

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
        sprintCounter++;
        if (sprintCounter > 13) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            }
            else if (spriteNumber == 2){
                spriteNumber = 1;
            }
            sprintCounter = 0;
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
        }
        g.drawImage(image, worldX, worldY ,gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
