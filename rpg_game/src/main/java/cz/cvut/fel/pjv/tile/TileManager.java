package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile; // array with tiles

    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWordCol][gp.maxWordRow];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {
        try {
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png"));

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/wall.png"));
            tile[0].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tree+.png"));
            tile[4].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap(String map_path) {
        try {
            InputStream is = getClass().getResourceAsStream(map_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxWordCol && row < gp.maxWordRow) {
                String line = br.readLine();

                while (col < gp.maxWordCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    ++col;
                }
                if (col == gp.maxWordCol) {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D g2) {
        int world_col = 0;
        int world_row = 0;

        while (world_col < gp.maxWordCol && world_row < gp.maxWordRow) {

            int tileNum = mapTileNum[world_col][world_row];

            int worldX = world_col * gp.tileSize; // position on the map
            int worldY = world_row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // where tile on the screen
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + 3*gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - 3*gp.tileSize < gp.player.worldX + gp.player.screenY
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            ++world_col;

            if (world_col == gp.maxWordCol) {
                world_col = 0;
                ++world_row;
            }
        }
    }
}
