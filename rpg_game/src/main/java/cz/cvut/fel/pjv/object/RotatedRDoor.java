package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a RotatedRDoor object in the game.
 */
public class RotatedRDoor extends GameObjects {
    public RotatedRDoor() {
        name_object = "RotatedRDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(RotatedRDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_righ_rotated.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
