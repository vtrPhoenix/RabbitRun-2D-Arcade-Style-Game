package EntityTest;

import com.project.RabbitRun.Entity.Entity;
import com.project.RabbitRun.Entity.Player;
import com.project.RabbitRun.main.CollisionChecker;
import com.project.RabbitRun.main.GamePanel;
import com.project.RabbitRun.Entity.Enemy;
import com.project.RabbitRun.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private Enemy enemy;
    private Player player;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
        keyHandler = new KeyHandler(gamePanel);

        // Initialize enemy and player at fixed points
        enemy = new Enemy(gamePanel, 100, 150);
        player = new Player(gamePanel, keyHandler);

        // Reset to default
        enemy.setDirectionCooldown(0);
        enemy.setStuckCounter(0);
        enemy.setCollisionOn(false);
        enemy.setDefaultValues();
    }

    @Test
    public void testEnemyInitialization() {
        assertNotNull(enemy, "Enemy should be initialized");
        assertEquals(100, enemy.getWorldX(), "Enemy's initial X position should be correct");
        assertEquals(150, enemy.getWorldY(), "Enemy's initial Y position should be correct");
        assertEquals(2, enemy.getSpeed(), "Enemy's initial speed should be 2");
        assertEquals("left", enemy.getDirection(), "Enemy's initial direction should be 'left'");
    }

    @Test
    public void testEnemyRestart() {
        enemy.setWorldX(200);
        enemy.setWorldY(300);
        enemy.setDirection("up");

        enemy.restart();

        assertEquals(100, enemy.getWorldX(), "Enemy's X position should reset to initial X position");
        assertEquals(150, enemy.getWorldY(), "Enemy's Y position should reset to initial Y position");
        assertEquals("left", enemy.getDirection(), "Enemy's direction should reset to default 'left'");
    }

    @Test
    public void testLoadImage() {
        assertDoesNotThrow(() -> enemy.getEnemyImage(), "Loading enemy images should not throw an exception");
    }

    @Test
    public void testCollisionWithEnemy() {
        // Create a second enemy at the same position
        Enemy anotherEnemy = new Enemy(gamePanel, 100, 150);
        gamePanel.enemies.add(anotherEnemy);

        assertTrue(enemy.collisionWithEnemy(), "Enemy should detect a collision with another enemy at the same position");

        // Move the second enemy away
        anotherEnemy.setWorldX(300);
        anotherEnemy.setWorldY(300);

        assertFalse(enemy.collisionWithEnemy(), "Enemy should not detect a collision when no other enemy is at the same position");
    }

    @Test
    public void testCurrentDirection() {
        enemy.setDirection("up");
        int initialY = enemy.getWorldY();
        enemy.currentDirection();
        assertEquals(initialY - enemy.getSpeed(), enemy.getWorldY(), "Enemy should move up correctly");

        enemy.setDirection("down");
        initialY = enemy.getWorldY();
        enemy.currentDirection();
        assertEquals(initialY + enemy.getSpeed(), enemy.getWorldY(), "Enemy should move down correctly");

        enemy.setDirection("left");
        int initialX = enemy.getWorldX();
        enemy.currentDirection();
        assertEquals(initialX - enemy.getSpeed(), enemy.getWorldX(), "Enemy should move left correctly");

        enemy.setDirection("right");
        initialX = enemy.getWorldX();
        enemy.currentDirection();
        assertEquals(initialX + enemy.getSpeed(), enemy.getWorldX(), "Enemy should move right correctly");
    }

    @Test
    public void testAlternateDirection() {
        enemy.setDirection("left");
        enemy.setCollisionOn(false);
        enemy.setStuckCounter(0);
        enemy.setDirectionCooldown(0);

        gamePanel.enemies.clear();
        gamePanel.collisionChecker = new CollisionChecker(gamePanel) {
            @Override
            public void checkTile(Entity entity) {
            }
        };

        int initialDeltaX = 50;
        int negativeDeltaY = -30;
        enemy.alternateDirection(initialDeltaX, negativeDeltaY);
        assertEquals("up", enemy.getDirection(), "Enemy should change direction to 'up' when going left and deltaY is negative");

        enemy.setDirection("left");
        int positiveDeltaY = 30;
        enemy.alternateDirection(initialDeltaX, positiveDeltaY);
        assertEquals("down", enemy.getDirection(), "Enemy should change direction to 'down' when going left and deltaY is positive");

        enemy.setDirection("up");
        enemy.alternateDirection(20, 30);
        assertEquals("right", enemy.getDirection(), "Enemy should change direction to 'right' when going up and deltaX is positive");

        enemy.setDirection("down");
        enemy.alternateDirection(-20, 30);
        assertEquals("left", enemy.getDirection(), "Enemy should change direction to 'left' when going down and deltaX is negative");
    }

    @Test
    public void testChangeDirection() {
        enemy.setDirection("up");
        enemy.changeDirection();
        assertEquals("down", enemy.getDirection(), "Direction should change from 'up' to 'down'");

        enemy.setDirection("down");
        enemy.changeDirection();
        assertEquals("up", enemy.getDirection(), "Direction should change from 'down' to 'up'");

        enemy.setDirection("left");
        enemy.changeDirection();
        assertEquals("right", enemy.getDirection(), "Direction should change from 'left' to 'right'");

        enemy.setDirection("right");
        enemy.changeDirection();
        assertEquals("left", enemy.getDirection(), "Direction should change from 'right' to 'left'");
    }

    @Test
    public void testDetermineDirectionRight() {
        player.setWorldX(200);
        player.setWorldY(150);
        enemy.determineDirection(player);

        assertEquals("right", enemy.getDirection(), "Enemy should set direction to 'right' towards the player.");
    }

    @Test
    public void testDetermineDirectionDown() {
        player.setWorldX(100);
        player.setWorldY(250);
        enemy.determineDirection(player);

        assertEquals("down", enemy.getDirection(), "Enemy should set direction to 'down' towards the player.");
    }

    @Test
    public void testDetermineDirectionLeft() {
        player.setWorldX(50);
        player.setWorldY(150);
        enemy.determineDirection(player);

        assertEquals("left", enemy.getDirection(), "Enemy should set direction to 'left' towards the player.");
    }

    @Test
    public void testDetermineDirectionUp() {
        player.setWorldX(100);
        player.setWorldY(50);
        enemy.determineDirection(player);

        assertEquals("up", enemy.getDirection(), "Enemy should set direction to 'up' towards the player.");
    }

    @Test
    public void testUpdateEnemy() {
        // Set initial direction and no cooldown
        enemy.setDirection("left");
        enemy.setDirectionCooldown(0);

        // Place player to the right of the enemy
        player.setWorldX(200);
        player.setWorldY(150);

        // updateEnemy without any collision
        enemy.updateEnemy(player);

        // Verify that direction is set towards the player initially
        if (!enemy.isCollisionOn()) {
            assertEquals("right", enemy.getDirection(), "Enemy should set direction towards the player when updated");
        } else {
            // If collision happens, ensure the cooldown is set
            assertEquals(60, enemy.getDirectionCooldown(), "Cooldown should be set to 60 after collision");
            assertNotEquals("right", enemy.getDirection(), "Direction should change due to collision");
        }

        // Trigger a collision and verify the logic
        enemy.setCollisionOn(true);
        enemy.updateEnemy(player);

        // Verify the cooldown after collision
        assertEquals(60, enemy.getDirectionCooldown(), "Cooldown should be set to 60 after collision");
        assertNotEquals("right", enemy.getDirection(), "Direction should change due to collision");
    }
}
