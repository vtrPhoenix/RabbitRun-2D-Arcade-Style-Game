package com.project.RabbitRun.main;

import com.project.RabbitRun.Entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX - entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY - entity.solidArea.height + entity.solidArea.y;

        int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
        int entityRightCol = entityRightWorldX/gamePanel.tileSize;
        int entityTopRow = entityTopWorldY/gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileM.maptilenum[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileM.maptilenum[entityRightCol][entityTopRow];

            if(gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
            
            break;
        case "down":

            break;

        case "left":

            break;

        case "right":

            break;

    }

    }

}




