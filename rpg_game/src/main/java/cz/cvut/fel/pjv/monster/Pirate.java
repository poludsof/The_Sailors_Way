package cz.cvut.fel.pjv.monster;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.object.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Pirate extends Entity {
    GamePanel gp;
    Objects obj = new Objects();
    int time = 0;

    public Pirate(GamePanel gp) {
        super(gp);
        this.gp = gp;
        heart_count = 3;
        speed = 3;
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
            up1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_up1.png"));
            up2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_up2.png"));
            left1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_left1.png"));
            left2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_left2.png"));
            right1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_right1.png"));
            right2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_right2.png"));
            down1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_down1.png"));
            down2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("pirate/pirate_down2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void randomAction() {
        if (time == 20) {
            time = 0;
            Random num = new Random();
            int i = num.nextInt(4);
            if (i == 0)
                direction = "up";
            if (i == 1)
                direction = "down";
            if (i == 2)
                direction = "left";
            if (i == 3)
                direction = "right";
        }
        ++time;
    }

    public void fightMonster(int idx) {
        if (idx >= 0){
            if (gp.player.timeToDamage) {
                if (gp.player.heart_count > 0) {
                    System.out.println("minus one");
                    gp.player.heart_count--;
                }
                gp.player.timeToDamage = false;
            }
        }
    }
}
