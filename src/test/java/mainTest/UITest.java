package mainTest;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.UI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UI class in the RabbitRun game.
 */
class UITest {
    private UI ui;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        ui = new UI(gamePanel);
    }

    @Test
    void testShowMessage() {

        String testMessage = "Test Message";
        Color testColor = Color.RED;

        ui.showMessage(testMessage, testColor);


        assertEquals("Test Message", ui.getMessage());
        assertTrue(ui.isDispMessage());
        assertEquals(Color.RED, ui.getMessageColor());
        assertEquals(0, ui.getMessageTimer());
    }

    @Test
    void testSetStartTime() {
        long expectedStartTime = System.currentTimeMillis();

        ui.setStartTime(expectedStartTime);

        assertEquals(expectedStartTime, ui.getStartTime());
    }


}

