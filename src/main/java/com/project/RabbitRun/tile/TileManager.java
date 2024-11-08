package com.project.RabbitRun.tile;

import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Manages tiles in the "Rabbit Run" game, including loading tile images,
 * reading map data, and drawing tiles within the visible area of the game.
 */
public class TileManager {

    /** The main game panel that contains the game state and settings. */
    GamePanel gamePanel;
    /** Array to hold different types of tiles. */
    public Tile[] tile;
    /** 2D array representing the tile map, where each element refers to a tile index. */
    public int[][] mapTileNum;

    /**
     * Main constructor for the tile manager. Initializes the tile array with a maximum of 10 tiles
     * and sets the dimensions of the {@code mapTileNum} array based on the game's world size.
     *
     * @param gamePanel the main game panel containing game settings and state
     */
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
        getTileImage();
        loadMap("/maps/map03.txt");
    }

    /**
     * Loads tile images and sets their collision properties. Adds images to the tile array.
     * Currently loads a grass tile and a tree tile.
     */
    public void getTileImage()
    {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Tiles/tree2.png")));
            tile[1].collision = true;

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads a map file and populates the {@code mapTileNum} array with tile indices.
     * Each value in the file corresponds to a specific tile type.
     *
     * @param path the path to the map file containing tile indices
     */
    public void loadMap(String path)
    {
        try{
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()){

                String line = br.readLine();

                while(col < gamePanel.getMaxWorldCol())
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.getMaxWorldCol())
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e) {
            // Exception handling
        }
    }

    /**
     * Draws tiles on the screen within the bounds of the player's visible area.
     * Uses the {@code mapTileNum} array to determine which tiles to draw.
     *
     * @param g2 the {@code Graphics2D} context used for drawing
     */
    public void draw(Graphics2D g2)
    {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow())
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.getScreenX();
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.getScreenY();

            if(worldX + (2*gamePanel.getTileSize()) > gamePanel.player.worldX - gamePanel.player.getScreenX() && worldY+(2*gamePanel.getTileSize()) > gamePanel.player.worldY - gamePanel.player.getScreenY()
            && worldX - (2*gamePanel.getTileSize()) < gamePanel.player.worldX + gamePanel.player.getScreenX() && worldY -(2*gamePanel.getTileSize()) < gamePanel.player.worldY + gamePanel.player.getScreenY())
                g2.drawImage(tile[tileNum].image, screenX, screenY ,gamePanel.getTileSize(), gamePanel.getTileSize(), null);

            worldCol++;


            if (worldCol == gamePanel.getMaxWorldCol())
            {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
