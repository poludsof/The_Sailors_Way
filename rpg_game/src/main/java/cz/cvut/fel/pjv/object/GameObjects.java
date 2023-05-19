package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 The GameObjects class represents game objects in the game world.
 It contains information about the object's image, name, collision status,
 position, and solid area. It also provides methods for drawing the object
 on the screen and picking up objects in the game.
 */
public class GameObjects {

    // Object parameters.
    public BufferedImage image;
    public String name_object;
    public boolean collision_obj = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 80, 80); // solid area of object

    /**
     Draws the object on the screen.
     @param g2 the Graphics2D object used for drawing
     @param gp the GamePanel instance
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;  // Position of the object on the screen.
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if the object is within the visible area of the player's screen.
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Draw the object image.
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**
     Picks up the game object and performs corresponding actions based on the object's type.
     @param idx the index of the object in the object array
     @param gp  the GamePanel instance
     */
    public static void pickUpObj(int idx, GamePanel gp) {

        // If the object exists and has not yet been removed from the map.
        if (idx != -1 && gp.objArray[idx] != null) {
             if (gp.objArray[idx].name_object.equals("Key")) {

                gp.objArray[idx] = null;
                ++gp.player.key_count;
                gp.player.inventory.put("Key", gp.player.key_count);

                gp.sound.setMusic(2);
                LOGGER.info("Player: +1 key in inventory.");
             }

            else if (gp.objArray[idx].name_object.equals("BushLine")) {

                gp.objArray[idx] = null;
                ++gp.player.level;
                gp.sound.setMusic(5); // level up
                LOGGER.info("Player: +1 level.");

                // Remove right and left BushLine.
                if (idx == 6) { gp.objArray[idx + 1] = null; }
                if (idx == 7) { gp.objArray[idx - 1] = null; }
            }

            else if (gp.objArray[idx].name_object.equals("Heart")) {
                gp.objArray[idx] = null;
                 if (gp.player.heart_count < gp.player.max_health) {

                    ++gp.player.heart_count;
                     gp.sound.setMusic(4);  // get health
                     LOGGER.info("Player: +1 health.");

                 } else { LOGGER.info("You picked up health, but health is already at its maximum."); }
            }
            else if (gp.objArray[idx].name_object.equals("Rum")) {
                gp.objArray[idx] = null;
                LOGGER.info("Player: +1 rum in inventory. The player can move faster.");

                ++gp.player.rum_count;
                gp.player.inventory.put("Rum", gp.player.rum_count);
            }

            else if (gp.objArray[idx].name_object.equals("Map")) {
                gp.objArray[idx] = null;
                LOGGER.info("Player: +1 map in inventory. The player can see the whole game map.");

                ++gp.player.map_count;
                gp.player.inventory.put("Map", gp.player.map_count);
            }
            else if (gp.objArray[idx].name_object.equals("Sword")) {
                gp.objArray[idx] = null;
                LOGGER.info("Player: +1 sword in inventory. The player can increase his attack area.");

                ++gp.player.sword_count;
                gp.player.inventory.put("Sword", gp.player.sword_count);
            }

            else if (gp.player.key_count >= 1) {
                 --gp.player.key_count;
                 switch (gp.objArray[idx].name_object) {
                     case "RDoor", "LDoor" -> {
                         LOGGER.info("You opened the doors to the labyrinth. -1 key in inventory");
                         gp.objArray[idx] = null;

                         ++gp.player.level;
                         LOGGER.info("Player: +1 level.");

                         gp.sound.setMusic(5);  // level up
                         gp.sound.setMusic(1);  // open the door

                         // Remove right and left Door.
                         if (idx == 0) gp.objArray[idx + 1] = null;
                         else gp.objArray[idx - 1] = null;
                     }
                     case "RotatedLDoor", "RotatedRDoor" -> {  // doors to boss
                         LOGGER.info("You The player opened the doors to the boss. -1 key in inventory");
                         gp.objArray[idx] = null;

                         ++gp.player.level;
                         LOGGER.info("Player: +1 level.");

                         gp.sound.setMusic(1);  // open the door

                         // Remove right and left RotatedDoor.
                         if (idx == 2) gp.objArray[idx + 1] = null;
                         else gp.objArray[idx - 1] = null;
                     }
                     case "HellRDoor", "HellLDoor" -> {  // doors from boss
                         LOGGER.info("You opened the doors from the boss's room. -1 key in inventory");
                         gp.objArray[idx] = null;

                         gp.sound.setMusic(0); // rammstein

                         // Remove right and left HellDoor.
                         if (idx == 4) gp.objArray[idx + 1] = null;
                         else gp.objArray[idx - 1] = null;
                     }

                     default -> ++gp.player.key_count; //  If none of the doors open, add the key again
                 }

                 // Update the number of keys in the inventory
                 gp.player.inventory.put("Key", gp.player.key_count);
             }
        }
    }
}
