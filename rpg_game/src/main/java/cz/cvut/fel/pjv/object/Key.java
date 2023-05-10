package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends GameObjects {
    public Key() {
        name_object = "Key";
        try {
            image = ImageIO.read(Key.class.getClassLoader().getResourceAsStream("objects/key.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
