package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.main.GamePanel;
import junit.framework.TestCase;

/**
 Test for the GameObjects class.
 */
public class GameObjectsTest extends TestCase {
    GamePanel gp = new GamePanel();

    /**
     Test to check picking up objects by the player.
     */
    public void testPickUpObj() {
        gp.objArray[0] = new Key();
        gp.objArray[1] = new Rum();
        gp.objArray[2] = new Sword();
        gp.objArray[3] = new Map();
        gp.objArray[4] = new Heart();

        int KeyCount = gp.player.key_count;
        GameObjects.pickUpObj(0, gp);
        assertEquals(KeyCount + 1, gp.player.key_count);
        assertNotNull("The method does not fill the player's inventory.", gp.player.inventory);

        int RumCount = gp.player.rum_count;
        GameObjects.pickUpObj(1, gp);
        assertEquals(RumCount + 1, gp.player.rum_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[1]);

        int SwordCount = gp.player.sword_count;
        GameObjects.pickUpObj(2, gp);
        assertEquals(SwordCount + 1, gp.player.sword_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[2]);

        int MapCount = gp.player.map_count;
        GameObjects.pickUpObj(3, gp);
        assertEquals(MapCount + 1, gp.player.map_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[3]);

        int HeartCount = gp.player.heart_count;
        GameObjects.pickUpObj(4, gp);
        if (gp.player.heart_count < gp.player.max_health)
            assertEquals(HeartCount + 1, gp.player.heart_count);
        else
            assertEquals(HeartCount, gp.player.heart_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[4]);
    }

    /**
     Test to check picking up all types of doors by the player.
     */
    public void testAllDoors() {
        GamePanel gp = new GamePanel();

        for (int i = 0; i < 6; ++i) {
            gp.objArray[0] = new RDoor();
            gp.objArray[1] = new LDoor();
            gp.objArray[2] = new RotatedLDoor();
            gp.objArray[3] = new RotatedRDoor();
            gp.objArray[4] = new HellRDoor();
            gp.objArray[5] = new HellLDoor();
            gp.player.key_count = 1;
            GameObjects.pickUpObj(i, gp);
            assertEquals(0, gp.player.key_count);
            assertNull("The method does not clear the array after picking up the item.", gp.objArray[i]);
        }

        gp.objArray[4] = new HellRDoor();
        gp.objArray[5] = new HellLDoor();
        gp.player.key_count = 0;
        for (int i = 0; i < 6; ++i) {
            GameObjects.pickUpObj(i, gp);
            assertEquals(0, gp.player.key_count);
            assertNotNull("The door is removed from the array when the player has no key.", gp.objArray[i]);
        }
    }
}