package cz.cvut.fel.pjv.monster;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.object.Key;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

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
                worldX = Integer.parseInt((String) jsonObject.get("worldX")) * gp.tileSize; // 87
                worldY = Integer.parseInt((String) jsonObject.get("worldY")) * gp.tileSize; // 40
                speed = Integer.parseInt((String) jsonObject.get("speed"));
                heart_count = Integer.parseInt((String) jsonObject.get("heart_count"));
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
            direction = "down";
            showHealth = false;
            timeToDamage = false;
        }
    }

    private void getBossImage() {
        try {
            up1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_up1.png"));
            up2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_up2.png"));
            left1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_left1.png"));
            left2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_left2.png"));
            right1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_right1.png"));
            right2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_right2.png"));
            down1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_down1.png"));
            down2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("boss/boss_down2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void randomBossAction() { // todo test
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

    public void fightBoss() { // todo test
        int tmp_x = gp.boss.worldX / gp.tileSize;
        int tmp_y = gp.boss.worldY / gp.tileSize;
        if (!gp.boss.timeToDamage && gp.player.attacking) {
            if (gp.boss.heart_count > 0) {
                --gp.boss.heart_count;
                gp.boss.showHealth = true;
                gp.playMusic(6);
            } else {
                System.out.println("hi");
                gp.ASetter.PlaceGoldKey(tmp_x, tmp_y);
                gp.boss = null;
            }
            this.timeToDamage = true;
        }
    }

}
