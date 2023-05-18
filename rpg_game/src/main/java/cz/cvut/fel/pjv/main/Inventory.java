package cz.cvut.fel.pjv.main;

import java.awt.*;

public class Inventory {
    static int countRow, countCol;
    static int itemX, itemY;
    static int maxCol = 4;

    /**
     The method draws each item that the player currently has in his inventory to the screen.
     */
    public static void drawInventory(Graphics2D g2, GamePanel gp) {
        countRow = 0;
        countCol = 0;

        // Create a rectangle representing the inventory area.
        Rectangle inventoryList = new Rectangle(380, 250, gp.tileSize * 4 + 60, gp.tileSize * 3 + 50);

        // Draw the background of the rectangle for the inventory.
        g2.setColor(new Color(154, 229, 255, 80));
        g2.fillRect((int) inventoryList.getX(), (int) inventoryList.getY(),
                    (int) inventoryList.getWidth(), (int) inventoryList.getHeight());

        g2.draw(inventoryList); //Draw rectangle.

        // Draw border for inventory rectangle.
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(20));
        g2.drawRoundRect( (int) inventoryList.getX() - 10,  (int) inventoryList.getY() - 10, (int) inventoryList.getWidth() + 20, (int) inventoryList.getHeight() + 20, 20, 20);

        // Set starting position for first item in inventory.
        itemX = (int) inventoryList.getX() + 15;
        itemY = (int) inventoryList.getY() + 15;

        // Iterate through inventory items and draw them on the screen.
        for (int i = 0; i < gp.player.inventory.get("Key"); ++i) {
            g2.drawImage(ImageManager.key, itemX, itemY, gp.tileSize, gp.tileSize, null);
            drawRectangle(g2, gp);
            checkBound(gp, inventoryList);
        }

        for (int i = 0; i < gp.player.inventory.get("Map"); ++i) {
            g2.drawImage(ImageManager.map, itemX, itemY, gp.tileSize, gp.tileSize, null);
            drawRectangle(g2, gp);
            checkBound(gp, inventoryList);
        }

        for (int i = 0; i < gp.player.inventory.get("Sword"); ++i) {
            g2.drawImage(ImageManager.sword, itemX, itemY, gp.tileSize, gp.tileSize, null);
            drawRectangle(g2, gp);
            checkBound(gp, inventoryList);
        }

        for (int i = 0; i < gp.player.inventory.get("Rum"); ++i) {
            g2.drawImage(ImageManager.rum, itemX, itemY, gp.tileSize, gp.tileSize, null);
            drawRectangle(g2, gp);
            checkBound(gp, inventoryList);
        }
    }

    /**
     Move the object coordinates depending on the inventory boundaries.
     */
    public static void checkBound(GamePanel gp, Rectangle inventoryList) {
        itemX += gp.tileSize + 10;  // Shift the position by the size of the item + the distance between items.
        ++countCol;
        if (countCol >= maxCol) { // If all places in the row for elements are filled, then the move to a new row.
            itemX = (int) inventoryList.getX() + 15;
            itemY += gp.tileSize + 10;
            ++countRow;
            countCol = 0;
        }
    }

    /**
     Draws a black frame around the object in inventory.
     */
    private static void drawRectangle(Graphics2D g2, GamePanel gp) {
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(itemX, itemY, gp.tileSize, gp.tileSize, 0, 0);
    }
}
