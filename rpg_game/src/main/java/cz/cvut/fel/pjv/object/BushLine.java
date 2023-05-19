package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a BushLine object in the game.
 */
public class BushLine extends GameObjects {
    public BushLine() {
        name_object = "BushLine";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(BushLine.class.getClassLoader().getResourceAsStream("objects/bushline.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
