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
        gamePanel.object[0].worldX = 17 * gamePanel.tileSize;
        gamePanel.object[0].worldY = 9 * gamePanel.tileSize;

        gamePanel.object[1] = new ObjReward();
        gamePanel.object[1].worldX = 22 * gamePanel.tileSize;
        gamePanel.object[1].worldY = 16 * gamePanel.tileSize;

        gamePanel.object[2] = new ObjBonusReward();
//        gamePanel.object[2].worldX = 24 * gamePanel.tileSize;
//        gamePanel.object[2].worldY = 6 * gamePanel.tileSize;

        gamePanel.object[3] = new ObjReward();
        gamePanel.object[3].worldX = 14 * gamePanel.tileSize;
        gamePanel.object[3].worldY = 21 * gamePanel.tileSize;

        gamePanel.object[4] = new ObjPunishment();
        gamePanel.object[4].worldX = 16 * gamePanel.tileSize;
        gamePanel.object[4].worldY = 17 * gamePanel.tileSize;

        gamePanel.object[6] = new ObjReward();
        gamePanel.object[6].worldX = 23 * gamePanel.tileSize;
        gamePanel.object[6].worldY = 22 * gamePanel.tileSize;

        gamePanel.object[7] = new ObjBonusReward();
//        gamePanel.object[7].worldX = 11 * gamePanel.tileSize;
//        gamePanel.object[7].worldY = 33 * gamePanel.tileSize;

        gamePanel.object[8] = new ObjReward();
        gamePanel.object[8].worldX = 29 * gamePanel.tileSize;
        gamePanel.object[8].worldY = 32 * gamePanel.tileSize;

        gamePanel.object[9] = new ObjPunishment();
        gamePanel.object[9].worldX = 34 * gamePanel.tileSize;
        gamePanel.object[9].worldY = 27 * gamePanel.tileSize;

        gamePanel.object[10] = new ObjPunishment();
        gamePanel.object[10].worldX = 35 * gamePanel.tileSize;
        gamePanel.object[10].worldY = 20 * gamePanel.tileSize;

        gamePanel.object[11] = new ObjBonusReward();
//        gamePanel.object[11].worldX = 38 * gamePanel.tileSize;
//        gamePanel.object[11].worldY = 8 * gamePanel.tileSize;

        gamePanel.object[12] = new ObjReward();
        gamePanel.object[12].worldX = 21 * gamePanel.tileSize;
        gamePanel.object[12].worldY = 27 * gamePanel.tileSize;

        gamePanel.object[13] = new ObjReward();
        gamePanel.object[13].worldX = 38 * gamePanel.tileSize;
        gamePanel.object[13].worldY = 11 * gamePanel.tileSize;

        gamePanel.object[14] = new ObjReward();
        gamePanel.object[14].worldX = 29 * gamePanel.tileSize;
        gamePanel.object[14].worldY = 8 * gamePanel.tileSize;

        gamePanel.object[15] = new ObjBonusReward();

    }
}
