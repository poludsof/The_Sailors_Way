package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.enemy.Boss;
import cz.cvut.fel.pjv.object.House;
import junit.framework.TestCase;

/**
 Test for the CollisionChecker class.
 */
public class CollisionCheckerTest extends TestCase {
    GamePanel gp = new GamePanel();
    String filename = "src/dataJson/new_game.json";

    /**
     Test to check collision between the player and the boat.
     */
    public void testCheckCollisionBoat() {
        gp.player.setDefaultValues(filename);
        gp.player.worldX = 0;  // set the position of the player on the map
        gp.player.worldY = 0;

        gp.ship = new Ship(gp);  // create a new boat for testing

        boolean result;

        // Case 1: The player is not colliding with the boat
        gp.ship.worldX = 100;  // set the position of the boat on the map
        gp.ship.worldY = 100;
        result = gp.checker.CheckCollisionShip(gp.player);
        assertFalse("The player should not be colliding with the boat", result);

        // Case 2: The player is colliding with the boat
        gp.ship.worldX = -100;
        gp.ship.worldY = 0;
        result = gp.checker.CheckCollisionShip(gp.player);
        assertTrue("The player should be colliding with the boat", result);
    }

    /**
     Test to check collision between the player and the house.
     */
    public void testCheckCollisionHouse() {
        gp.player.setDefaultValues(filename);
        gp.player.collision = false;
        gp.player.worldX = 0;
        gp.player.worldY = 0;

        // Case 1: The player is colliding with the house
        House house1 = new House(gp);
        house1.worldX = -50;
        house1.worldY = 0;
        gp.house = house1;

        gp.checker.CheckCollisionHouse(gp.player); // Call the method being tested
        assertTrue("The player should be colliding with the house", gp.player.collision);

        // Case 2: The player is not colliding with the house
        gp.player.collision = false;
        House house2 = new House(gp);
        house2.worldX = 100;
        house2.worldY = 100;
        gp.house = house2;

        gp.checker.CheckCollisionHouse(gp.player); // Call the method being tested
        assertFalse("The player should not be colliding with the house", gp.player.collision);
    }

    /**
     Test case to check collision between the player and the boss.
     */
    public void testCheckCollisionBoss() {
        gp.player.setDefaultValues(filename);
        gp.player.worldX = 0;
        gp.player.worldY = 0;

        gp.boss = new Boss(gp);
        gp.boss.worldX = 0;
        gp.boss.worldY = 0;

        // Case 1: The player is colliding with the boss
        boolean result = gp.checker.CheckCollisionBoss(gp.player);
        assertTrue(result);
        assertTrue("The player should be colliding with the boss", gp.player.collision);

        // Case 2: The player is not colliding with the boss
        gp.boss.worldX = 100;
        gp.boss.worldY = 100;
        gp.player.collision = false;

        result = gp.checker.CheckCollisionBoss(gp.player);
        assertFalse(result);
        assertFalse("The player should not be colliding with the boss", gp.player.collision);

    }
}