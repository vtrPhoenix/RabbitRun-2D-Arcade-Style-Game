package com.project.RabbitRun.Entity;

import com.project.RabbitRun.Object.ObjExitDoor;
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
    int hasClover = 0;
    int hasCarrot = 0;
    public int points = 0;
    private final int winningPoints = 100;
    private ObjExitDoor openDoor;

    public Player(GamePanel gamePanel , KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 25;
        openDoor = new ObjExitDoor(true);
        setDefaultValues();
        getPlayerImage();
    }

    public void restart(){
        setDefaultValues();
        points = 0;
        hasClover = 0;
        hasCarrot = 0;
    }

    public void setDefaultValues() {
        //change the starting location of the rabbit by changing the integer values. ex 1, 1 puts it the rabbit in the first box
        worldX = gamePanel.tileSize * 10;
        worldY = gamePanel.tileSize * 6;
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

            } else if (keyHandler.downPressed) {
                direction = "down";

            }else if (keyHandler.leftPressed){
                direction = "left";

            } else {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickObject(objIndex);

            //if collision is false, player can move
            if(collisionOn == false)
            {
                switch (direction){
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



            sprintCounter++;
            if(sprintCounter > 13){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                }else if(spriteNumber == 2){
                    spriteNumber = 1;
                }
                sprintCounter = 0;
            }
            if(points < 0){
                gamePanel.gameState = gamePanel.youLostState;
            }
            if(points >=winningPoints){
                gamePanel.object[5] = openDoor;
                gamePanel.object[5].worldX = 38 * gamePanel.tileSize;
                gamePanel.object[5].worldY = 32 * gamePanel.tileSize;
            }
        }
    }

    public void pickObject (int index) {
        if (index != 999) {
            //gamePanel.object[index] = null;
            String objName = gamePanel.object[index].name;

            switch (objName) {
                case "Clover" :
                    hasClover++;
                    gamePanel.object[index] = null;
                    points += 50;
                    gamePanel.ui.showMessage("YOU GOT A REWARD!",Color.green);
                    break;
                case "Carrot" :
                    hasCarrot++;
                    gamePanel.object[index] = null;
                    points += 100;
                    gamePanel.ui.showMessage("YOU GOT A BONUS REWARD!",Color.green);
                    break;
                case "Mushroom" :
                    gamePanel.object[index] = null;
                    points -= 100;
                    gamePanel.ui.showMessage("YOU FOUND A POISON MUSHROOM!",Color.red);
                    break;
                case "ExitDoor" :
                    if(points >= winningPoints){
                        gamePanel.gameState = gamePanel.youWonState;
                    }
                    break;
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
