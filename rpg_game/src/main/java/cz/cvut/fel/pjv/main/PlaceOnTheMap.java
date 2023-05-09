package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.monster.Pirate;
import cz.cvut.fel.pjv.object.*;

public class PlaceOnTheMap {
    GamePanel gp;

    public PlaceOnTheMap(GamePanel gp) {
        this.gp = gp;
    }

    public void PlaceObject() {
        gp.obj_arr[0] = new BlackKey();
        gp.obj_arr[0].worldX = 66 * gp.tileSize;
        gp.obj_arr[0].worldY = 97 * gp.tileSize;

        gp.obj_arr[1] = new BlackKey();
        gp.obj_arr[1].worldX = 15 * gp.tileSize;
        gp.obj_arr[1].worldY = 90 * gp.tileSize;

        gp.obj_arr[2] = new RDoor();
        gp.obj_arr[2].worldX = 10 * gp.tileSize;
        gp.obj_arr[2].worldY = 88 * gp.tileSize;

        gp.obj_arr[3] = new LDoor();
        gp.obj_arr[3].worldX = 9 * gp.tileSize;
        gp.obj_arr[3].worldY = 88 * gp.tileSize;

        gp.obj_arr[4] = new RotatedLDoor();
        gp.obj_arr[4].worldX = 78 * gp.tileSize;
        gp.obj_arr[4].worldY = 43 * gp.tileSize;

        gp.obj_arr[5] = new RotatedRDoor();
        gp.obj_arr[5].worldX = 78 * gp.tileSize;
        gp.obj_arr[5].worldY = 44 * gp.tileSize;

        gp.obj_arr[6] = new Key(); // key error
        gp.obj_arr[6].worldX = 85 * gp.tileSize;
        gp.obj_arr[6].worldY = 44 * gp.tileSize;

        gp.obj_arr[7] = new HellRDoor();
        gp.obj_arr[7].worldX = 90 * gp.tileSize;
        gp.obj_arr[7].worldY = 33 * gp.tileSize;

        gp.obj_arr[8] = new HellLDoor();
        gp.obj_arr[8].worldX = 89 * gp.tileSize;
        gp.obj_arr[8].worldY = 33 * gp.tileSize;

        gp.obj_arr[9] = new Heart();
        gp.obj_arr[9].worldX = 4 * gp.tileSize;
        gp.obj_arr[9].worldY = 95 * gp.tileSize;

        gp.obj_arr[10] = new BushLine();
        gp.obj_arr[10].worldX = 41 * gp.tileSize;
        gp.obj_arr[10].worldY = 54 * gp.tileSize;

        gp.obj_arr[11] = new BushLine();
        gp.obj_arr[11].worldX = 42 * gp.tileSize;
        gp.obj_arr[11].worldY = 53 * gp.tileSize;

        gp.obj_arr[12] = new Heart();
        gp.obj_arr[12].worldX = 15 * gp.tileSize;
        gp.obj_arr[12].worldY = 81 * gp.tileSize;

        gp.obj_arr[13] = new Heart();
        gp.obj_arr[13].worldX = 9 * gp.tileSize;
        gp.obj_arr[13].worldY = 70 * gp.tileSize;

        gp.obj_arr[14] = new Rum();
        gp.obj_arr[14].worldX = 6 * gp.tileSize;
        gp.obj_arr[14].worldY = 41 * gp.tileSize;

        gp.obj_arr[15] = new Heart();
        gp.obj_arr[15].worldX = 4 * gp.tileSize;
        gp.obj_arr[15].worldY = 34 * gp.tileSize;

        gp.obj_arr[16] = new Heart();
        gp.obj_arr[16].worldX = 29 * gp.tileSize;
        gp.obj_arr[16].worldY = 61 * gp.tileSize;

        gp.obj_arr[17] = new Heart();
        gp.obj_arr[17].worldX = 26 * gp.tileSize;
        gp.obj_arr[17].worldY = 54 * gp.tileSize;

        gp.obj_arr[18] = new Heart();
        gp.obj_arr[18].worldX = 40 * gp.tileSize;
        gp.obj_arr[18].worldY = 49 * gp.tileSize;

        gp.obj_arr[19] = new Heart();
        gp.obj_arr[19].worldX = 19 * gp.tileSize;
        gp.obj_arr[19].worldY = 42 * gp.tileSize;

        gp.boat = new Boat();
        gp.boat.worldX = 88 * gp.tileSize;
        gp.boat.worldY = 2 * gp.tileSize;
    }

    public void PlaceMonster() {
        gp.monsters[0] = new Pirate(gp);
        gp.monsters[0].worldX = 14 * gp.tileSize;
        gp.monsters[0].worldY = 93 * gp.tileSize;

        gp.monsters[1] = new Pirate(gp);
        gp.monsters[1].worldX = 5 * gp.tileSize;
        gp.monsters[1].worldY = 93 * gp.tileSize;

        gp.monsters[2] = new Pirate(gp);
        gp.monsters[2].worldX = 10 * gp.tileSize;
        gp.monsters[2].worldY = 89 * gp.tileSize;

        gp.monsters[3] = new Pirate(gp);
        gp.monsters[3].worldX = 3 * gp.tileSize;
        gp.monsters[3].worldY = 54 * gp.tileSize;

        gp.monsters[4] = new Pirate(gp);
        gp.monsters[4].worldX = 8 * gp.tileSize;
        gp.monsters[4].worldY = 49 * gp.tileSize;

        gp.monsters[5] = new Pirate(gp);
        gp.monsters[5].worldX = 16 * gp.tileSize;
        gp.monsters[5].worldY = 56 * gp.tileSize;

        gp.monsters[6] = new Pirate(gp);
        gp.monsters[6].worldX = 10 * gp.tileSize;
        gp.monsters[6].worldY = 40 * gp.tileSize;

        gp.monsters[7] = new Pirate(gp);
        gp.monsters[7].worldX = 28 * gp.tileSize;
        gp.monsters[7].worldY = 44 * gp.tileSize;

        gp.monsters[8] = new Pirate(gp);
        gp.monsters[8].worldX = 28 * gp.tileSize;
        gp.monsters[8].worldY = 58 * gp.tileSize;

        gp.monsters[9] = new Pirate(gp);
        gp.monsters[9].worldX = 33 * gp.tileSize;
        gp.monsters[9].worldY = 55 * gp.tileSize;

        gp.monsters[10] = new Pirate(gp);
        gp.monsters[10].worldX = 22 * gp.tileSize;
        gp.monsters[10].worldY = 39 * gp.tileSize;

    }

}
