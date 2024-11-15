package mainTest;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class KeyHandlerTest {

    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    @BeforeEach
    public void setUp() {
        // Initialize a GamePanel instance (can be a mock if needed)
        gamePanel = new GamePanel();
        keyHandler = new KeyHandler(gamePanel);
    }

    @Test
    public void testKeyPressed() {
        // Simulate pressing the up key
        KeyEvent upKeyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        keyHandler.keyPressed(upKeyEvent);
        assertTrue(keyHandler.isUpPressed(), "Up key should be pressed");

        // Simulate pressing the down key
        KeyEvent downKeyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        keyHandler.keyPressed(downKeyEvent);
        assertTrue(keyHandler.isDownPressed(), "Down key should be pressed");

        // Simulate pressing the pause key
        gamePanel.setGameState(gamePanel.playState);
        KeyEvent pauseKeyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(pauseKeyEvent);
        assertEquals(gamePanel.pauseState, gamePanel.getGameState(), "Game should be in pause state after pressing 'P'");
    }

    @Test
    public void testKeyReleased() {
        // Simulate releasing the right key
        keyHandler.keyPressed(new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
        keyHandler.keyReleased(new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
        assertFalse(keyHandler.isRightPressed(), "Right key should be released");
    }

    @Test
    public void testPauseToggle() {
        // Test initial play to pause toggle
        gamePanel.setGameState(gamePanel.playState);
        KeyEvent pauseKeyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(pauseKeyEvent);
        assertEquals(gamePanel.pauseState, gamePanel.getGameState(), "Game state should toggle to pause");

        // Test pause to play toggle
        keyHandler.keyPressed(pauseKeyEvent);
        assertEquals(gamePanel.playState, gamePanel.getGameState(), "Game state should toggle to play");
    }

    @Test
    public void testGuideStateKeyPress() {
        // Set game state to guide and press 'B'
        gamePanel.setGameState(gamePanel.guideState);
        KeyEvent guideKeyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_B, 'B');
        keyHandler.keyPressed(guideKeyEvent);
        assertEquals(gamePanel.menuState, gamePanel.getGameState(), "Game state should switch to menu when 'B' is pressed in guide state");
    }
}
