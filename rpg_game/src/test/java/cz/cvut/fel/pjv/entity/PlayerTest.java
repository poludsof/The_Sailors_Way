package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.State;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    GamePanel gp = new GamePanel();
    public void testSetDefaultValues() {
        gp.player.setDefaultValues("rpg_game/src/dataJson/test_player.json");
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
        gp.player.heart_count = 0; // check end of game state
        gp.player.update();
        assertEquals(State.GAME_OVER, gp.state);

        gp.player.timeToDamage = true; // check damage timer
        gp.player.damageCounter = 59;
        gp.player.update();
        assertFalse(gp.player.timeToDamage);

        gp.player.keyH.spacePressed = true; // check attack after press space
        gp.player.update();
        assertTrue(gp.player.attacking);

        gp.player.keyH.swordButton = true; // check the changes of the attacking area when the Sword is applied
        gp.player.attacking = false;
        gp.player.sword_count = 1;
        gp.player.update();
        assertEquals(60, gp.player.attackArea.width);
        assertEquals(60, gp.player.attackArea.height);

        gp.player.rum_time_start = false; // check if the data has not changed when the rum_key_button is not pressed
        int past_speed = gp.player.speed;
        assertEquals(past_speed, gp.player.speed);

        gp.player.keyH.upPressed = true;
        gp.player.attacking = false;
        gp.player.keyH.rumButton = true;
        gp.player.rum_count = 1;
        past_speed = gp.player.speed;
        gp.player.update();
        assertTrue(gp.player.rum_time_start);
        assertEquals(past_speed + 4, gp.player.speed);
        assertEquals(0, gp.player.rum_count);

        gp.player.keyH.upPressed = true;
        gp.player.attacking = false;
        gp.player.rum_time_start = true;
        gp.player.rum_time = 5 * 60 + 1;
        past_speed = gp.player.speed;
        gp.player.update();
        assertFalse(gp.player.rum_time_start);
        assertEquals(past_speed - 4, gp.player.speed);
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