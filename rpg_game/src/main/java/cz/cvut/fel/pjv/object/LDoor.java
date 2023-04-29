package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class LDoor extends Objects{
    public LDoor() {
        name_object = "Door";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/x___door_closed_left.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
