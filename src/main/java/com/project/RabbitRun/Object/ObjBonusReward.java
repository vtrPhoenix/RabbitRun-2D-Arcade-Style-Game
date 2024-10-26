package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjBonusReward extends SuperObject{

    public ObjBonusReward() {
        name = "Carrot";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Reward/carrot.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
