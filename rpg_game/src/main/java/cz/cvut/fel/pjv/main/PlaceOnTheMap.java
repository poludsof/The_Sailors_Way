package cz.cvut.fel.pjv.main;

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

        gp.obj_arr[6] = new Key();
        gp.obj_arr[6].worldX = 85 * gp.tileSize;
        gp.obj_arr[6].worldY = 44 * gp.tileSize;

        gp.obj_arr[7] = new HellRDoor();
        gp.obj_arr[7].worldX = 90 * gp.tileSize;
        gp.obj_arr[7].worldY = 33 * gp.tileSize;

        gp.obj_arr[8] = new HellLDoor();
        gp.obj_arr[8].worldX = 89 * gp.tileSize;
        gp.obj_arr[8].worldY = 33 * gp.tileSize;

        gp.boat = new Boat();
        gp.boat.worldX = 88 * gp.tileSize;
        gp.boat.worldY = 2 * gp.tileSize;
    }
}
