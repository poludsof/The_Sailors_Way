package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

public class GameObjects {
    public BufferedImage image;
    public String name_object;
    public boolean collision_obj = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 80, 80); // solid area of object

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

    public static void pickUpObj(int idx, GamePanel gp) { // todo delete player
        if (idx != -1 && gp.obj_arr[idx] != null) {  // If the object exists and has not yet been removed from the map.
             if (gp.obj_arr[idx].name_object.equals("Key")) {
//                if (idx == 0) { ++gp.player.level; gp.playMusic(5);
//                    System.out.println("KEY\n"); }
                gp.obj_arr[idx] = null;
                ++gp.player.key_count;
                LOGGER.info("Player: +1 key in inventory.");
                gp.player.inventory.put("Key", gp.player.key_count);
                gp.playMusic(2);
            }

            else if (gp.obj_arr[idx].name_object.equals("BushLine")) {
                gp.obj_arr[idx] = null;
                ++gp.player.level;
                LOGGER.info("Player: +1 level.");
                gp.playMusic(5); // level up
                if (idx == 6) { gp.obj_arr[idx + 1] = null; }
                if (idx == 7) { gp.obj_arr[idx - 1] = null; }
            }

            else if (gp.obj_arr[idx].name_object.equals("Heart")) {
                gp.obj_arr[idx] = null;
                if (gp.player.heart_count < gp.player.max_health) { // todo max health
                    LOGGER.info("Player: +1 health.");
                    ++gp.player.heart_count;
                    gp.playMusic(4); // get health
                } else { LOGGER.info("You picked up health, but health is already at its maximum."); }
            }
            else if (gp.obj_arr[idx].name_object.equals("Rum")) {
                gp.obj_arr[idx] = null;
                LOGGER.info("Player: +1 rum in inventory. The player can move faster.");
                ++gp.player.rum_count;
                gp.player.inventory.put("Rum", gp.player.rum_count);
            }

            else if (gp.obj_arr[idx].name_object.equals("Map")) {
                gp.obj_arr[idx] = null;
                LOGGER.info("Player: +1 map in inventory. The player can see the whole game map.");
                ++gp.player.map_count;
                gp.player.inventory.put("Map", gp.player.map_count);
            }
            else if (gp.obj_arr[idx].name_object.equals("Sword")) {
                gp.obj_arr[idx] = null;
                LOGGER.info("Player: +1 sword in inventory. The player can increase his attack area.");
                ++gp.player.sword_count;
                gp.player.inventory.put("Sword", gp.player.sword_count);
            }

            else if (gp.player.key_count >= 1) {
                 --gp.player.key_count;
                 switch (gp.obj_arr[idx].name_object) {
                     case "RDoor", "LDoor" -> {
                         LOGGER.info("You opened the doors to the labyrinth. -1 key in inventory");
                         gp.obj_arr[idx] = null;
                         ++gp.player.level;
                         LOGGER.info("Player: +1 level.");
                         gp.playMusic(5); // level up
                         gp.playMusic(1); // open the door
                         if (idx == 0) gp.obj_arr[idx + 1] = null;
                         else gp.obj_arr[idx - 1] = null;
                     }
                     case "RotatedLDoor", "RotatedRDoor" -> {  // doors to boss
                         LOGGER.info("You The player opened the doors to the boss. -1 key in inventory");
                         ++gp.player.level;
                         LOGGER.info("Player: +1 level.");
                         gp.obj_arr[idx] = null;
                         gp.playMusic(1); // open the door
                         if (idx == 2) gp.obj_arr[idx + 1] = null;
                         else gp.obj_arr[idx - 1] = null;
                     }
                     case "HellRDoor", "HellLDoor" -> {  // doors from boss
                         LOGGER.info("You opened the doors from the boss's room. -1 key in inventory");
                         gp.obj_arr[idx] = null;
                         gp.playMusic(0);
                         if (idx == 4) gp.obj_arr[idx + 1] = null;
                         else gp.obj_arr[idx - 1] = null;
                     }
                     default -> ++gp.player.key_count; //  If none of the doors open, add the key again
                 }
                 gp.player.inventory.put("Key", gp.player.key_count); // Update the number of keys in the inventory
             }
        }

    }
}