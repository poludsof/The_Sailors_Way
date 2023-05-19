package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 The TileManager class handles the management and rendering of tiles in the game.
 It loads tile data from files, creates tile objects, and draws them on the screen.
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile; // Array with tiles, where each tile stores its name, image, and collision identifier.
    public int[][] mapTileNum;  // The array is a map of numbers, each number representing a specific tile.


    // Name and collision arrays to load tile information from files.
    ArrayList<String> tile_names = new ArrayList<>();
    ArrayList<String> tile_collisions = new ArrayList<>();


    // FILENAMES
    String tileFile = "/maps/tile_data_last.txt";
    String mapFile = "/maps/map_last.txt";

    /**
     TileManager object constructor, initializes the tile array and loads the map.
     @param gp instance GamePanel.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.worldCol][gp.worldRow];  // Create 2d array storing tile numbers on the map.
        loadData(tileFile);
        loadMap(mapFile);
        tile = new Tile[tile_names.size()];  // Create tile array.
        getTileImage();
    }

    /**
     Loads the map from a text file and stores it in the mapTileNum 2D array.
     @param map_path the path to the file with map
     */
    private void loadMap(String map_path) {
        try {
            InputStream is = getClass().getResourceAsStream(map_path);  // Open the file with map information.
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            // Read the map data line by line.
            while (col < gp.worldCol && row < gp.worldRow) {
                String line = br.readLine();

                // Parse the numbers in each line and store them in the mapTileNum array.
                while (col < gp.worldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    ++col;
                }

                // Reset the column index and move to the next row.
                if (col == gp.worldCol) {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Reads information about all tiles and loads it into an array with names and collision from a text file..
     @param tile_path the path to the file with tiles information
     */
    private void loadData(String tile_path) {
        try {
            InputStream is = getClass().getResourceAsStream(tile_path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            // Add the tile name and collision information to the lists from files.
            while (line  != null) {
                tile_names.add(line);
                tile_collisions.add(br.readLine());
                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Create tiles and load their images, name and determine if it is a solid object
     for the player from the array with all the tiles names.
     */
    private void getTileImage() {
        for (int i = 0; i < tile_names.size(); ++i) {
            try {
                tile[i] = new Tile();
                String obj = "tiles/" + tile_names.get(i);
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(obj)));
                tile[i].name = tile_names.get(i);
                tile[i].collision = Boolean.parseBoolean(tile_collisions.get(i));
            } catch (IOException e) {
                LOGGER.error("Error: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     Draws the tiles on the screen.
     @param g2 the Graphics2D object to draw the tiles
     */
    public void draw(Graphics2D g2) {
        for (int world_row = 0; world_row < gp.worldRow; ++world_row) {
            for (int world_col = 0; world_col < gp.worldCol; ++world_col) {
                // Get the tile number at the current position on the map.
                int tileNum = mapTileNum[world_col][world_row];

                int worldX = world_col * gp.tileSize;  // Position of the tile on the map.
                int worldY = world_row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;  // Position of the tile on the screen.
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Check if the tile is within the visible area of the player's screen.
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                        && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                        && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                        && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    // Draw the tile image.
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
