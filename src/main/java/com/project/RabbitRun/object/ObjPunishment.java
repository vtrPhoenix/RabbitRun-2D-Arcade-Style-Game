package com.project.RabbitRun.object;

import com.project.RabbitRun.exceptions.ImageLoadingException;

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
        path = "/Punishment/mushroom.png";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }  catch (NullPointerException e) {
            throw new ImageLoadingException("Image not found: " + path, e, path);
        } catch (IOException e) {
            throw new ImageLoadingException("Failed to load image: " + path, e, path);
        }
    }
}
