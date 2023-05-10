package cz.cvut.fel.pjv.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BlackKey extends GameObjects {
    public BlackKey() {
        name_object = "Key";
        try {
            image = ImageIO.read(Key.class.getClassLoader().getResourceAsStream("objects/h_key.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
