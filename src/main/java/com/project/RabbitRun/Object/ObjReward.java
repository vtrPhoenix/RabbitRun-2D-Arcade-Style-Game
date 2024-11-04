package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a regular reward object in the "Rabbit Run" game.
 * This object adds points to the player's score when collected.
 */
public class ObjReward extends SuperObject{

    /**
     * Initializes a reward object with a "Clover" image.
     * This reward contributes to the player's score upon collection.
     */
    public ObjReward() {
        name = "Clover";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Reward/clover.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
