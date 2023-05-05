package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.Key;
import cz.cvut.fel.pjv.object.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Boat{
    public BufferedImage image;
    public String name_object;
    public boolean collision_obj = false;
    public int worldX, worldY;
    //public Rectangle solidArea = new Rectangle(0, 0, 80, 80);
    public Boat() {
        name_object = "Boat";
        try {
            image = ImageIO.read(Key.class.getClassLoader().getResourceAsStream("objects/starship_no_shadow.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // where obj on the screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //if (worldX + gp.tileSize > gp.player.worldX - gp.player.screen && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, 5*gp.tileSize, 9*gp.tileSize, null);
    }

}
