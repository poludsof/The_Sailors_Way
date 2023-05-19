package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a HellLDoor object in the game.
 */
public class HellLDoor extends GameObjects {
    public HellLDoor() {
        name_object = "HellLDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(HellLDoor.class.getClassLoader().getResourceAsStream("objects/x___xhell_closed_left_door.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
