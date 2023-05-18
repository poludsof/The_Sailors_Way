package cz.cvut.fel.pjv.tile;

import java.awt.image.BufferedImage;

/**
 Represents a tile in the game.
 */
public class Tile {
    public BufferedImage image; // Tile image.
    public boolean collision = false; // Variable indicating if the player has collided with a tile.
    public String name;
}
