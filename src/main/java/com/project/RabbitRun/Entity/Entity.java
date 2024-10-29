package com.project.RabbitRun.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1,down1,left1,right1,up2,down2,left2,right2;
    public String direction;

    public int sprintCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

}
