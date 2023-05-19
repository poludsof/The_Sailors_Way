package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents a House object in the game.
 */
public class House{
    public int worldX, worldY;
    BufferedImage image;
    public Rectangle solidArea;
    public House(GamePanel gp) {
        worldX = 50 * gp.tileSize;
        worldY = 4 * gp.tileSize;
        solidArea = new Rectangle(gp.tileSize, 0, 4*gp.tileSize, 5*gp.tileSize);
        try {
            image = ImageIO.read(Objects.requireNonNull(House.class.getClassLoader().getResourceAsStream("objects/house.png")));
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // where obj on the screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, 5*gp.tileSize, 6*gp.tileSize, null);
    }
}
