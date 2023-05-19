package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.main.GamePanel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents Boss in the game.
 */
public class Boss extends Entity {
    public int frames = 1;

    /**
     Constructs a new instance of the Boss class.
     @param gp the GamePanel instance
     */
    public Boss(GamePanel gp) {
        super(gp);  // Call the constructor of the superclass (Entity).
        entityType = Type.BOSS;

        // Set the boss's solid area
        solidArea = new Rectangle(60, 48, 150, 110);

        getBossImage();
    }

    /**
     Sets the default values for the boss based on the provided JSON file.
     @param filename the name of the JSON file containing the boss's default values
     */
    public void setDefaultValues(String filename) {
        if (gp.boss != null) {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(filename)) {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
                worldX = (int) ((long) jsonObject.get("worldX")) * gp.tileSize; // 87
                worldY = (int) ((long) jsonObject.get("worldY")) * gp.tileSize; // 40
                speed = (int) ((long) jsonObject.get("speed"));
                heart_count = (int) ((long) jsonObject.get("heart_count"));
            } catch (ParseException | IOException e) {
                LOGGER.error("Error: {}", e.getMessage());
                e.printStackTrace();
            }
            direction = "down";
            showHealth = false;
            timeToDamage = false;
        }
    }

    /**
     Loads the boss's image files.
     */
    private void getBossImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boss/boss_down2.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Performs a random action for the boss.
     The boss changes its direction randomly every 80 frames (1.28 second).
     */
    public void randomBossAction() {
        if (frames == 80) {
            frames = 0;
            Random num = new Random();
            int i = num.nextInt(4);
            if (i == 0) direction = "up";
            if (i == 1) direction = "down";
            if (i == 2) direction = "left";
            if (i == 3) direction = "right";
        }
        ++frames;
    }

    /**
     The method is an attack of the boss by the player.
     */
    public void fightBoss() {
        int tmp_x = gp.boss.worldX / gp.tileSize;
        int tmp_y = gp.boss.worldY / gp.tileSize;

        // Check if the boss is not currently being damaged and the player is attacking.
        if (!gp.boss.timeToDamage && gp.player.attacking) {
            if (gp.boss.heart_count > 0) {
                --gp.boss.heart_count;
                LOGGER.info("You attack the boss. Boss loses 1 health.");
                gp.boss.showHealth = true;
                gp.sound.setMusic(6);
            } else {
                LOGGER.info("You won the boss. The boss is dead...");

                // The key appears at the place where the boss died.
                gp.objPlacer.PlaceGoldKey(tmp_x, tmp_y);

                // Remove the boss instance from the game
                gp.boss = null;
            }

            // Set the flag indicating that the boss is being damaged.
            this.timeToDamage = true;
        }
    }

}
