package cz.cvut.fel.pjv.monster;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.object.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Boss extends Entity {
    int time = 1;

    public Boss(GamePanel gp) {
        super(gp);
        speed = 1;
        entityType = Type.BOSS;


        // Set the boss's solid area
        solidArea = new Rectangle();
        solidArea.x = 60; // todo
        solidArea.y = 48;
        solidArea.height = 150; // todo
        solidArea.width = 110;

        getBossImage();
        setDefaultValues();
    }

    public void setDefaultValues() {
        heart_count = 6;
        worldX = 87 * gp.tileSize;
        worldY = 40 * gp.tileSize;
        direction = "down";
        showHealth = false;
        timeToDamage = false;
    }

    public void getBossImage() {
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
                gp.boss.showHealth = true;
                gp.playMusic(6);
            } else {
                System.out.println(tmp_y);
                gp.ASetter.PlaceGoldKey(tmp_x, tmp_y);
                gp.boss = null;
            }
            this.timeToDamage = true;
        }
    }

}
