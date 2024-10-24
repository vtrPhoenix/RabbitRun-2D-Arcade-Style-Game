package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class leaf {

    private int x;
    private int y;
    private BufferedImage leafimage;

    public leaf(int x, int y, Game game) {
        this.x = x;
        this.y = y;

        leafimage = game.getleafimage();
    }

    public void render (Graphics g)
    {
        g.drawImage(leafimage, x, y, 50, 50,  null);
    }
}
