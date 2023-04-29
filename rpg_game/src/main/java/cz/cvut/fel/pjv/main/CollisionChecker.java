package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker (GamePanel gp) {
        this.gp = gp;
    }

    public void CheckCollisionTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
        }
    }

    public int CheckCollisionObj(Entity entity) {
        int idx = -1;
        for (int i = 0; i < gp.obj_arr.length; ++i) {
            if (gp.obj_arr[i] != null) {
                entity.solidArea.x += entity.worldX;  // get position on the map
                entity.solidArea.y += entity.worldY;

                gp.obj_arr[i].solidArea.x += gp.obj_arr[i].worldX;  // get position on the map
                gp.obj_arr[i].solidArea.y += gp.obj_arr[i].worldY;

                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                        if (gp.obj_arr[i].solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
                            if (gp.obj_arr[i].collision_obj) // is solid obj
                                entity.collision = true;
                            idx = i;
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                        if (gp.obj_arr[i].solidArea.intersects(entity.solidArea)) {
                            if (gp.obj_arr[i].collision_obj) // is solid obj
                                entity.collision = true;
                            idx = i;
                        }
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                        if (gp.obj_arr[i].solidArea.intersects(entity.solidArea)) {
                            if (gp.obj_arr[i].collision_obj) // is solid obj
                                entity.collision = true;
                            idx = i;
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                        if (gp.obj_arr[i].solidArea.intersects(entity.solidArea)) {
                            if (gp.obj_arr[i].collision_obj) // is solid obj
                                entity.collision = true;
                            idx = i;
                        }
                    }
                }
                entity.solidArea.x = 20;
                entity.solidArea.y = 24;

                gp.obj_arr[i].solidArea.x = 0;  // get position on the map
                gp.obj_arr[i].solidArea.y = 0;
            }
        }
        return idx;
    }
}
