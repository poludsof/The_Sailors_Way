package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a HellRDoor object in the game.
 */
public class HellRDoor extends GameObjects {
    public HellRDoor() {
        name_object = "HellRDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(HellRDoor.class.getClassLoader().getResourceAsStream("objects/x___xhell_closed_right_door.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
