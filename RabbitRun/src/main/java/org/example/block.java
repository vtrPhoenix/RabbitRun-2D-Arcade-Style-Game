package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class block {
    private int x;
    private int y;
    private BufferedImage block_image;

    public block(int x, int y, Game game) {
        this.x = x;
        this.y = y;

        block_image = game.getblockimage();
    }

    public void render (Graphics g)
    {
        g.drawImage(block_image, x, y, 50, 50,  null);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
