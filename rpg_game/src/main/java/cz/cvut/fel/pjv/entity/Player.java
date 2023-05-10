package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.KeyHandler;
import cz.cvut.fel.pjv.object.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    Objects obj = new Objects();
    public int key_count = 0, dead_pirate_count = 0;
    public int level = 1;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        heart_count = 6;

        screenX = gp.screen_width / 2 - (gp.tileSize / 2);
        screenY = gp.screen_height / 2 - (gp.tileSize / 2);

        // Set the player's solid area
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.height = 56;
        solidArea.width = 40;

        attackArea.width = 40;
        attackArea.height = 40;

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

            attack_up = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/attack_up.png"));
            attack_down = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/attack_down.png"));
            attack_left = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/attack_left.png"));
            attack_right = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/attack_right.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the player's movements and animations based on the user's input.
     */
    public void update() {
        if (heart_count == 0) {
            gp.state = GamePanel.State.GAME_OVER;
        }

        if (timeToDamage) {
            damageCounter++;
            if (damageCounter == 60) { //  каждую секунду получает удар
                timeToDamage = false;
                damageCounter = 0;
            }
        }

        if (attacking) {
            int idx_m = attacking();
            if (idx_m >= 0) {
                gp.monsters[idx_m].fightMonster(idx_m);
            }
        }

        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed)
                direction = "right";

            if (keyH.spacePressed)
                attacking = true;

            collision = false;
            gp.checker.CheckCollisionTile(this);

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
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (timeToDamage) {
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2.setComposite(alphaComposite);
            g2.setColor(Color.RED);
            g2.fillRect(screenX + 10, screenY, gp.tileSize - 20, gp.tileSize);
        }

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if (attacking) image = attack_up;
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if (attacking) image = attack_down;
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                if (attacking) image = attack_left;
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
                if (attacking) image = attack_right;
            }
        }

        if (image == attack_up) g2.drawImage(attack_up, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_down) g2.drawImage(attack_down, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_left) g2.drawImage(attack_left, screenX - gp.tileSize, screenY, gp.tileSize * 2, gp.tileSize, null);
        else if (image == attack_right) g2.drawImage(attack_right, screenX, screenY, gp.tileSize * 2, gp.tileSize, null);
        else g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private int attacking(){
        int idx_pirate = -1;
        spriteCounter++;
        if (spriteCounter <= 25) {
            int currWorldX = worldX;
            int currWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

//            System.out.println("before " + worldX);
            switch (direction) {
                case "up" -> { worldY -= attackArea.height; }
                case "down" -> { worldY += attackArea.height; }
                case "left" -> { worldX -= attackArea.width; }
                case "right" -> { worldX += attackArea.width; }
            }
//            System.out.println("after " + worldX);

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            idx_pirate = gp.checker.CheckCollisionEntity(this, gp.monsters);
//            fightMonster(idx_pirate);
            worldX = currWorldX;
            worldY = currWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25){
            gp.playMusic(7);
            spriteCounter = 0;
            attacking = false;
        }
        return idx_pirate;
    }

}
