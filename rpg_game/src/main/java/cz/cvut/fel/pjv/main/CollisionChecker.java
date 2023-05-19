package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 The CollisionChecker class is responsible for checking collisions
 between entities and game objects.
 */
public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker (GamePanel gp) { this.gp = gp; }

    /**
     Checks collision between an entity and tiles on the map.
     Updates the entity's collision flag if a collision occurs.
     @param entity The entity for collision check
     */
    public void CheckCollisionTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the positions of each corner of the player to then determine which tiles he is on.
        // +/- speed predicts which coordinate the player will be at in one step.
        int entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        int entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        int entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        int entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;

        int tile2, tile1 = 0;

        switch (entity.direction) {
            case "up" -> {
                // Get the tile numbers for the top-left and top-right corners of the entity.
                tile1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tile2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                // Get the tile numbers for the bottom-left and bottom-right corners of the entity.
                tile1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tile2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                // Get the tile numbers for the top-left and bottom-left corners of the entity.
                tile1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tile2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                // Get the tile numbers for the top-right and bottom-right corners of the entity.
                tile1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tile2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision) {
                    entity.collision = true;
                }
            }
        }

        // Check if the entity is colliding with a specific tile (e.g., lava).
        if (gp.tileM.tile[tile1].name.equals("xz_lava4.png")) {
            if (entity.heart_count > 0) {
                entity.heart_count--;
                LOGGER.info("You lost 1 health on the lava.");
            }
            if (entity.heart_count == 0)
                LOGGER.info("You died by touching the lava.");
        }
    }

    /**
     Checks collision between an entity and objects in the game.
     @param entity The entity for collision check
     @return The index of the collided object in the objArray, or -1 if no collision occurred
     */
    public int CheckCollisionObj(Entity entity) {
        int idx = -1;
        for (int i = 0; i < gp.objArray.length; ++i) {
            if (gp.objArray[i] != null) {

                // Calculate where the solid part of the player is on the map.
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // Calculate where the solid part of the object is on the map.
                gp.objArray[i].solidArea.x += gp.objArray[i].worldX;
                gp.objArray[i].solidArea.y += gp.objArray[i].worldY;

                // Make a prediction for a collision depending on which way the player is moving.
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                }

                // If the solid area of the object intersects with the solid area of the player, the .intersects returns true
                if (gp.objArray[i].solidArea.intersects(entity.solidArea)) {
                    if (gp.objArray[i].collision_obj) // is solid obj
                        entity.collision = true;
                    idx = i;
                }

                // Return the initial parameters of the player and the object.
                entity.solidArea.x = 20;
                entity.solidArea.y = 24;

                gp.objArray[i].solidArea.x = 0;
                gp.objArray[i].solidArea.y = 0;
            }
        }
        return idx;
    }

    /**
     Checks collision between an entity and other entities in the game.
     @param entity The entity for collision check
     @return The index of the collided entity in the pirates array, or -1 if no collision occurred
     */
    public int CheckCollisionEntity(Entity entity) {
        int idx = -1;
        for (int i = 0; i < gp.pirates.length; ++i) {
            if (gp.pirates[i] != null) {

                // Calculate where the solid part of the player is on the map.
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // Calculate where the solid part of the pirate is on the map.
                gp.pirates[i].solidArea.x += gp.pirates[i].worldX;
                gp.pirates[i].solidArea.y += gp.pirates[i].worldY;

                // Make a prediction for a collision depending on which way the player is moving.
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                }

                // If the solid area of the pirate intersects with the solid area of the player, the .intersects returns true
                if (gp.pirates[i].solidArea.intersects(entity.solidArea)) {
                    entity.collision = true;
                    idx = i;
                }

                // Return the initial parameters of the player and the pirate.
                entity.solidArea.x = 20;
                entity.solidArea.y = 24;

                gp.pirates[i].solidArea.x = 20;
                gp.pirates[i].solidArea.y = 24;
            }
        }
        return idx;
    }

    /**
     Checks collision between the given entity and the player.
     @return true/false Collision of entity with a player
     */
    public boolean CheckCollisionPlayer(Entity entity) {
        boolean fight = false;
        if (gp.player != null) {

            // Calculate where the solid part of the entity is on the map.
            entity.solidArea.x += entity.worldX;
            entity.solidArea.y += entity.worldY;

            // Calculate where the solid part of the player is on the map.
            gp.player.solidArea.x += gp.player.worldX;
            gp.player.solidArea.y += gp.player.worldY;

            // Make a prediction for a collision depending on which way the entity is moving.
            switch (entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
            }

            // If the solid area of the player intersects with the solid area of the entity, the .intersects returns true
            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collision = true;
                fight = true;
            }

            // Return the initial parameters of the entity and the player.
            entity.solidArea.x = 20;
            entity.solidArea.y = 24;

            gp.player.solidArea.x = 20;
            gp.player.solidArea.y = 24;
        }
        return fight;
    }

    /**
     Checks collision between the given entity and the ship.
     @param entity The entity for collision check
     @return true/false Collision of the player with ship
     */
    public boolean CheckCollisionShip(Entity entity) {
        boolean ret = false;

        // Calculate where the solid part of the player is on the map.
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        // Calculate where the solid part of the ship is on the map.
        gp.ship.solidArea.x += gp.ship.worldX;
        gp.ship.solidArea.y += gp.ship.worldY;

        // Make a prediction for a collision depending on which way the player is moving.
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
        }

        // If the solid area of the ship intersects with the solid area of the player, the .intersects returns true
        if (gp.ship.solidArea.intersects(entity.solidArea))
            ret = true;

        // Return the initial parameters of the entity and the player.
        entity.solidArea.x = 20;
        entity.solidArea.y = 24;

        gp.ship.solidArea.x = 100;
        gp.ship.solidArea.y = 0;

        if (ret)
            LOGGER.info("You won the game. Congratulations. Herbert will now be able to fly away on his ship.");

        return ret;
    }

    /**
     Checks collision between the given entity and the house.
     @param entity The entity for collision check
     */
    public void CheckCollisionHouse(Entity entity) {
        // Calculate where the solid part of the player is on the map.
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        // Calculate where the solid part of the house is on the map.
        gp.house.solidArea.x += gp.house.worldX;
        gp.house.solidArea.y += gp.house.worldY;

        // Make a prediction for a collision depending on which way the player is moving.
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
        }

        // If the solid area of the house intersects with the solid area of the player, the .intersects returns true
        if (gp.house.solidArea.intersects(entity.solidArea))
            entity.collision = true;

        // Return the initial parameters of the entity and the player.
        entity.solidArea.x = 20;
        entity.solidArea.y = 24;

        gp.house.solidArea.x = 80;
        gp.house.solidArea.y = 0;
    }

    /**
     Checks collision between the given entity and the boss.
     @param entity The entity for collision check
     @return true/false Collision of the player with Boss
     */
    public boolean CheckCollisionBoss(Entity entity) {
        boolean fight = false;
        if (gp.boss != null) {
            // Calculate where the solid part of the player is on the map.
            entity.solidArea.x += entity.worldX;
            entity.solidArea.y += entity.worldY;

            // Calculate where the solid part of the boss is on the map.
            gp.boss.solidArea.x += gp.boss.worldX;
            gp.boss.solidArea.y += gp.boss.worldY;

            // Make a prediction for a collision depending on which way the player is moving.
            switch (entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
            }

            // If the solid area of the boss intersects with the solid area of the player, the .intersects returns true
            if (gp.boss.solidArea.intersects(entity.solidArea)) {
                entity.collision = true;
                fight = true;
            }

            // Return the initial parameters of the entity and the player.
            entity.solidArea.x = 20;
            entity.solidArea.y = 24;

            gp.boss.solidArea.x = 60;
            gp.boss.solidArea.y = 48;
        }
        return fight;
    }
}
