package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObjects {
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
            gp.stopMusic();
             if (gp.obj_arr[idx].name_object.equals("Key")) {
                if (idx == 0) { player.level++; gp.playMusic(5); }
                gp.obj_arr[idx] = null;
                ++player.key_count;
                gp.player.inventory.put("Key", player.key_count);
                gp.playMusic(2);
            }

            else if (gp.obj_arr[idx].name_object.equals("BushLine")) {
                gp.obj_arr[idx] = null;
                ++player.level;
                gp.playMusic(5);
                if (idx == 6) { gp.obj_arr[idx + 1] = null; }
                if (idx == 7) { gp.obj_arr[idx - 1] = null; }
            }

            else if (gp.obj_arr[idx].name_object.equals("Heart")) {
                gp.obj_arr[idx] = null;
                if (player.heart_count < 6) {
                    ++player.heart_count;
                    gp.playMusic(4);
                }
            }

            else if (gp.obj_arr[idx].name_object.equals("Rum")) {
                gp.obj_arr[idx] = null;
                gp.player.rum_count += 1;
                gp.player.inventory.put("Rum", player.rum_count);
            }

            else if (gp.obj_arr[idx].name_object.equals("Map")) {
                gp.obj_arr[idx] = null;
                gp.player.map_count += 1;
                gp.player.inventory.put("Map", player.map_count);
            }

            else if (gp.obj_arr[idx].name_object.equals("Sword")) {
                gp.obj_arr[idx] = null;
                gp.player.sword_count += 1;
                gp.player.inventory.put("Sword", player.sword_count);
            }
            else if (player.key_count >= 1) {
                 if (idx == 0 && gp.obj_arr[idx].name_object.equals("RDoor")) {
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx + 1] = null;
                     player.level++;
                     gp.playMusic(5);
                     gp.playMusic(1);
                     --player.key_count;
                 }
                 if (idx == 1 && gp.obj_arr[idx].name_object.equals("LDoor")) {
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx - 1] = null;
                     player.level++;
                     gp.playMusic(5);
                     gp.playMusic(1);
                     --player.key_count;
                 }
                 if (idx == 2 && gp.obj_arr[idx].name_object.equals("RotatedLDoor")) {
                     player.level++;
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx + 1] = null;
                     gp.playMusic(1);
                     --player.key_count;
                 }
                 if (idx == 3 && gp.obj_arr[idx].name_object.equals("RotatedRDoor")) {
                     player.level++;
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx - 1] = null;
                     gp.playMusic(1);
                     --player.key_count;
                 }
                 if (idx == 4 && gp.obj_arr[idx].name_object.equals("HellRDoor")) {
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx + 1] = null;
                     gp.playMusic(0);
                     --player.key_count;
                 }
                 if (idx == 5 && gp.obj_arr[idx].name_object.equals("HellLDoor")) {
                     gp.obj_arr[idx] = null;
                     gp.obj_arr[idx - 1] = null;
                     gp.playMusic(0);
                     --player.key_count;
                 }
                 gp.player.inventory.put("Key", player.key_count);
             }
        }
    }
}
