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

    public Player(GamePanel gamePanel , KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        posX = 100;
        posY = 100;
        speed = 4;
        direction = "left";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up1.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left1.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyHandler.upPressed){
            direction = "up";
            posY -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            posY += speed;
        }else if (keyHandler.leftPressed){
            direction = "left";
            posX -= speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            posX += speed;
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(posX, posY, gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage image = null;

        switch (direction){
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            default:
                break;
        }
        g2.drawImage(image, posX, posY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
