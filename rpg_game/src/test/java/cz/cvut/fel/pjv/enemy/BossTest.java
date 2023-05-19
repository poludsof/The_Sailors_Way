package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.main.GamePanel;
import junit.framework.TestCase;

/**
 Test for the Boss class.
 */
public class BossTest extends TestCase {
    GamePanel gp = new GamePanel();
    String filename = "rpg_game/src/dataJson/test_boss.json";

    /**
     Verifies if the default values are set correctly.
     */
    public void testSetDefaultValues() {
        gp.boss.setDefaultValues(filename);
        assertEquals(666 * gp.tileSize, gp.boss.worldX);
        assertEquals(4 * gp.tileSize, gp.boss.worldY);
        assertEquals(1, gp.boss.speed);
        assertEquals(0, gp.boss.heart_count);
    }

    /**
     Checks the change of direction of the boss.
     */
    public void testRandomBossAction() {
        // Until the number of frames reaches a certain value, the boss moves in one direction.
        String past_direction = gp.boss.direction;
        gp.boss.frames = 0;
        gp.boss.randomBossAction();
        assertEquals(1, gp.boss.frames);
        assertEquals(past_direction, gp.boss.direction);

        // The direction has changed.
        gp.boss.frames = 80;
        past_direction = gp.boss.direction;
        gp.boss.randomBossAction();
        assertEquals(1, gp.boss.frames);
        assertNotSame(past_direction, gp.boss.direction);
    }

    /**
     Verifies the behavior when the boss is hit by the player's attack.
     */
    public void testFightBoss() {
        // If the boss is in a state after the hit, he cannot take the hit again.
        gp.boss.timeToDamage = true;
        gp.boss.fightBoss();
        assertTrue(gp.boss.timeToDamage);

        // The player attacks the boss.
        gp.boss.timeToDamage = false;
        gp.player.attacking = true;
        gp.boss.heart_count = 5;
        gp.boss.fightBoss();
        assertEquals(4, gp.boss.heart_count);
        assertTrue(gp.boss.showHealth);

        // The boss is dead and must disappear.
        gp.boss.timeToDamage = false;
        gp.player.attacking = true;
        gp.boss.heart_count = 0;
        gp.boss.fightBoss();
        assertNull("The method does not remove the boss when it runs out of lives.", gp.boss);
    }
}