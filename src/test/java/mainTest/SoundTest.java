package mainTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.project.RabbitRun.main.Sound;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundTest {
    private Sound sound;

    @BeforeEach
    public void setUp() {
        sound = new Sound();
    }

    @Test
    public void testInitialization() {
        assertNotNull(sound.soundURL, "Sound URLs should be initialized");
        for (int i = 0; i < sound.soundURL.length; i++) {
            if (sound.soundURL[i] != null) {
                assertTrue(sound.soundURL[i].toString().endsWith(".wav"),
                        "Sound URL at index " + i + " should point to a .wav file");
            }
        }
    }

    @Test
    public void testSetFileValidIndex() {
        sound.setFile(0); // Example: testing the first sound file
        assertNotNull(sound.clip, "Clip should be initialized");
        assertTrue(sound.clip.isOpen(), "Clip should be open after setting a valid file");
    }

    @Test
    public void testPlay() {
        sound.setFile(1); // Load a valid sound file
        sound.play();
        assertNotNull(sound.clip, "Clip should be initialized");
        assertTrue(sound.clip.isOpen(), "Clip should be open after setFile() is called");
    }

    @Test
    public void testLoop() {
        sound.setFile(1); // Load a valid sound file
        assertDoesNotThrow(() -> sound.loop(),
                "loop should not throw an exception after setting a valid file");
    }

    @Test
    public void testMultipleSetFileCalls() {
        sound.setFile(0);
        sound.setFile(1); // Load another file
        assertNotNull(sound.clip, "Clip should be re-initialized when setFile is called again");
    }

}
