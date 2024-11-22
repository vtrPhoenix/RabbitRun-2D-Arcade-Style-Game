package mainTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.project.RabbitRun.main.CollisionCheckerObject;
import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.entity.Entity;
import com.project.RabbitRun.object.SuperObject;

import java.awt.*;

public class CollisionCheckerObjectTest {

    private GamePanel gamePanel;
    private CollisionCheckerObject collisionChecker;
    private Entity testEntity;

    @BeforeEach
    public void setUp() {
        // Initialize GamePanel and CollisionCheckerObject
        gamePanel = new GamePanel();
        collisionChecker = new CollisionCheckerObject(gamePanel);

        // Create a test entity
        testEntity = new Entity();
        testEntity.solidArea = new Rectangle(0, 0, 32, 32);
        testEntity.setWorldX(100);
        testEntity.setWorldY(100);
        testEntity.setSpeed(5);

        // Create a test object
        SuperObject testObject = new SuperObject();
        testObject.setSolidArea(new Rectangle(0, 0, 32, 32));
        testObject.setWorldX(120);
        testObject.setWorldY(100);
        testObject.setCollision(true);

        // Add the object to the GamePanel
        gamePanel.object[0] = testObject;
    }

    @Test
    public void testNoCollision() {
        // Place the entity away from the object
        testEntity.setWorldX(200);
        testEntity.setWorldY(200);
        testEntity.setDirection("up");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(999, result, "Expected no collision to be detected");
        assertFalse(testEntity.isCollisionOn(), "Collision flag should remain false");
    }

    @Test
    public void testCollisionFromRight() {
        // Move entity to collide from the right
        testEntity.setWorldX(85); // Overlaps with the left edge of the object's solid area
        testEntity.setWorldY(100);
        testEntity.setDirection("right");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(0, result, "Expected collision with object index 0");
        assertTrue(testEntity.isCollisionOn(), "Collision flag should be set to true");
    }

    @Test
    public void testCollisionFromLeft() {
        // Move entity to collide from the left
        testEntity.setWorldX(150); // Overlaps with the right edge of the object's solid area
        testEntity.setWorldY(100);
        testEntity.setDirection("left");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(0, result, "Expected collision with object index 0");
        assertTrue(testEntity.isCollisionOn(), "Collision flag should be set to true");
    }

    @Test
    public void testCollisionFromAbove() {
        // Move entity to collide from above
        testEntity.setWorldX(120);
        testEntity.setWorldY(130); // Overlaps with the bottom edge of the object's solid area
        testEntity.setDirection("up");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(0, result, "Expected collision with object index 0");
        assertTrue(testEntity.isCollisionOn(), "Collision flag should be set to true");
    }

    @Test
    public void testCollisionFromBelow() {
        // Move entity to collide from below
        testEntity.setWorldX(120);
        testEntity.setWorldY(70); // Overlaps with the top edge of the object's solid area
        testEntity.setDirection("down");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(0, result, "Expected collision with object index 0");
        assertTrue(testEntity.isCollisionOn(), "Collision flag should be set to true");
    }

    @Test
    public void testMultipleObjects() {
        // Add another object to the GamePanel
        SuperObject secondObject = new SuperObject();
        secondObject.setSolidArea(new Rectangle(0, 0, 32, 32));
        secondObject.setWorldX(200);
        secondObject.setWorldY(100);
        secondObject.setCollision(true);
        gamePanel.object[1] = secondObject;

        // Move entity to collide with the second object
        testEntity.setWorldX(200);
        testEntity.setWorldY(100);
        testEntity.setDirection("left");

        int result = collisionChecker.checkObject(testEntity, true);

        assertEquals(1, result, "Expected collision with object index 1");
        assertTrue(testEntity.isCollisionOn(), "Collision flag should be set to true");
    }
}
