package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a Map object in the game.
 */
public class Map extends GameObjects {
    public Map() {
        name_object = "Map";
        collision_obj = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream("objects/map.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
