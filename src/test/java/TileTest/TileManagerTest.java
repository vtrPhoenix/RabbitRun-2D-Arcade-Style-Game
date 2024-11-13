package TileTest;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.tile.Tile;
import com.project.RabbitRun.tile.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileManagerTest {

    GamePanel gamePanel;
    TileManager tileManager;


    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
        tileManager = new TileManager(gamePanel);
    }

    @Test
    /** tests gettileimage() and loadmap() methods in the tile managaer class.
     * if both methods work correctly the mapTileNum array will contain the values 0 or 1 at every index
     */
    void testloadMap() {

        for(int i = 0; i < gamePanel.getMaxWorldCol(); i++)
        {
            for(int j = 0; j < gamePanel.getMaxWorldRow(); j++)
            {
                assertTrue(tileManager.mapTileNum[i][j] == 0 || tileManager.mapTileNum[i][j] == 1, "The values in" +
                        " the mapTileNum array" +
                        "are either 0 or 1 as read from the input file");
            }
        }
    }
}