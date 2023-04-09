package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

//import java.io.IOException;
//import java.io.InputStream;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
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
                y -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
