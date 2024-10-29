package com.project.RabbitRun.tile;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];


    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/maps/map02.txt");
    }

    public void getTileImage()
    {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/block.png")));
            tile[1].collision = true;

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String path)
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
                    mapTileNum[col][row] = num;
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
        //g2.drawImage(tile[0].image, 0,0, gamePanel.tileSize, gamePanel.tileSize, null);
        //g2.drawImage(tile[0].image, 48,0, gamePanel.tileSize, gamePanel.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow)
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if(worldX + (2*gamePanel.tileSize) > gamePanel.player.worldX - gamePanel.player.screenX && worldY+(2*gamePanel.tileSize) > gamePanel.player.worldY - gamePanel.player.screenY
            && worldX - (2*gamePanel.tileSize) < gamePanel.player.worldX + gamePanel.player.screenX && worldY -(2*gamePanel.tileSize) < gamePanel.player.worldY + gamePanel.player.screenY)
                g2.drawImage(tile[tileNum].image, screenX, screenY ,gamePanel.tileSize, gamePanel.tileSize, null);

            worldCol++;


            if (worldCol == gamePanel.maxWorldCol)
            {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
