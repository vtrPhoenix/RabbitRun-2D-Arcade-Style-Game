package EntityTest;

import com.project.RabbitRun.Entity.Player;
import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private Player player;

    @BeforeEach
    public void setUp() {
        // Initialize GamePanel and KeyHandler objects
        gamePanel = new GamePanel();
        keyHandler = new KeyHandler(gamePanel);
        player = new Player(gamePanel, keyHandler);

        // Set up any necessary state for the player or GamePanel
        player.setDefaultValues();
    }

    @Test
    public void testPlayerInitialization() {
        // Check if player is initialized correctly
        assertNotNull(player, "Player should be initialized");
        assertEquals(gamePanel.getTileSize() * 10, player.getWorldX(), "Player's initial X position should be correct");
        assertEquals(gamePanel.getTileSize() * 6, player.getWorldY(), "Player's initial Y position should be correct");
        assertEquals(4,player.getSpeed(), "Player's initial speed should be 4");

    }

    @Test
    public void testPlayerMovement() {
        // Simulate pressing the right arrow key
        keyHandler.setRightPressed(true);
        int initialX = player.getWorldX();

        // Update the player state
        player.update();

        // Verify the player moved to the right
        assertTrue(player.getWorldX() > initialX, "Player should move to the right when the right key is pressed");

        // Reset keys and move left
        keyHandler.setRightPressed(false);
        keyHandler.setLeftPressed(true);
        initialX = player.getWorldX();
        player.update();
        assertTrue(player.getWorldX() < initialX, "Player should move to the left when the left key is pressed");

        keyHandler.setLeftPressed(false);
        keyHandler.setUpPressed(true);
        int initialY = player.getWorldY();
        player.update();
        assertTrue(player.getWorldY() < initialY, "Player should move to the north when the up key is pressed");

        keyHandler.setUpPressed(false);
        keyHandler.setDownPressed(true);
        initialY = player.getWorldY();
        player.update();
        assertTrue(player.getWorldY() > initialY, "Player should move to the south when the down key is pressed");

    }

    @Test
    public void testPlayerRestart() {
        // Simulate the player collecting items and gaining points
        player.points = 100;
        player.setHasClover(3);
        player.setHasCarrot(2);

        // Restart the player
        player.restart();

        // Verify player state is reset
        assertEquals(0, player.points, "Player points should be reset to 0");
        assertEquals(0, player.getHasClover(), "Player clover count should be reset to 0");
        assertEquals(0, player.getHasCarrot(), "Player carrot count should be reset to 0");
    }

    @Test
    public void testPlayerImageLoading() {
        assertDoesNotThrow(() -> player.getPlayerImage(), "Loading player images should not throw an exception");
    }

    @Test
    public void testDirectionChange() {
        keyHandler.setRightPressed(true);
        player.update();
        assertEquals("right", player.getDirection(), "Player's direction should be 'right' when right key is pressed");

        keyHandler.setRightPressed(false);
        keyHandler.setLeftPressed(true);
        player.update();
        assertEquals("left", player.getDirection(), "Player's direction should be 'left' when left key is pressed");
    }

}
