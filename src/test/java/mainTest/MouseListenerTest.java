package mainTest;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.mouseListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MouseListenerTest {


    private GamePanel gamePanel;
    private mouseListener mouseListener;

    @BeforeEach
    public void setUp() {
        // Initialize GamePanel and mouseListener
        gamePanel = new GamePanel();
        mouseListener = new mouseListener(gamePanel);
    }

    @Test
    public void testGuideButtonClickInMenuState() {
        // Set initial game state to menuState
        gamePanel.setGameState(gamePanel.menuState);

        // Simulate mouse click within guide button bounds
        MouseEvent clickEvent = new MouseEvent(gamePanel, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 60, 270, 1, false);
        mouseListener.mousePressed(clickEvent);

        // Verify that the game state changes to guideState
        assertEquals(gamePanel.guideState, gamePanel.getGameState(), "Game state should be set to guideState after clicking guide button");
    }

    @Test
    public void testStartButtonClickInMenuState() {
        // Set initial game state to menuState
        gamePanel.setGameState(gamePanel.menuState);

        // Simulate mouse click within start button bounds
        MouseEvent clickEvent = new MouseEvent(gamePanel, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 310, 270, 1, false);
        mouseListener.mousePressed(clickEvent);

        // Verify that the game state changes to playState
        assertEquals(gamePanel.playState, gamePanel.getGameState(), "Game state should be set to playState after clicking start button");
    }

    @Test
    public void testPLayAgainButtonClickInYouLostState() {
        // Set initial game state to menuState
        gamePanel.setGameState(gamePanel.youLostState);

        // Simulate mouse click within start button bounds
        MouseEvent clickEvent = new MouseEvent(gamePanel, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 130, 520, 1, false);
        mouseListener.mousePressed(clickEvent);

        // Verify that the game state changes to playState
        assertEquals(gamePanel.playState, gamePanel.getGameState(), "Game state should be set to playState after clicking start button");
    }

}