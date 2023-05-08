package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.object.Objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public enum Type{
        PLAYER,
        PIRATE
    }

    public int worldX, worldY;
    Objects obj = new Objects();

    public int speed;
    public String direction;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public GamePanel gp;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean timeToDamage = false;
    public int DamageCounter = 0;

    public Rectangle solidArea;
    public int heart_count;
    public boolean collision = false;
    public Type entityType;

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

    public void update() {
        randomAction();

        collision = false;
        gp.checker.CheckCollisionTile(this);
        gp.checker.CheckCollisionObj(this);

        boolean fight = gp.checker.CheckCollisionPlayer(this);
        if (this.entityType == Type.PIRATE && fight) {
            if (!gp.player.timeToDamage) {
                --gp.player.heart_count;
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

    public void randomAction() {
    }

    public void fightMonster(int idx) {

    }
}
