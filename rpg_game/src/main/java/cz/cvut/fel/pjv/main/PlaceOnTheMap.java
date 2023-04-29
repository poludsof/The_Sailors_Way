package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.*;

public class PlaceOnTheMap {
    GamePanel gp;

    public PlaceOnTheMap(GamePanel gp) {
        this.gp = gp;
    }

    public void PlaceObject() {
        gp.obj_arr[0] = new Key();
        gp.obj_arr[0].worldX = 23 * gp.tileSize;
        gp.obj_arr[0].worldY = 7 * gp.tileSize;

        gp.obj_arr[1] = new Key();
        gp.obj_arr[1].worldX = 15 * gp.tileSize;
        gp.obj_arr[1].worldY = 90 * gp.tileSize;

        gp.obj_arr[2] = new RDoor();
        gp.obj_arr[2].worldX = 10 * gp.tileSize;
        gp.obj_arr[2].worldY = 88 * gp.tileSize;

        gp.obj_arr[3] = new LDoor();
        gp.obj_arr[3].worldX = 9 * gp.tileSize;
        gp.obj_arr[3].worldY = 88 * gp.tileSize;
    }
}
