package com.project.RabbitRun.tile;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

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
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/8bitgrass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/block.png")));


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        g2.drawImage(tile[1].image, 0,0, gamePanel.tileSize, gamePanel.tileSize, null);
    }



}
