package com.project.RabbitRun.object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the exit door object in the "Rabbit Run" game.
 * The door can either be open or closed, and its image changes accordingly.
 */
public class ObjExitDoor extends SuperObject{
    /** Indicates whether the exit door is open. */
    private boolean open;
    /** Image for the closed state of the door. */
    private BufferedImage closedImage;
    /** Image for the open state of the door. */
    private BufferedImage openImage;

    /**
     * Initializes the exit door with a specified state (open or closed).
     * Sets the appropriate image based on the door's state.
     *
     * @param open {@code true} if the door is open; {@code false} if closed
     */
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
