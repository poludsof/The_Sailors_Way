package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.RDoor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Dictionary;

public class Inventory {

    int count_row = 0;
    int count_col = 0;
    int itemX;
    int itemY;
    public void drawInventory(Graphics2D g2, GamePanel gp) {
        count_row = 0;
        count_col = 0;

        Rectangle inventoryList = new Rectangle(380, 250, gp.tileSize * 4 + 60, gp.tileSize * 3 + 50);

        BufferedImage key, map, rum, sword;
        try {
            key = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/key.png"));
            map = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/map.png"));
            rum = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/rum.png"));
            sword = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/sword.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect((int) inventoryList.getX(),
                (int) inventoryList.getY(),
                (int) inventoryList.getWidth(),
                (int) inventoryList.getHeight());

        g2.draw(inventoryList);

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

    private void checkCounter(GamePanel gp, Rectangle inventoryList) {
        itemX += gp.tileSize + 10;
        ++count_col;
        if (count_col >= 4) {
            itemX = (int) inventoryList.getX() + 15;
            itemY += gp.tileSize + 10;
            ++count_row;
            count_col = 0;
        }
    }

    private void drawRectangle(Graphics2D g2, GamePanel gp) {
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(itemX, itemY, gp.tileSize, gp.tileSize, 0, 0);
    }
}
