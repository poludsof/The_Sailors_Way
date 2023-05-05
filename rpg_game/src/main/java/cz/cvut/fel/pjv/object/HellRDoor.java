package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HellRDoor extends Objects {
    public HellRDoor() {
        name_object = "Door";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/x___xhell_closed_right_door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
