package cz.cvut.fel.pjv.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

public class ImageManager {
    public static BufferedImage hat_sailor, hat, back_level, background, open_map, sailor, frame, arrow,
                                full_heart, half_heart, empty_heart,  // hearts
                                key, map, rum, sword,  // objects
                                image, image_end, enter_arrow, image_shadow, // ship
                                pause_button, chest_button, play_button, key_button, exit_button, space_button;
    static {
        try {
            open_map = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/open_map.jpg")));
            hat = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("level/hat_sailor.png")));
            back_level = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("level/back_level.png")));
            background = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("level/background.png")));
            empty_heart = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("heart/empty_heart.png")));
            half_heart = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("heart/half_heart.png")));
            full_heart = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("heart/full_heart.png")));
            key = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/key.png")));
            map = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/map.png")));
            rum = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/rum.png")));
            sword = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/sword.png")));
            image_shadow = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/starship_with_shadow.png")));
            image_end = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/starship_with_fire.png")));
            enter_arrow = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/arrow_boat.png")));
            hat_sailor = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("objects/hat.png")));
            sailor = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("player/sailor_down2.png")));
            frame = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/under.png")));
            pause_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/pause_button.png")));
            chest_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/chest.png")));
            play_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/play_button.png")));
            arrow = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/arrow.png")));
            key_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/wasd.png")));
            exit_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/exit_button.png")));
            space_button = ImageIO.read(Objects.requireNonNull(ImageManager.class.getClassLoader().getResourceAsStream("buttons/space.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
