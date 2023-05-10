package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Rum extends GameObjects {
    public Rum() {
        name_object = "Rum";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/rum.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
