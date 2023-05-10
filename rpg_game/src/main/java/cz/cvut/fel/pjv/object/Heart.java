package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Heart extends GameObjects {
    public Heart() {
        name_object = "Heart";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("heart/full_heart.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
