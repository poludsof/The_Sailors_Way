package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Objects {
    public BufferedImage image;
    public String name_object;
    public boolean collision_obj = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 80, 80);

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // where obj on the screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void pickUpObj(int idx, GamePanel gp, Player player) {
        if (idx != -1) {
            if (gp.obj_arr[idx].name_object.equals("Door")) {
                if (player.key_count >= 1) {
                    gp.obj_arr[idx] = null;
                    if (idx == 2) {
                        gp.obj_arr[idx + 1] = null;
                        player.level++;
                        gp.playMusic(5);
                        gp.playMusic(1); }
                    if (idx == 3) {
                        gp.obj_arr[idx - 1] = null;
                        player.level++;
                        gp.playMusic(5);
                        gp.playMusic(1); }

                    if (idx == 4) {
                        gp.obj_arr[idx + 1] = null;
                        gp.playMusic(1); }
                    if (idx == 5) {
                        gp.obj_arr[idx - 1] = null;
                        gp.playMusic(1); }

                    if (idx == 7) { gp.obj_arr[idx + 1] = null; gp.playMusic(0); }
                    if (idx == 8) { gp.obj_arr[idx - 1] = null; gp.playMusic(0); }
                    --player.key_count;
                }
            }
            else if (gp.obj_arr[idx].name_object.equals("Key")) {
                if (idx == 0) { player.level++; gp.playMusic(5); }
                gp.obj_arr[idx] = null;
                ++player.key_count;
                gp.playMusic(2);
            }

            else if (gp.obj_arr[idx].name_object.equals("BushLine")) {
                gp.obj_arr[idx] = null;
                ++player.level;
                gp.playMusic(5);
                if (idx == 10) { gp.obj_arr[idx + 1] = null; }
                if (idx == 11) { gp.obj_arr[idx - 1] = null; }
            }

            else if (gp.obj_arr[idx].name_object.equals("Heart")) {
                if (player.heart_count < 6) {
                    gp.obj_arr[idx] = null;
                    ++player.heart_count;
                    gp.playMusic(4);
                }
            }
        }
    }
}