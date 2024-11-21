package mainTest;

import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.main.Main;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainWindowCreation() {
        // Run the main method in a Swing thread to prevent conflicts
        SwingUtilities.invokeLater(() -> {
            // Simulate launching the application
            Main.main(new String[]{});

            // Get the currently active JFrame
            JFrame[] frames = (JFrame[]) JFrame.getFrames();
            assertTrue(frames.length > 0, "No JFrame was created.");

            JFrame window = frames[0];

            // Check if the title matches
            assertEquals("RabbitRun", window.getTitle(), "Window title is incorrect.");

            // Check if the window is set to close on exit
            assertEquals(JFrame.EXIT_ON_CLOSE, window.getDefaultCloseOperation(), "Close operation is not set to EXIT_ON_CLOSE.");

            // Check if the window is not resizable
            assertFalse(window.isResizable(), "Window should not be resizable.");

            // Check if the window is visible
            assertTrue(window.isVisible(), "Window is not visible.");
        });
    }

    @Test
    void testGamePanelAdded() {
        SwingUtilities.invokeLater(() -> {
            // Simulate launching the application
            Main.main(new String[]{});

            // Get the currently active JFrame
            JFrame window = (JFrame) JFrame.getFrames()[0];

            // Verify the GamePanel is added to the JFrame
            Component[] components = window.getContentPane().getComponents();
            assertTrue(components.length > 0, "No components were added to the JFrame.");
            assertInstanceOf(GamePanel.class, components[0], "The first component is not a GamePanel.");
        });
    }

    @Test
    void testWindowCenteredOnScreen() {
        SwingUtilities.invokeLater(() -> {
            // Simulate launching the application
            Main.main(new String[]{});

            // Get the currently active JFrame
            JFrame window = (JFrame) JFrame.getFrames()[0];

            // Verify the window is centered on the screen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle bounds = window.getBounds();

            int expectedX = (screenSize.width - bounds.width) / 2;
            int expectedY = (screenSize.height - bounds.height) / 2;

            assertEquals(expectedX, bounds.x, "Window is not horizontally centered.");
            assertEquals(expectedY, bounds.y, "Window is not vertically centered.");
        });
    }
}
