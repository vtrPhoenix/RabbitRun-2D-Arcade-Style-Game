package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ObjExitDoor extends SuperObject{
    public boolean open;
    private BufferedImage closedImage;
    private BufferedImage openImage;

    public ObjExitDoor(boolean open) {
        this.open = open;
        name = "ExitDoor";
        try {
            closedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Exit/doorclosed.png")));
            openImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Exit/dooropened.png")));
            if(open) {
                image = openImage;
            }else {
                image = closedImage;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
