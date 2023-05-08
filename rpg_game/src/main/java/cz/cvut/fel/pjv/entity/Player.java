package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.KeyHandler;
import cz.cvut.fel.pjv.monster.Pirate;
import cz.cvut.fel.pjv.object.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    Objects obj = new Objects();
    Pirate pirate = new Pirate(gp);
    public int key_count = 0;
    public int level = 1;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        heart_count = 5;

        screenX = gp.screen_width / 2 - (gp.tileSize / 2);
        screenY = gp.screen_height / 2 - (gp.tileSize / 2);

        // Set the player's solid area
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.height = 56;
        solidArea.width = 40;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets default values for the player's properties, such as coordinates and movement speed.
     */
    public void setDefaultValues() {
        // Coordinates of the initial position of the player on the map
        worldX = 10 * gp.tileSize; //10
        worldY = 93 * gp.tileSize; //93
        speed = 6;
        direction = "down";
    }

    /**
     * Loads the player's image from the resource folder.
     */
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_up1.png"));
            up2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_up2.png"));
            left1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_left1.png"));
            left2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_left2.png"));
            right1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_right1.png"));
            right2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_right2.png"));
            down1 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_down1.png"));
            down2 = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_down2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the player's movements and animations based on the user's input.
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed)
                direction = "right";

            collision = false;
            gp.checker.CheckCollisionTile(this);

            int idx_m = gp.checker.CheckCollisionEntity(this, gp.monsters);
            if (idx_m >= 0) {
                gp.monsters[idx_m].fightMonster(idx_m);
            }

            int idx_obj = gp.checker.CheckCollisionObj(this);
            obj.pickUpObj(idx_obj, gp, this);

            if (!collision) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }

        if (timeToDamage) {
            DamageCounter++;
            if (DamageCounter == 60) { //  каждую секунду получает удар
                timeToDamage = false;
                DamageCounter = 0;
            }
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1)
                    image = up1;
                if (spriteNum == 2)
                    image = up2;
            }
            case "down" -> {
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
            }
            case "left" -> {
                if (spriteNum == 1)
                    image = left1;
                if (spriteNum == 2)
                    image = left2;
            }
            case "right" -> {
                if (spriteNum == 1)
                    image = right1;
                if (spriteNum == 2)
                    image = right2;
            }
        }
        if (timeToDamage) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
