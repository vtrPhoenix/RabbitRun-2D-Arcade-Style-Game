package com.project.RabbitRun.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class menuMouseListener extends MouseAdapter {
    GamePanel gamePanel;

    private final Rectangle guideButtonBounds = new Rectangle(50, 254, 185, 35);
    private final Rectangle startButtonBounds = new Rectangle(300, 254, 185, 35);
    private final Rectangle quitButtonBounds = new Rectangle(550, 254, 185, 35);

    public menuMouseListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        Point clickPoint = e.getPoint();
        if(gamePanel.gameState == gamePanel.menuState) {
            if (guideButtonBounds.contains(clickPoint)) {
                //print the guide
                System.out.println("Guide clicked");
            } else if (startButtonBounds.contains(clickPoint)) {
                gamePanel.gameState = gamePanel.playState;
            } else if (quitButtonBounds.contains(clickPoint)) {
                System.exit(0);
            }
        }
    }


}
