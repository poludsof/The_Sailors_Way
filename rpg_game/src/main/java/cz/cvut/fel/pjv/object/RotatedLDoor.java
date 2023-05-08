package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class RotatedLDoor extends Objects {
    public RotatedLDoor() {
        name_object = "Door";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_lef_rotated.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}