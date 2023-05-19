package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a Sword object in the game.
 */
public class Sword extends GameObjects {
    public Sword() {
        name_object = "Sword";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(Sword.class.getClassLoader().getResourceAsStream("objects/sword.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
