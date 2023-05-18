package cz.cvut.fel.pjv.main;

import java.awt.*;

/**
 This class represents a ship in the game.
 */
public class Ship {
    public int worldX ;
    public int worldY;
    public Rectangle solidArea = new Rectangle(100, 0, 250, 600);

    /**
     The constructor sets the coordinates of the ship's location.
     */
    public Ship(GamePanel gp) {
        worldX = 88 * gp.tileSize;
        worldY = 2 * gp.tileSize;
    }

    /**
     The method takes in a Graphics2D object and a GamePanel object, and draws the ship on the screen.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        // Position of the ship on the screen relative to the player.
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // If the game is not over, it draws the ship with a shadow and an arrow for the player to enter the spaceship.
        if (gp.state != State.HAPPY_END) {
            g2.drawImage(ImageManager.image_shadow, screenX, screenY, 5 * gp.tileSize, 9 * gp.tileSize, null);
            g2.drawImage(ImageManager.enter_arrow, screenX, screenY + 300, gp.tileSize, gp.tileSize, null);
        } else  // it draws the ship with flames coming out of the back.
            g2.drawImage(ImageManager.image_end, screenX, screenY - 200, 5 * gp.tileSize, 10 * gp.tileSize, null);
    }

}
