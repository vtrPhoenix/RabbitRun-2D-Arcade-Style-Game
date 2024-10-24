package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 1500;
    public static int HEIGHT = 1000;

    boolean running = false;
    Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);


    public BufferedImage getrabbitcharacter()
    {
        return buffrabbit;
    }

    public BufferedImage getblockimage()
    {
        return buffblock;
    }

    public BufferedImage getleafimage()
    {
        return buffleaf;
    }



    //rabbitimage
    ImageIcon image2 = new ImageIcon("8bitrabbit.jpg");
    Image newimage = image2.getImage();
    Image moddedrabbitimage = newimage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    private BufferedImage buffrabbit = convertUsingConstructor(newimage);

    //block image
    ImageIcon blockimage = new ImageIcon("8bitblock.png");
    Image newblockimage = blockimage.getImage();
    private BufferedImage buffblock = convertUsingConstructor(newblockimage);

    //leaf image
    ImageIcon leafimage = new ImageIcon("8bitleaf.png");
    Image newleafimage = leafimage.getImage();
    private BufferedImage buffleaf = convertUsingConstructor(newleafimage);



    public BufferedImage convertUsingConstructor(Image image) throws IllegalArgumentException {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Image dimensions are invalid");
        }
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }



    public block block1;
    public block block2;
    public leaf leaf1;

    public Player p;

    public void init()
    {

        addKeyListener(new keyInput(this));
        p = new Player(0,0, this);
        block1 = new block(50,50,this);
        block2 = new block(50,50+50,this);
        leaf1 = new leaf(100,100,this);
    }




    synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    synchronized void stop() {
        if (!running)
            return;
        running = false;
        try{
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;


        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                delta--;
            }
            render();

        }
        stop();
    }


    private void tick() {
        p.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, 1500, 1000, this);

        block1.render(g);
        block2.render(g);
        leaf1.render(g);

        p.render(g);


        g.dispose();
        bs.show();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            p.setY(p.getY()-50);
        }
        else if (key == KeyEvent.VK_DOWN) {
            p.setY(p.getY()+50);
        }
        else if (key == KeyEvent.VK_LEFT) {
            p.setX(p.getX()-50);
        }
        else if (key == KeyEvent.VK_RIGHT) {
            p.setX(p.getX()+50);
        }
    }

    public void keyReleased(KeyEvent e){

    }


    public static void main(String[] args)
    {
        Game game = new Game();
        game.setSize(WIDTH, HEIGHT);


        JFrame frame = new JFrame("Rabbit Run");
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);



        game.start();

    }

}
