package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.main.GamePanel;
import junit.framework.TestCase;

public class GameObjectsTest extends TestCase {

    public void testPickUpObj() {

        GamePanel gp = new GamePanel();
        GameObjects obj = new GameObjects();

        // Проверяем, что метод корректно обрабатывает объекты типа "Key"
        gp.objArray[0] = new Key();
        gp.objArray[1] = new Rum();
        gp.objArray[2] = new Sword();
        gp.objArray[3] = new Map();
        gp.objArray[4] = new Heart();

        int Level = gp.player.level;
        int KeyCount = gp.player.key_count;
        obj.pickUpObj(0, gp);
        assertEquals(Level + 1, gp.player.level); // Проверяем, что уровень увеличился на 1
        assertEquals(KeyCount + 1, gp.player.key_count); // Проверяем, что количество ключей уменьшилось на 1
        assertNotNull("The method does not fill the player's inventory.", gp.player.inventory);

        int RumCount = gp.player.rum_count;
        obj.pickUpObj(1, gp);
        assertEquals(RumCount + 1, gp.player.rum_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[1]);

        int SwordCount = gp.player.sword_count;
        obj.pickUpObj(2, gp);
        assertEquals(SwordCount + 1, gp.player.sword_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[2]);

        int MapCount = gp.player.map_count;
        obj.pickUpObj(3, gp);
        assertEquals(MapCount + 1, gp.player.map_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[3]);

        int HeartCount = gp.player.heart_count;
        obj.pickUpObj(4, gp);
        if (gp.player.heart_count < gp.player.max_health)
            assertEquals(HeartCount + 1, gp.player.heart_count);
        else
            assertEquals(HeartCount, gp.player.heart_count);
        assertNull("The method does not clear the array after picking up the item.", gp.objArray[4]);
    }

    public void testAllDoors() {
        GamePanel gp = new GamePanel();
        GameObjects obj = new GameObjects();

        for (int i = 0; i < 6; ++i) {
            gp.objArray[0] = new RDoor();
            gp.objArray[1] = new LDoor();
            gp.objArray[2] = new RotatedLDoor();
            gp.objArray[3] = new RotatedRDoor();
            gp.objArray[4] = new HellRDoor();
            gp.objArray[5] = new HellLDoor();
            gp.player.key_count = 1;
            obj.pickUpObj(i, gp);
            assertEquals(0, gp.player.key_count); // Проверяем, что количество ключей уменьшилось на 1
            assertNull("The method does not clear the array after picking up the item.", gp.objArray[i]);
        }

        gp.objArray[4] = new HellRDoor();
        gp.objArray[5] = new HellLDoor();
        gp.player.key_count = 0;
        for (int i = 0; i < 6; ++i) {
            obj.pickUpObj(i, gp);
            assertEquals(0, gp.player.key_count); // Проверяем, что количество ключей не уменьшилось
            assertNotNull("The door is removed from the array when the player has no key.", gp.objArray[i]);
        }
    }
}