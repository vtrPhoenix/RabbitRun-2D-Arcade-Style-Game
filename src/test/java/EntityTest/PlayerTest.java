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
        player.setDefaultValues();  // Reset player to default state before each test
    }

    @Test
    public void testPlayerInitialization() {
        // Check if player is initialized correctly
        assertNotNull(player, "Player should be initialized");
        assertEquals(gamePanel.getTileSize() * 10, player.getWorldX(), "Player's initial X position should be correct");
        assertEquals(gamePanel.getTileSize() * 6, player.getWorldY(), "Player's initial Y position should be correct");
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
}
