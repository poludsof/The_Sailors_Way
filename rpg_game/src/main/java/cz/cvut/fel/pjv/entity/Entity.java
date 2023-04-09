package cz.cvut.fel.pjv.entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public String direction;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
