package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    private int x;
    private int y;
    private BufferedImage rabbit;

    public Player(int x, int y, Game game) {
        this.x = x;
        this.y = y;


        rabbit = game.getrabbitcharacter();

    }

    public void tick()
    {
        //edges collision detection
        if(x<0)
        {
            x = 0;
        }
        if(x >= 1500-75)
        {
            x = 1500-75;
        }
        if(y < 0)
        {
            y = 0;
        }
        if(y >= 1000 - 100)
        {
            y = 1000 - 100;
        }

        //block collision detection
        if(x == 50 && y == 50)
        {
            x = 0;
            y = 0;
        }



    }

    public void render (Graphics g)
    {
        g.drawImage(rabbit, x, y, 50, 50,  null);
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
