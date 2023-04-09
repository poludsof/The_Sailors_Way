package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.screen_col][gp.screen_row];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png"));

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.screen_col && row < gp.screen_row) {
                String line = br.readLine();

                while (col < gp.screen_col) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    ++col;
                }
                if (col == gp.screen_col) {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.screen_col && row < gp.screen_row) {

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            ++col;
            x += gp.tileSize;

            if (col == gp.screen_col) {
                col = 0;
                x = 0;
                ++row;
                y += gp.tileSize;
            }
        }
    }
}
