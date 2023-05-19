package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a Key object in the game.
 */
public class Key extends GameObjects {
    public Key() {
        name_object = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(Key.class.getClassLoader().getResourceAsStream("objects/key.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
