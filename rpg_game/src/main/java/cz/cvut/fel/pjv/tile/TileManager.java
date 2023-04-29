package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    GamePanel gp;
    public Tile[] tile; // array with tiles

    public int[][] mapTileNum;
    ArrayList<String> tile_names = new ArrayList<>();
    ArrayList<String> tile_collisions = new ArrayList<>();

    /**
     * A constructor that initializes the tile array and loads the map.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
//        getMapSize("/maps/map_last.txt");
        mapTileNum = new int[gp.worldCol][gp.worldRow]; // A 2d array storing tile numbers on the map
        loadData("/maps/tile_data_last.txt");
        tile = new Tile[tile_names.size()]; //Tile array
        getTileImage();
        loadMap("/maps/map_last.txt");
    }

    private void getMapSize(String map_path) {
        try {
            InputStream is = getClass().getResourceAsStream(map_path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            String[] line2 = line.split(" ");
            gp.worldCol = line2.length;
            gp.worldRow = line2.length;
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads tile images from a resource folder into the tile array.
     */
    private void getTileImage() {
        for (int i = 0; i < tile_names.size(); ++i) {
            try {
                tile[i] = new Tile();
//                tile[i].image = ImageIO.read(TileManager.class.getClassLoader().getResourceAsStream("tiles2/" + tile_names.get(i)));
                String st = "tiles2/" + tile_names.get(i);
                System.out.println(st);
                tile[i].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(st));

                tile[i].collision = Boolean.parseBoolean(tile_collisions.get(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Loads the map from a text file and stores it in the mapTileNum 2D array.
     */
    private void loadMap(String map_path) {
        try {
            InputStream is = getClass().getResourceAsStream(map_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.worldCol && row < gp.worldRow) {
                String line = br.readLine();

                while (col < gp.worldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    ++col;
                }
                if (col == gp.worldCol) {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData(String file_path) {
        try {
            InputStream is = getClass().getResourceAsStream(file_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            while (line  != null) {
                tile_names.add(line);
                tile_collisions.add(br.readLine());
                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws the tiles on the screen.
     */
    public void draw(Graphics2D g2) {
        int world_col = 0;
        int world_row = 0;

        while (world_col < gp.worldCol && world_row < gp.worldRow) {

            int tileNum = mapTileNum[world_col][world_row];

            int worldX = world_col * gp.tileSize; // position on the map
            int worldY = world_row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // where tile on the screen
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            ++world_col;

            if (world_col == gp.worldCol) {
                world_col = 0;
                ++world_row;
            }
        }
    }
}
