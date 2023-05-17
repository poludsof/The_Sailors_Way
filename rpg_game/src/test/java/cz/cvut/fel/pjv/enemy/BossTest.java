package cz.cvut.fel.pjv.enemy;

import cz.cvut.fel.pjv.main.GamePanel;
import junit.framework.TestCase;

public class BossTest extends TestCase {
    GamePanel gp = new GamePanel();

    public void testSetDefaultValues() {
        gp.boss.setDefaultValues("rpg_game/src/dataJson/test_boss.json");
        assertEquals(666 * gp.tileSize, gp.boss.worldX); // Проверяем, что количество ключей не уменьшилось
        assertEquals(4 * gp.tileSize, gp.boss.worldY); // Проверяем, что количество ключей не уменьшилось
        assertEquals(1, gp.boss.speed); // Проверяем, что количество ключей не уменьшилось
        assertEquals(0, gp.boss.heart_count); // Проверяем, что количество ключей не уменьшилось
    }

    public void testRandomBossAction() {
        String past_direction = gp.boss.direction;
        gp.boss.time = 0;
        gp.boss.randomBossAction();
        assertEquals(1, gp.boss.time);
        assertEquals(past_direction, gp.boss.direction); // никак не изменилось

        gp.boss.time = 80;
        gp.boss.randomBossAction();
        assertEquals(0, gp.boss.time);
    }

    public void testFightBoss() {
        // не заходит в иф
        gp.boss.timeToDamage = true;
        gp.boss.fightBoss();
        assertTrue(gp.boss.timeToDamage);

        gp.player.attacking = true;
        gp.boss.fightBoss();
        assertTrue(gp.boss.timeToDamage);

        //in if
        gp.boss.timeToDamage = false;
        gp.player.attacking = true;
        gp.boss.heart_count = 5;
        gp.boss.fightBoss();
        assertEquals(4, gp.boss.heart_count);
        assertTrue(gp.boss.showHealth);

        gp.boss.timeToDamage = false;
        gp.player.attacking = true;
        gp.boss.heart_count = 0;
        gp.boss.fightBoss();
        System.out.println(gp.boss);
        assertNull("The method does not remove the boss when it runs out of lives", gp.boss);
    }
}