package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a LDoor object in the game.
 */
public class LDoor extends GameObjects {
    public LDoor() {
        name_object = "LDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(LDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_left.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
