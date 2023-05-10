package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public enum Type{
        PLAYER,
        PIRATE
    }

    public int worldX, worldY;

    public int speed;
    public String direction;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attack_up, attack_down, attack_left, attack_right;
    public GamePanel gp;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean timeToDamage = false;
    public int damageCounter = 0;

    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int heart_count;
    public boolean collision = true;
    public Type entityType;
    public boolean attacking = false;
    public boolean showHealth = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // where obj on the screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                case "down" -> {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                case "left" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                case "right" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }

            if (showHealth) {
                g2.setColor(Color.black);
                g2.fillRect(screenX, screenY - 20, gp.tileSize, 12);

                int fill_health = (gp.tileSize - 4) / 3;
                g2.setColor(Color.red);
                g2.fillRect(screenX + 2, screenY - 18, fill_health * heart_count + 10, 8);
            }

            if (timeToDamage) {
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(alphaComposite);
                g2.setColor(Color.RED);
                g2.fillRect(screenX + 10, screenY, gp.tileSize - 20, gp.tileSize);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }

    public void update() {
        randomAction();

        if (timeToDamage) {
            damageCounter++;
            if (damageCounter == 80) { //  каждую секунд 40 олучает удар
                timeToDamage = false;
                damageCounter = 0;
            }
        }

        collision = false;
        gp.checker.CheckCollisionTile(this);
        gp.checker.CheckCollisionObj(this);

        boolean fight = gp.checker.CheckCollisionPlayer(this);
        if (this.entityType == Type.PIRATE && fight) {
            if (!gp.player.timeToDamage) {
                if (gp.player.heart_count > 0) {
                    --gp.player.heart_count;
                    gp.playMusic(8);
                }
                gp.player.timeToDamage = true;
            }
        }

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

    public void randomAction() {}

    public void fightMonster(int idx) {}

}
