package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjPunishment extends SuperObject{

    public ObjPunishment() {
        name = "Mushroom";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Punishment/mushroom.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
