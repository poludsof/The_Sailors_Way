package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a Rum object in the game.
 */
public class Rum extends GameObjects {
    public Rum() {
        name_object = "Rum";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(Rum.class.getClassLoader().getResourceAsStream("objects/rum.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
