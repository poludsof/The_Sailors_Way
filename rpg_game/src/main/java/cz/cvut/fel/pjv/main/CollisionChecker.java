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

        int tileNum1 = 0, tileNum2 = 0;

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
        if (gp.tileM.tile[tileNum1].name.equals("xz_lava4.png")) {
            if (entity.heart_count > 0)
                entity.heart_count--;
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
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                    }
                }
                if (gp.obj_arr[i].solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
                    if (gp.obj_arr[i].collision_obj) // is solid obj
                        entity.collision = true;
                    idx = i;
                }

                entity.solidArea.x = 20;
                entity.solidArea.y = 24;

                gp.obj_arr[i].solidArea.x = 0;  // get position on the map
                gp.obj_arr[i].solidArea.y = 0;
            }
        }
        return idx;
    }

    public int CheckCollisionEntity(Entity entity, Entity[] arrayEntity) { // todo test
//        System.out.println("X:" +  entity.worldX + "  :  " + entity.solidArea.width);
        int idx = -1;
        for (int i = 0; i < arrayEntity.length; ++i) {
            if (arrayEntity[i] != null) {
                entity.solidArea.x += entity.worldX;  // get position on the map
                entity.solidArea.y += entity.worldY;

                arrayEntity[i].solidArea.x += arrayEntity[i].worldX;  // get position on the map
                arrayEntity[i].solidArea.y += arrayEntity[i].worldY;

                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                    }
                }
                if (arrayEntity[i].solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
                    entity.collision = true;
                    idx = i;
                }

                entity.solidArea.x = 20;
                entity.solidArea.y = 24;

                arrayEntity[i].solidArea.x = 20;  // get position on the map
                arrayEntity[i].solidArea.y = 24;
            }
        }
        return idx;
    }

    public boolean CheckCollisionPlayer(Entity entity) {
        boolean fight = false;
        if (gp.player != null) {
            entity.solidArea.x += entity.worldX;  // get position on the map
            entity.solidArea.y += entity.worldY;
            gp.player.solidArea.x += gp.player.worldX;  // get position on the map
            gp.player.solidArea.y += gp.player.worldY;
            switch (entity.direction) {
                case "up" -> {
                    entity.solidArea.y -= entity.speed;
                }
                case "down" -> {
                    entity.solidArea.y += entity.speed;
                }
                case "right" -> {
                    entity.solidArea.x += entity.speed;
                }
                case "left" -> {
                    entity.solidArea.x -= entity.speed;
                }
            }
            if (entity.solidArea.intersects(gp.player.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
                entity.collision = true;
                fight = true;
            }
            entity.solidArea.x = 20;
            entity.solidArea.y = 24;
            gp.player.solidArea.x = 20;  // get position on the map
            gp.player.solidArea.y = 24;
        }
        return fight;
    }

    public boolean CheckCollisionBoat(Entity entity) {
        boolean ret = false;
        entity.solidArea.x += entity.worldX;  // get position on the map
        entity.solidArea.y += entity.worldY;

        gp.boat.solidArea.x += gp.boat.worldX;  // get position on the map
        gp.boat.solidArea.y += gp.boat.worldY;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
        }
        if (gp.boat.solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
            ret = true;
        }
        entity.solidArea.x = 20;
        entity.solidArea.y = 24;
        gp.boat.solidArea.x = 100;  // get position on the map
        gp.boat.solidArea.y = 0;

        return ret;
    }

    public void CheckCollisionHouse(Entity entity) {
        entity.solidArea.x += entity.worldX;  // get position on the map
        entity.solidArea.y += entity.worldY;

        gp.house.solidArea.x += gp.house.worldX;  // get position on the map
        gp.house.solidArea.y += gp.house.worldY;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
        }
        if (gp.house.solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
            entity.collision = true;
        }
        entity.solidArea.x = 20;
        entity.solidArea.y = 24;
        gp.house.solidArea.x = gp.tileSize;  // get position on the map
        gp.house.solidArea.y = 0;
    }

    public boolean CheckCollisionBoss(Entity entity) {
        boolean fight = false;
        if (gp.boss != null) {
            entity.solidArea.x += entity.worldX;  // get position on the map
            entity.solidArea.y += entity.worldY;

            gp.boss.solidArea.x += gp.boss.worldX;  // get position on the map
            gp.boss.solidArea.y += gp.boss.worldY;

            switch (entity.direction) {
                case "up" -> {
                    entity.solidArea.y -= entity.speed;
                }
                case "down" -> {
                    entity.solidArea.y += entity.speed;
                }
                case "right" -> {
                    entity.solidArea.x += entity.speed;
                }
                case "left" -> {
                    entity.solidArea.x -= entity.speed;
                }
            }
            if (gp.boss.solidArea.intersects(entity.solidArea)) { // if the solid area of the object intersects with the solid area of the player, the .intersects returns true
                entity.collision = true;
                fight = true;
            }

            entity.solidArea.x = 20;
            entity.solidArea.y = 24;

            gp.boss.solidArea.x = 60;  // get position on the map
            gp.boss.solidArea.y = 48;
        }
        return fight;
    }
}
