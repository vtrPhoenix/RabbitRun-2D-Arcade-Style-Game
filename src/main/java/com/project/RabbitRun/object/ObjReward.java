package com.project.RabbitRun.object;

import com.project.RabbitRun.exceptions.ImageLoadingException;

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
        path = "/Reward/clover.png";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }  catch (NullPointerException e) {
            throw new ImageLoadingException("Image not found: " + path, e, path);
        } catch (IOException e) {
            throw new ImageLoadingException("Failed to load image: " + path, e, path);
        }
    }
}
