package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.State;
import junit.framework.TestCase;

/**
 Test for the Player class.
 */
public class PlayerTest extends TestCase {
    GamePanel gp = new GamePanel();
    String filename = "src/dataJson/new_game.json";

    /**
     Verifies if the default values are set correctly.
     */
    public void testSetDefaultValues() {
        gp.player.setDefaultValues(filename);
        assertEquals(10 * gp.tileSize, gp.player.worldX);
        assertEquals(93 * gp.tileSize, gp.player.worldY);
        assertEquals(6, gp.player.speed);
        assertEquals(6, gp.player.heart_count);
        assertEquals(0, gp.player.key_count);
        assertEquals(0, gp.player.rum_count);
        assertEquals(0, gp.player.map_count);
        assertEquals(0, gp.player.sword_count);
        assertEquals(1, gp.player.level);
        assertEquals(0, gp.player.dead_pirate_count);
    }

    /**
     Verifies if the inventory is filled correctly based on the player's counts.
     */
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

    /**
     Verifies the behavior of the player's update based on different options.
     */
    public void testUpdate() {

        checkEndOfGameState();

        checkAttackState();
        checkAttackArea();

        checkRumSpeed();
    }

    /**
     Check the player's movement on the map.
     */
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

    /**
     Checks the game's end state when the player has no life left.
     */
    private void checkEndOfGameState() {
        gp.player.setDefaultValues(filename);

        gp.player.heart_count = 0;
        gp.player.update();
        assertEquals(State.GAME_OVER, gp.state);
    }

    /**
     Checks attack state after press space.
     */
    private void checkAttackState() {
        gp.player.setDefaultValues(filename);

        gp.player.keyH.spacePressed = true;
        gp.player.update();
        assertTrue(gp.player.attacking);
    }

    /**
     Checks the changes of the attacking area when the Sword is applied.
     */
    private void checkAttackArea() {
        gp.player.setDefaultValues(filename);

        gp.player.keyH.swordButton = true;
        gp.player.attacking = false;
        gp.player.sword_count = 1;
        gp.player.update();
        assertEquals(60, gp.player.attackArea.width);
        assertEquals(60, gp.player.attackArea.height);
    }

    /**
     Check if the data has (not) changed when the rum_key_button is not pressed/pressed.
     */
    private void checkRumSpeed() {
        gp.player.setDefaultValues(filename);

        // Check speed increase when rum_key_button is pressed.
        gp.player.attacking = false;
        gp.player.keyH.rumButton = true;
        gp.player.rum_count = 1;
        int past_speed = gp.player.speed;
        gp.player.update();
        assertTrue(gp.player.rum_time_start);
        assertEquals(past_speed + 4, gp.player.speed);
        assertEquals(0, gp.player.rum_count);

        gp.player.attacking = false;
        gp.player.rum_time_start = true;
        gp.player.rum_time = 5 * 60 + 1;
        past_speed = gp.player.speed;
        gp.player.update();
        assertFalse(gp.player.rum_time_start);
        assertEquals(past_speed - 4, gp.player.speed);

    }
}