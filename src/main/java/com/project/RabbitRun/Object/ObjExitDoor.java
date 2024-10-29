package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjExitDoor extends SuperObject{
    public ObjExitDoor() {
        name = "ExitDoor";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Exit/doorclosed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
