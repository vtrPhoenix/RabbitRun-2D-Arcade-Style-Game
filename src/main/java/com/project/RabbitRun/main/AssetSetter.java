package com.project.RabbitRun.main;

import com.project.RabbitRun.Object.ObjBonusReward;
import com.project.RabbitRun.Object.ObjExitDoor;
import com.project.RabbitRun.Object.ObjPunishment;
import com.project.RabbitRun.Object.ObjReward;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.object[0] = new ObjReward();
        gamePanel.object[0].worldX = 7 * gamePanel.tileSize;
        gamePanel.object[0].worldY = 4 * gamePanel.tileSize;

        gamePanel.object[1] = new ObjReward();
        gamePanel.object[1].worldX = 12 * gamePanel.tileSize;
        gamePanel.object[1].worldY = 10 * gamePanel.tileSize;

        gamePanel.object[2] = new ObjBonusReward();
        gamePanel.object[2].worldX = 12 * gamePanel.tileSize;
        gamePanel.object[2].worldY = 3 * gamePanel.tileSize;

        gamePanel.object[3] = new ObjBonusReward();
        gamePanel.object[3].worldX = 4 * gamePanel.tileSize;
        gamePanel.object[3].worldY = 15 * gamePanel.tileSize;

        gamePanel.object[4] = new ObjPunishment();
        gamePanel.object[4].worldX = 9 * gamePanel.tileSize;
        gamePanel.object[4].worldY = 11 * gamePanel.tileSize;

        gamePanel.object[5] = new ObjExitDoor();
        gamePanel.object[5].worldX = 25 * gamePanel.tileSize;
        gamePanel.object[5].worldY = 25 * gamePanel.tileSize;
    }
}
