package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
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

public class Boss extends Entity {
    int time = 1;

    public Boss(GamePanel gp) {
        super(gp);
        entityType = Type.BOSS;

        // Set the boss's solid area
        solidArea = new Rectangle();
        solidArea.x = 60; // todo
        solidArea.y = 48;
        solidArea.height = 150; // todo
        solidArea.width = 110;

        getBossImage();
    }

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

    public void randomBossAction() {
        if (time == 80) {
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

    public void fightBoss() {
        int tmp_x = gp.boss.worldX / gp.tileSize;
        int tmp_y = gp.boss.worldY / gp.tileSize;
        if (!gp.boss.timeToDamage && gp.player.attacking) {
            if (gp.boss.heart_count > 0) {
                --gp.boss.heart_count;
                LOGGER.info("You attack the boss. Boss loses 1 health.");
                gp.boss.showHealth = true;
                gp.sound.setMusic(6);
            } else {
                LOGGER.info("You won the boss. The boss is dead..");
                gp.objPlacer.PlaceGoldKey(tmp_x, tmp_y);
                gp.boss = null;
            }
            this.timeToDamage = true;
        }
    }

}
