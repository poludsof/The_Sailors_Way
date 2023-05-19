package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents Pirate in the game.
 */
public class Pirate extends Entity {
    GamePanel gp;
    int time = 0;

    /**
     Constructs a new instance of the Pirate class.
     @param gp the GamePanel instance
     */
    public Pirate(GamePanel gp) {
        super(gp);  // Call the constructor of the superclass (Entity).
        this.gp = gp;
        speed = 1;
        entityType = Type.PIRATE;

        // Set the pirate's solid area
        solidArea = new Rectangle(20, 24, 56, 40);
        direction = "down";

        getPirateImage();
    }

    /**
     Loads the pirate's image files.
     */
    public void getPirateImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pirate/pirate_down2.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Performs a random action for the pirate.
     The pirate changes its direction randomly every 30 frames (0.48 second).
     */
    public void randomAction() {
        if (time == 30) {
            time = 0;
            Random num = new Random();
            int i = num.nextInt(4);
            if (i == 0) direction = "up";
            if (i == 1) direction = "down";
            if (i == 2) direction = "left";
            if (i == 3) direction = "right";
        }
        ++time;
    }


    /**
     The method is an attack of the pirate by the player.
     @param idx the index of the pirate enemy in the pirate array
     */
    public void fightPirate(int idx) {
        if (idx >= 0) {

            // Check if the pirate is not currently being damaged and the player is attacking.
            if (!gp.pirates[idx].timeToDamage && gp.player.attacking) {
                if (gp.pirates[idx].heart_count > 0) {
                    --gp.pirates[idx].heart_count;
                    LOGGER.info("You attack the pirate. Pirate loses 1 health.");

                    gp.pirates[idx].showHealth = true;
                    gp.sound.setMusic(6);
                } else {

                    // Remove the boss instance from the game
                    gp.pirates[idx] = null;
                    LOGGER.info("You the the pirate.");

                    ++gp.player.dead_pirate_count;
                }

                // Set the flag indicating that the boss is being damaged.
                this.timeToDamage = true;
            }

            // If the player kills two pirates at the beginning of the game, a key appears that opens the bunker door.
            if (gp.player.dead_pirate_count == 2 && gp.player.level == 1) {
                gp.objPlacer.PlaceFirstKey();
            }
        }
    }
}
