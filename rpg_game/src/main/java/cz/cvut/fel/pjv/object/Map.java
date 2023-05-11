package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Map extends GameObjects {
    public Map() {
        name_object = "Map";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/map.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
