package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    GamePanel gp = new GamePanel();
    public void testSetDefaultValues() {
        gp.player.setDefaultValues("target/test_player.json");
        assertEquals(999 * gp.tileSize, gp.player.worldX);
        assertEquals(666 * gp.tileSize, gp.player.worldY);
        assertEquals(6, gp.player.speed);
        assertEquals(6, gp.player.heart_count);
        assertEquals(2, gp.player.key_count);
        assertEquals(3, gp.player.rum_count);
        assertEquals(4, gp.player.map_count);
        assertEquals(5, gp.player.sword_count);
        assertEquals(10, gp.player.level);
        assertEquals(9, gp.player.dead_pirate_count);
    }

    public void testFillInventory() {
        gp.player.map_count = 1;
        gp.player.rum_count = 2;
        gp.player.key_count = 3;
        gp.player.sword_count = 4;
        gp.player.fillInventory();
        assertNotNull(gp.player.inventory);
        assertEquals(1, (int) gp.player.inventory.get("Map"));
        assertEquals(2, (int) gp.player.inventory.get("Rum"));
        assertEquals(3, (int) gp.player.inventory.get("Key"));
        assertEquals(4, (int) gp.player.inventory.get("Sword"));
    }

    public void testUpdate() {
    }

    public void testDoMove() {
        int pastCoord = gp.player.worldX;
        gp.player.direction = "left";
        gp.player.doMove();
        assertEquals(pastCoord - gp.player.speed, gp.player.worldX);

        pastCoord = gp.player.worldX;
        gp.player.direction = "right";
        gp.player.doMove();
        assertEquals(pastCoord + gp.player.speed, gp.player.worldX);

        pastCoord = gp.player.worldY;
        gp.player.direction = "up";
        gp.player.doMove();
        assertEquals(pastCoord - gp.player.speed, gp.player.worldY);

         pastCoord = gp.player.worldY;
         gp.player.direction = "down";
         gp.player.doMove();
         assertEquals(pastCoord + gp.player.speed, gp.player.worldY);
    }
}