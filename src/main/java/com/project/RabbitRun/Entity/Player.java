package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gamePanel , KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize/2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 12;
        worldY = gamePanel.tileSize * 12;
        speed = 4;
        direction = "left";
    }

    public void getPlayerImage(){
        try{

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

    public void update() {
        if(keyHandler.upPressed || keyHandler.leftPressed || keyHandler.downPressed || keyHandler.rightPressed){
            if(keyHandler.upPressed){
                direction = "up";
                worldY -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed;
            }else if (keyHandler.leftPressed){
                direction = "left";
                worldX -= speed;
            } else {
                direction = "right";
                worldX += speed;
            }

            sprintCounter++;
            if(sprintCounter > 13){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                }else if(spriteNumber == 2){
                    spriteNumber = 1;
                }
                sprintCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction){
            case "left":
                if(spriteNumber == 1)
                    image = left1;
                else if(spriteNumber == 2)
                    image = left2;
                break;
            case "right":
                if(spriteNumber == 1)
                    image = right1;
                else if(spriteNumber == 2)
                    image = right2;
                break;
            case "up":
                if(spriteNumber == 1)
                    image = up1;
                else if(spriteNumber == 2)
                    image = up2;
                break;
            case "down":
                if(spriteNumber == 1)
                    image = down1;
                else if(spriteNumber == 2)
                    image = down2;
                break;
            default:
                break;
        }
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
