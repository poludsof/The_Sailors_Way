package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a Heart object in the game.
 */
public class Heart extends GameObjects {
    public Heart() {
        name_object = "Heart";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(Heart.class.getClassLoader().getResourceAsStream("heart/full_heart.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
