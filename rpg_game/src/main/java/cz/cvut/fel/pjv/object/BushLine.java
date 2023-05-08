package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BushLine extends Objects {
    public BushLine() {
        name_object = "BushLine";
        collision_obj = true;
        try {
            image = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/bushline.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
