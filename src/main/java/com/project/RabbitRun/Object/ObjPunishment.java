package com.project.RabbitRun.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a punishment object in the "Rabbit Run" game.
 * This object, when encountered by the player, may reduce the player's score.
 */
public class ObjPunishment extends SuperObject{

    /**
     * Initializes a punishment object with a "Mushroom" image.
     * This object is associated with a penalty when the player interacts with it.
     */
    public ObjPunishment() {
        name = "Mushroom";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Punishment/mushroom.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
