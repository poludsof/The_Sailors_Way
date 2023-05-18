package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

public class Pirate extends Entity {
    GamePanel gp;
    int time = 0;

    public Pirate(GamePanel gp) {
        super(gp);
        this.gp = gp;
        speed = 1;
        entityType = Type.PIRATE;

        // Set the player's solid area
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.height = 56;
        solidArea.width = 40;
        direction = "down";

        getPirateImage();
    }

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

    public void fightPirate(int idx) {
        if (idx >= 0) {
            if (!gp.pirates[idx].timeToDamage && gp.player.attacking) {
                if (gp.pirates[idx].heart_count > 0) {
                    --gp.pirates[idx].heart_count;
                    gp.pirates[idx].showHealth = true;
                    gp.sound.setMusic(6);
                } else {
                    gp.pirates[idx] = null;
                    ++gp.player.dead_pirate_count;
                }
                this.timeToDamage = true;
            }
            if (gp.player.dead_pirate_count == 2 && gp.player.level == 1) {
                gp.objPlacer.PlaceFirstKey();
            }
        }
    }
}
