package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screen_width / 2 - (gp.tileSize / 2);
        screenY = gp.screen_height / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.height = 56;
        solidArea.width = 40;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = 14 * gp.tileSize;
        worldY = 24 * gp.tileSize;
        speed = 4;
        direction = "down";
    }
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

//            up1 = ImageIO.read(Player.class.getClassLoader().getResource("player/boy_down_1.png"));
//            up1 = ImageIO.read(getClass().getResourceAsStream("/resources2/boy_d_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
//                worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
//                worldY += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
//                worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
//                worldX += speed;
            }

            collision = false;
            gp.checker.CheckTile(this);
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

    }
    public void draw(Graphics2D g2) {

//        g2.setColor(Color.blue);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
