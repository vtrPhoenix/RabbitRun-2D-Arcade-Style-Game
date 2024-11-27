package com.project.RabbitRun.object;

import com.project.RabbitRun.exceptions.ImageLoadingException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a bonus reward object in the "Rabbit Run" game.
 * This object provides extra points to the player when collected.
 */
public class ObjBonusReward extends SuperObject{

    /**
     * Initializes a bonus reward object with a "Carrot" image.
     * This reward grants additional points to the player upon collection.
     */
    public ObjBonusReward() {
        name = "Carrot";
        path = "/Reward/carrot.png";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }  catch (NullPointerException e) {
            throw new ImageLoadingException("Image not found: " + path, e, path);
        } catch (IOException e) {
            throw new ImageLoadingException("Failed to load image: " + path, e, path);
        }
    }
}
