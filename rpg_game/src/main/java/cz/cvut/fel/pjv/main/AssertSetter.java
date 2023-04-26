package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.Key;

public class AssertSetter {
    GamePanel gp;

    public AssertSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj_arr[0] = new Key();
        gp.obj_arr[0].worldX = 23 * gp.tileSize;
        gp.obj_arr[0].worldY = 7 * gp.tileSize;
    }
}
