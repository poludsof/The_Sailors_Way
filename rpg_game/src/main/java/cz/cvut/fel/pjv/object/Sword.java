package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Sword extends GameObjects {
    public Sword() {
        name_object = "Sword";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/sword.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
