package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HellLDoor extends GameObjects {
    public HellLDoor() {
        name_object = "HellLDoor";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/x___xhell_closed_left_door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
