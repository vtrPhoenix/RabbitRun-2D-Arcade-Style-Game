package com.project.RabbitRun.main;

import com.project.RabbitRun.Object.ObjBonusReward;
import com.project.RabbitRun.Object.ObjExitDoor;
import com.project.RabbitRun.Object.ObjPunishment;
import com.project.RabbitRun.Object.ObjReward;

/**
 * Responsible for initializing and placing game objects on the game board
 * in the "Rabbit Run" game. This includes rewards, bonus rewards, punishments,
 * and the exit door.
 */
public class AssetSetter {
    /** Reference to the main game panel, which contains the game state and objects. */
    GamePanel gamePanel;

    /**
     * Constructs an {@code AssetSetter} with a reference to the {@code GamePanel}.
     *
     * @param gamePanel the game panel to which objects will be added
     */
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Places various game objects at specific coordinates on the game board.
     * Objects include rewards, bonus rewards, and punishments, each assigned
     * a specific location to appear in the game.
     */
    public void setObject() {
        gamePanel.object[0] = new ObjReward();
        gamePanel.object[0].worldX = 17 * gamePanel.getTileSize();
        gamePanel.object[0].worldY = 9 * gamePanel.getTileSize();

        gamePanel.object[1] = new ObjReward();
        gamePanel.object[1].worldX = 22 * gamePanel.getTileSize();
        gamePanel.object[1].worldY = 16 * gamePanel.getTileSize();

        gamePanel.object[2] = new ObjBonusReward();

        gamePanel.object[3] = new ObjReward();
        gamePanel.object[3].worldX = 14 * gamePanel.getTileSize();
        gamePanel.object[3].worldY = 21 * gamePanel.getTileSize();

        gamePanel.object[4] = new ObjPunishment();
        gamePanel.object[4].worldX = 16 * gamePanel.getTileSize();
        gamePanel.object[4].worldY = 17 * gamePanel.getTileSize();

        gamePanel.object[6] = new ObjReward();
        gamePanel.object[6].worldX = 23 * gamePanel.getTileSize();
        gamePanel.object[6].worldY = 22 * gamePanel.getTileSize();

        gamePanel.object[7] = new ObjBonusReward();

        gamePanel.object[8] = new ObjReward();
        gamePanel.object[8].worldX = 29 * gamePanel.getTileSize();
        gamePanel.object[8].worldY = 32 * gamePanel.getTileSize();

        gamePanel.object[9] = new ObjPunishment();
        gamePanel.object[9].worldX = 34 * gamePanel.getTileSize();
        gamePanel.object[9].worldY = 27 * gamePanel.getTileSize();

        gamePanel.object[10] = new ObjPunishment();
        gamePanel.object[10].worldX = 35 * gamePanel.getTileSize();
        gamePanel.object[10].worldY = 20 * gamePanel.getTileSize();

        gamePanel.object[11] = new ObjBonusReward();

        gamePanel.object[12] = new ObjReward();
        gamePanel.object[12].worldX = 21 * gamePanel.getTileSize();
        gamePanel.object[12].worldY = 27 * gamePanel.getTileSize();

        gamePanel.object[13] = new ObjReward();
        gamePanel.object[13].worldX = 38 * gamePanel.getTileSize();
        gamePanel.object[13].worldY = 11 * gamePanel.getTileSize();

        gamePanel.object[14] = new ObjReward();
        gamePanel.object[14].worldX = 29 * gamePanel.getTileSize();
        gamePanel.object[14].worldY = 8 * gamePanel.getTileSize();

        gamePanel.object[15] = new ObjBonusReward();
    }
}
