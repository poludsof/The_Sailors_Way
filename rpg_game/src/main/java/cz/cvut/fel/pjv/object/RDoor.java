package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a RDoor object in the game.
 */
public class RDoor extends GameObjects {
    public RDoor() {
        name_object = "RDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_right.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
