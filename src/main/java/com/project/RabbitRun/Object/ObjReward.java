package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjReward extends SuperObject{

    public ObjReward() {
        name = "Clover";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Reward/clover.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
