package com.project.RabbitRun.main;

import com.project.RabbitRun.Entity.Entity;

/**
 * Handles collision detection in the "Rabbit Run" game, checking if entities
 * collide with tiles or objects on the game board.
 */
public class CollisionChecker {

    /** Reference to the main game panel, which contains game state and settings. */
    GamePanel gamePanel;

    /**
     * Constructs a CollisionChecker with a reference to the game panel.
     *
     * @param gamePanel the game panel containing game settings and state
     */
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks if the specified entity collides with any tiles based on its direction and position.
     *
     * @param entity the entity (e.g., player or enemy) to check for tile collision
     */
    public void checkTile(Entity entity)
    {
        /** the left coordinate of an entity solid area */
        int entityLeftWorldX = entity.worldX +entity.solidArea.x;
        /**
         * the right coordinate of an entity solid area
         */
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        /**
         * the top coordinate of an entity solid area
         */
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        /**
         * the bottom coordinate of an entity solid area
         */
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
        int entityRightCol = entityRightWorldX/gamePanel.tileSize;
        int entityTopRow = entityTopWorldY/gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;

        int tileNum1;
        int tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision)
                {
                    entity.collisionOn = true;
                }

                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision)
                {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision)
                {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision)
                {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**
     * Checks if the specified entity collides with any objects in the game.
     *
     * @param entity the entity to check for object collision
     * @param player specifies if the entity is the player character
     * @return the index of the object that the entity collides with, or 999 if no collision occurs
     */
    public int checkObject (Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gamePanel.object.length; i++) {
            if (gamePanel.object[i] != null) {

                // Getting entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Getting object's solid area position
                gamePanel.object[i].solidArea.x = gamePanel.object[i].worldX + gamePanel.object[i].solidArea.x;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].worldY + gamePanel.object[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                // Reset solid area positions to defaults
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.object[i].solidArea.x = gamePanel.object[i].solidAreaDefaultX;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
