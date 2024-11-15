package mainTest;

import com.project.RabbitRun.main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

public class GamePanelTest {

    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    public void testInitialization() {
        // Test initial game state
        assertEquals(gamePanel.menuState, gamePanel.getGameState(), "Game should start in the menu state.");

        // Test screen settings
        assertEquals(768, gamePanel.getScreenWidth(), "Screen width should be correctly set.");
        assertEquals(576, gamePanel.getScreenHeight(), "Screen height should be correctly set.");

        // Test that essential components are initialized
        assertNotNull(gamePanel.getPlayer(), "Player should be initialized.");
//        assertNotNull(gamePanel.tileM, "TileManager should be initialized.");
        assertNotNull(gamePanel.enemies, "Enemies list should be initialized.");
    }

    @Test
    public void testGameStateChange() {
        // Change to play state
        gamePanel.setGameState(gamePanel.playState);
        assertEquals(gamePanel.playState, gamePanel.getGameState(), "Game state should be set to play state.");

        // Change to pause state
        gamePanel.setGameState(gamePanel.pauseState);
        assertEquals(gamePanel.pauseState, gamePanel.getGameState(), "Game state should be set to pause state.");
    }

    @Test
    public void testStartGameThread() {
        // Start the game thread
        gamePanel.startGameThread();
        assertNotNull(gamePanel.getGameThread(), "Game thread should be initialized after starting.");
        assertTrue(gamePanel.getGameThread().isAlive(), "Game thread should be alive after starting.");
    }

//    @Test
//    public void testGameLoop() {
//        // Mocking a Runnable method to verify game loop execution
//        Thread mockThread = Mockito.mock(Thread.class);
//        gamePanel.gameThread = mockThread;
//
//        // Simulate game loop running
//        doNothing().when(mockThread).run();
//        gamePanel.run();
//        verify(mockThread, times(1)).run();
//    }
}
