package mainTest;

import com.project.RabbitRun.main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.project.RabbitRun.main.AssetSetter;
import com.project.RabbitRun.Object.ObjBonusReward;
import com.project.RabbitRun.Object.ObjPunishment;
import com.project.RabbitRun.Object.ObjReward;

public class AssetSetterTest {

    private GamePanel gamePanel;
    private AssetSetter assetSetter;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
        assetSetter = new AssetSetter(gamePanel);
    }

    /*
    @Test
    public void testInitialization() {
        assertNotNull(assetSetter, "AssetSetter should be initialized properly");
        assertEquals(gamePanel, assetSetter, "AssetSetter should reference the correct GamePanel");
    }
    */

    /*
    @Test
    public void testSetObjectPlacesBonusReward() {
        assetSetter.setObject();
        assertTrue(gamePanel.getObjects()[0] instanceof ObjBonusReward, "First object should be a bonus reward");
        assertEquals(3 * 48, gamePanel.getObjects()[0].getWorldX(), "Bonus reward should be placed at the correct X coordinate");
        assertEquals(7 * 48, gamePanel.getObjects()[0].getWorldY(), "Bonus reward should be placed at the correct Y coordinate");
    }
    */


    @Test
    public void testSetObjectPlacesCorrectTypes() {
        assetSetter.setObject();

        // Check that specific indices in the object array contain the expected object types
        assertInstanceOf(ObjReward.class, gamePanel.object[0], "First object should be an ObjReward");
        assertInstanceOf(ObjPunishment.class, gamePanel.object[1], "Second object should be an ObjReward");
        assertInstanceOf(ObjBonusReward.class, gamePanel.object[2], "Third object should be an ObjBonusReward");
        assertInstanceOf(ObjReward.class, gamePanel.object[3], "Fourth object should be an ObjReward");
    }

    @Test
    public void testSetObjectPlacesCorrectCoordinates() {
        assetSetter.setObject();

        // Verify object coordinates
        assertEquals(17 * gamePanel.getTileSize(), gamePanel.object[0].getWorldX(), "ObjReward X coordinate should match");
        assertEquals(9 * gamePanel.getTileSize(), gamePanel.object[0].getWorldY(), "ObjReward Y coordinate should match");

        assertEquals(16 * gamePanel.getTileSize(), gamePanel.object[1].getWorldX(), "ObjExitDoor X coordinate should match");
        assertEquals(17 * gamePanel.getTileSize(), gamePanel.object[1].getWorldY(), "ObjExitDoor Y coordinate should match");

        assertEquals(0 * gamePanel.getTileSize(), gamePanel.object[2].getWorldX(), "ObjPunishment X coordinate should match");
        assertEquals(0 * gamePanel.getTileSize(), gamePanel.object[2].getWorldY(), "ObjPunishment Y coordinate should match");

        assertEquals(14 * gamePanel.getTileSize(), gamePanel.object[3].getWorldX(), "ObjReward X coordinate should match");
        assertEquals(21 * gamePanel.getTileSize(), gamePanel.object[3].getWorldY(), "ObjReward Y coordinate should match");
    }

    @Test
    public void testSetObjectDoesNotExceedMaxObjects() {
        assetSetter.setObject();

        // Verify that only the allocated array length is used
        assertEquals(20, gamePanel.object.length, "GamePanel object array should have a maximum of 20 slots");
        for (int index = 18; index < gamePanel.object.length; index++) {
            assertNull(gamePanel.object[index], "Remaining slots in the object array should be null");
        }
    }

    @Test
    public void testObjectsAreNotNull() {
        assetSetter.setObject();

        // Ensure objects are initialized
        for (int index = 0; index < 15; index++) {
            if (index == 5)
                continue;
            assertNotNull(gamePanel.object[index], "Object at index " + index + " should not be null after initialization");
        }
    }
}
