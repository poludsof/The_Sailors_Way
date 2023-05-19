package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a RotatedLDoor object in the game.
 */
public class RotatedLDoor extends GameObjects {
    public RotatedLDoor() {
        name_object = "RotatedLDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(RotatedLDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_lef_rotated.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
