package com.project.RabbitRun.Entity;

import com.project.RabbitRun.main.CollisionChecker;
import com.project.RabbitRun.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Enemy extends Entity {

    GamePanel gamePanel;
    private final int initialX, initialY;
    private int pathUpdateCounter = 0;
    private int spriteCounter = 0;
    private int spriteNumber = 1;
    private List<Point> currentPath = new ArrayList<>();
    private int targetWorldX;
    private int targetWorldY;

    public Enemy(GamePanel gamePanel, int startX, int startY) {
        this.gamePanel = gamePanel;
        this.initialX = startX;
        this.initialY = startY;
        this.worldX = startX;
        this.worldY = startY;
        this.targetWorldX = startX;
        this.targetWorldY = startY;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 25;

        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "left";
    }

    public static List<Enemy> initializeEnemies(GamePanel gamePanel) {
        int[][] enemyPositions = {
                {gamePanel.tileSize * 38, gamePanel.tileSize * 8},
                {gamePanel.tileSize * 29, gamePanel.tileSize * 32},
                {gamePanel.tileSize * 11, gamePanel.tileSize * 33}
        };

        List<Enemy> enemies = new ArrayList<>();
        for (int[] pos : enemyPositions) {
            enemies.add(new Enemy(gamePanel, pos[0], pos[1]));
        }
        return enemies;
    }

    public void restart() {
        this.worldX = initialX;
        this.worldY = initialY;
        setDefaultValues();
    }

    public void getEnemyImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemy/Down2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEnemy(Player player) {
        // Update path less frequently for performance
        pathUpdateCounter++;
        if (pathUpdateCounter > 20) {
            pathUpdateCounter = 0;

            int targetX = player.worldX / gamePanel.tileSize;
            int targetY = player.worldY / gamePanel.tileSize;
            int currentX = this.worldX / gamePanel.tileSize;
            int currentY = this.worldY / gamePanel.tileSize;

            currentPath = findPath(currentX, currentY, targetX, targetY);
            if (!currentPath.isEmpty()) {
                Point nextStep = currentPath.get(0);
                targetWorldX = nextStep.x * gamePanel.tileSize;
                targetWorldY = nextStep.y * gamePanel.tileSize;
            }
        }

        if (worldX != targetWorldX || worldY != targetWorldY) {
            updateDirection();
            moveTowardsTarget();
        }

        spriteCounter++;
        if (spriteCounter > 13) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

        if (collisionWithPlayer(player)) {
            gamePanel.gameState = gamePanel.youLostState;
        }
    }

    private void updateDirection() {
        int deltaX = targetWorldX - worldX;
        int deltaY = targetWorldY - worldY;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                direction = "right";
            } else if (deltaX < 0) {
                direction = "left";
            }
        } else {
            if (deltaY > 0) {
                direction = "down";
            } else if (deltaY < 0) {
                direction = "up";
            }
        }
    }

    private void moveTowardsTarget() {
        // cap speed to prevent enemy pouncing on player
        int deltaX = targetWorldX - worldX;
        int deltaY = targetWorldY - worldY;

        if (Math.abs(deltaX) > 0) {
            int stepX = (int) Math.signum(deltaX) * Math.min(speed, Math.abs(deltaX));
            worldX += stepX;
        }

        if (Math.abs(deltaY) > 0) {
            int stepY = (int) Math.signum(deltaY) * Math.min(speed, Math.abs(deltaY));
            worldY += stepY;
        }
    }

    public boolean collisionWithPlayer(Player player) {
        Rectangle enemyBounds = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle playerBounds = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

        return enemyBounds.intersects(playerBounds);
    }

    private List<Point> findPath(int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost));
        Map<Point, Point> cameFrom = new HashMap<>();
        Map<Point, Integer> gCostMap = new HashMap<>();
        Point start = new Point(startX, startY);
        Point goal = new Point(goalX, goalY);

        openSet.add(new Node(start, 0, heuristic(start, goal)));
        gCostMap.put(start, 0);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Point current = currentNode.position;

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            for (Point neighbor : getNeighbors(current)) {
                if (!isWalkable(neighbor)) continue;

                int tentativeGCost = gCostMap.getOrDefault(current, Integer.MAX_VALUE) + 1;

                if (tentativeGCost < gCostMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gCostMap.put(neighbor, tentativeGCost);
                    int fCost = tentativeGCost + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, tentativeGCost, fCost));
                }
            }
        }
        return new ArrayList<>(); // Return an empty path if no path is found
    }

    private List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        List<Point> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }

    private List<Point> getNeighbors(Point p) {
        return List.of(
                new Point(p.x + 1, p.y),
                new Point(p.x - 1, p.y),
                new Point(p.x, p.y + 1),
                new Point(p.x, p.y - 1)
        );
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Manhattan distance
    }

    private boolean isWalkable(Point p) {
        int tileX = p.x;
        int tileY = p.y;

        if (tileX < 0 || tileY < 0 || tileX >= gamePanel.maxWorldCol || tileY >= gamePanel.maxWorldRow) {
            return false;
        }

        int prevWorldX = this.worldX;
        int prevWorldY = this.worldY;

        this.worldX = tileX * gamePanel.tileSize;
        this.worldY = tileY * gamePanel.tileSize;
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        // reset to previous position to avoid interference
        this.worldX = prevWorldX;
        this.worldY = prevWorldY;

        return !collisionOn;
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        if ("up".equals(direction)) {
            if (spriteNumber == 1) {
                image = up1;
            } else {
                image = up2;
            }
        } else if ("down".equals(direction)) {
            if (spriteNumber == 1) {
                image = down1;
            } else {
                image = down2;
            }
        } else if ("left".equals(direction)) {
            if (spriteNumber == 1) {
                image = left1;
            } else {
                image = left2;
            }
        } else if ("right".equals(direction)) {
            if (spriteNumber == 1) {
                image = right1;
            } else {
                image = right2;
            }
        }

        // Calculate screen position based on the player position
        int screenX = this.worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = this.worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    private static class Node {
        Point position;
        int gCost;
        int fCost;

        Node(Point position, int gCost, int fCost) {
            this.position = position;
            this.gCost = gCost;
            this.fCost = fCost;
        }
    }
}
