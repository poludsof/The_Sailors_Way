package cz.cvut.fel.pjv.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 This class represents a ship in the game.
 */
public class Ship {
    public BufferedImage image, image_end, enter_arrow, image_shadow;
    public int worldX ;
    public int worldY;
    public Rectangle solidArea = new Rectangle(100, 0, 250, 600);

    /**
     * The constructor loads the necessary images and sets the coordinates of the ship's location.
     */
    public Ship(GamePanel gp) {
        worldX = 88 * gp.tileSize;
        worldY = 2 * gp.tileSize;
        try {
            image = ImageIO.read(Objects.requireNonNull(Ship.class.getClassLoader().getResourceAsStream("objects/starship_no_shadow.png")));
            image_shadow = ImageIO.read(Objects.requireNonNull(Ship.class.getClassLoader().getResourceAsStream("objects/starship_with_shadow.png")));
            image_end = ImageIO.read(Objects.requireNonNull(Ship.class.getClassLoader().getResourceAsStream("objects/starship_with_fire.png")));
            enter_arrow = ImageIO.read(Objects.requireNonNull(Ship.class.getClassLoader().getResourceAsStream("objects/arrow_boat.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method takes in a Graphics2D object and a GamePanel object, and draws the ship on the screen.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // where obj on the screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // If the game is not over, it draws the ship with a shadow and an arrow for the player to enter the spaceship.
        if (gp.state != GamePanel.State.HAPPY_END) {
            g2.drawImage(image_shadow, screenX, screenY, 5 * gp.tileSize, 9 * gp.tileSize, null);
            g2.drawImage(enter_arrow, screenX, screenY + 300, gp.tileSize, gp.tileSize, null);
        } else  // it draws the ship with flames coming out of the back.
            g2.drawImage(image_end, screenX, screenY - 200, 5 * gp.tileSize, 10 * gp.tileSize, null);
    }

}
