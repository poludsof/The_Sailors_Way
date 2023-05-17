package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.RDoor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Objects;

public class Inventory {
    static int count_row = 0;
    static int count_col = 0;
    static int itemX;
    static int itemY;
    public static void drawInventory(Graphics2D g2, GamePanel gp) {
        count_row = 0;
        count_col = 0;

        Rectangle inventoryList = new Rectangle(380, 250, gp.tileSize * 4 + 60, gp.tileSize * 3 + 50);

        BufferedImage key, map, rum, sword;
        try {
            key = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/key.png")));
            map = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/map.png")));
            rum = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/rum.png")));
            sword = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/sword.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setColor(new Color(154, 229, 255, 80));
        g2.fillRect((int) inventoryList.getX(),
                (int) inventoryList.getY(),
                (int) inventoryList.getWidth(),
                (int) inventoryList.getHeight());

        g2.draw(inventoryList);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(20));
        g2.drawRoundRect( (int) inventoryList.getX() - 10,  (int) inventoryList.getY() - 10, (int) inventoryList.getWidth() + 20, (int) inventoryList.getHeight() + 20, 20, 20);


        itemX = (int) inventoryList.getX() + 15;
        itemY = (int) inventoryList.getY() + 15;

        for (int i = 0; i < gp.player.inventory.get("Key"); ++i) {
            if (count_row < 3) {
                g2.drawImage(key, itemX, itemY, gp.tileSize, gp.tileSize, null);
                drawRectangle(g2, gp);
            }
            checkCounter(gp, inventoryList);

        }

        for (int i = 0; i < gp.player.inventory.get("Map"); ++i) {
            if (count_row < 3) {
                g2.drawImage(map, itemX, itemY, gp.tileSize, gp.tileSize, null);
                drawRectangle(g2, gp);
            }
            checkCounter(gp, inventoryList);

        }

        for (int i = 0; i < gp.player.inventory.get("Sword"); ++i) {
            if (count_row < 3) {
                g2.drawImage(sword, itemX, itemY, gp.tileSize, gp.tileSize, null);
                drawRectangle(g2, gp);
            }
            checkCounter(gp, inventoryList);

        }

        for (int i = 0; i < gp.player.inventory.get("Rum"); ++i) {
            if (count_row < 3) {
                g2.drawImage(rum, itemX, itemY, gp.tileSize, gp.tileSize, null);
                drawRectangle(g2, gp);
            }
            checkCounter(gp, inventoryList);
        }
    }

    public static void checkCounter(GamePanel gp, Rectangle inventoryList) {
        itemX += gp.tileSize + 10;
        ++count_col;
        if (count_col >= 4) {
            itemX = (int) inventoryList.getX() + 15;
            itemY += gp.tileSize + 10;
            ++count_row;
            count_col = 0;
        }
    }

    private static void drawRectangle(Graphics2D g2, GamePanel gp) {
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(itemX, itemY, gp.tileSize, gp.tileSize, 0, 0);
    }
}
