package com.project.RabbitRun.tile;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;
    int maptilenum[][];


    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        maptilenum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadmap("/maps/map02.txt");
    }

    public void getTileImage()
    {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/block.png")));


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadmap(String path)
    {
        try{
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){

                String line = br.readLine();

                while(col < gamePanel.maxWorldCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    maptilenum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();


        }
        catch(Exception e)
        {

        }
    }


    public void draw(Graphics2D g2)
    {
        g2.drawImage(tile[0].image, 0,0, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawImage(tile[0].image, 48,0, gamePanel.tileSize, gamePanel.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow)
        {
            int tilenum = maptilenum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;


            g2.drawImage(tile[tilenum].image, screenX, screenY ,gamePanel.tileSize, gamePanel.tileSize, null);
            worldCol++;


            if (worldCol == gamePanel.maxWorldCol)
            {
                worldCol = 0;

                worldRow++;

            }
        }


    }



}
