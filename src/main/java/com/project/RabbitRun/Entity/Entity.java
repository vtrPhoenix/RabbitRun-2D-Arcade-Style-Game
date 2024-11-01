package com.project.RabbitRun.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents an entity within the game, including attributes for
 * position, speed, directional images, and collision handling.
 */
public class Entity {

    /** X-coordinate of the entity's position in the game world. */
    public int worldX;

    /** Y-coordinate of the entity's position in the game world. */
    public int worldY;

    /** Speed at which the entity moves. */
    public int speed;

    /** Image for moving up (first frame). */
    public BufferedImage up1;

    /** Image for moving down (first frame). */
    public BufferedImage down1;

    /** Image for moving left (first frame). */
    public BufferedImage left1;

    /** Image for moving right (first frame). */
    public BufferedImage right1;

    /** Image for moving up (second frame). */
    public BufferedImage up2;

    /** Image for moving down (second frame). */
    public BufferedImage down2;

    /** Image for moving left (second frame). */
    public BufferedImage left2;

    /** Image for moving right (second frame). */
    public BufferedImage right2;

    /** Current direction the entity is facing, such as "up", "down", "left", or "right". */
    public String direction;

    /** Counter for managing sprint animations. */
    public int sprintCounter = 0;

    /** Current frame of the sprite animation (either 1 or 2). */
    public int spriteNumber = 1;

    /** Rectangle representing the solid area for collision detection. */
    public Rectangle solidArea;

    /** Default X-coordinate of the solid area's position. */
    public int solidAreaDefaultX;

    /** Default Y-coordinate of the solid area's position. */
    public int solidAreaDefaultY;

    /** Indicates whether a collision is currently occurring. */
    public boolean collisionOn = false;
}
