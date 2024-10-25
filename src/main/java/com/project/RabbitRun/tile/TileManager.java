package com.project.RabbitRun.tile;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage()
    {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResource("/Tiles/8bitgrass.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
