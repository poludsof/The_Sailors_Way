package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.enemy.Boss;
import cz.cvut.fel.pjv.enemy.Pirate;
import cz.cvut.fel.pjv.object.House;
import junit.framework.TestCase;

import java.awt.*;

public class CollisionCheckerTest extends TestCase {
    GamePanel gp = new GamePanel();

    public void testCheckCollisionEntity() {
        gp.player.solidArea = new Rectangle(20, 24, 32, 32); // Set up player's solid area
        gp.player.collision = false;
        gp.player.worldX = 0;
        gp.player.worldY = 0;

        Entity[] entities = new Entity[2]; // Create an array of entities
        entities[0] = new Pirate(gp);
        entities[0].solidArea = new Rectangle(22, 20, 32, 32); // Set up first entity's solid area
        entities[0].worldX = 0;
        entities[0].worldY = 0;

        entities[1] = new Pirate(gp);
        entities[1].solidArea = new Rectangle(150, 150, 0, 0); // Set up second entity's solid area
        entities[1].worldX = 0;
        entities[1].worldY = 0;

        int result = gp.checker.CheckCollisionEntity(gp.player); // Call the method being tested
        assertTrue(gp.player.collision); // Check if player's collision flag is set
        assertEquals(0, result); // Check if the method returned the correct index of the colliding entity
    }

    public void testCheckCollisionBoat() {
        gp.player.solidArea = new Rectangle(20, 24, 32, 32);

        gp.player.worldX = 0;  // set the position of the player on the map
        gp.player.worldY = 0;

        gp.ship = new Ship(gp);  // create a new boat for testing
        gp.ship.worldX = 0;  // set the position of the boat on the map
        gp.ship.worldY = 0;

        boolean result;
        // Case 1: The player is not colliding with the boat
        gp.ship.solidArea = new Rectangle(100, 100, 32, 32);
        result = gp.checker.CheckCollisionShip(gp.player);
        assertFalse("The player should not be colliding with the boat", result);

        // Case 2: The player is colliding with the boat
        gp.ship.solidArea = new Rectangle(0, 0, 32, 32);
        result = gp.checker.CheckCollisionShip(gp.player);
        assertTrue("The player should be colliding with the boat", result);
    }

    public void testCheckCollisionHouse() {
        gp.player.collision = false;
        gp.player.solidArea = new Rectangle(20, 24, 32, 32); // Set up player's solid area
        gp.player.worldX = 0;
        gp.player.worldY = 0;

        House house1 = new House(gp);
        house1.solidArea = new Rectangle(32, 32, 64, 64); // Set up house's solid area
        house1.worldX = 0;
        house1.worldY = 0;
        gp.house = house1;

        gp.checker.CheckCollisionHouse(gp.player); // Call the method being tested
        assertTrue(gp.player.collision);

        gp.player.collision = false;
        House house2 = new House(gp);
        house2.solidArea = new Rectangle(132, 132, 64, 64); // Set up house's solid area
        house2.worldX = 0;
        house2.worldY = 0;
        gp.house = house2;

        gp.checker.CheckCollisionHouse(gp.player); // Call the method being tested
        assertFalse(gp.player.collision);
    }

    public void testCheckCollisionBoss() {
        gp.player.solidArea = new Rectangle(0, 0, 20, 20);
        gp.player.worldX = 0;
        gp.player.worldY = 0;
        gp.player.collision = false;

        gp.boss = new Boss(gp);
        gp.boss.solidArea = new Rectangle(0, 8, 32, 32);
        gp.boss.worldX = 0;
        gp.boss.worldY = 0;

        // Вызываем метод CheckCollisionBoss
        boolean result = gp.checker.CheckCollisionBoss(gp.player);
        assertTrue(result);
        assertTrue(gp.player.collision);


        gp.boss.solidArea = new Rectangle(60, 68, 32, 32);
        gp.boss.worldX = 0;
        gp.boss.worldY = 0;
        gp.player.collision = false;

        result = gp.checker.CheckCollisionBoss(gp.player);
        assertFalse(result);
        assertFalse(gp.player.collision);

    }
}