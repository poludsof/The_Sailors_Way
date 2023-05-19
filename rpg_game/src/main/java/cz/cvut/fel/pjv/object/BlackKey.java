package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a BlackKey object in the game.
 */
public class BlackKey extends GameObjects {
    public BlackKey() {
        name_object = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(BlackKey.class.getClassLoader().getResourceAsStream("objects/h_key.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
