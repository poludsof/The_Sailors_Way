package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.enemy.Pirate;
import cz.cvut.fel.pjv.object.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 The MapObjectPlacer class reads JSON files containing game object coordinates and places the objects on the map.
 */
public class MapObjectPlacer {
    GamePanel gp;

    /**
     Constructor for MapObjectPlacer class. Initializes the GamePanel object.
     */
    public MapObjectPlacer(GamePanel gp) {
        this.gp = gp;
    }

    /**
     The method reads in a JSON file containing coordinates of game objects and places them on the map.
     It reads in the object name from the JSON file and creates a new object of that type to be placed on the map.
     @param filename String name of the JSON file containing the game objects coordinates.
     */
    public void PlaceObject(String filename) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filename));

            JSONArray jsonArray = (JSONArray) jsonObject.get("Objects");

            for (int i = 0; i < jsonArray.size(); ++i) {

                // Start filling the array with index 10, the first 10 cells are occupied by unchanged items.
                int idx = i + 10;

                JSONObject obj = (JSONObject) jsonArray.get(i);

                // Get the coordinates and name of the object.
                JSONArray coordArray = (JSONArray) obj.get("coordinates");
                String name = (String) obj.get("Name");

                // Create a new object based on its name.
                switch (name) {
                    case "Key" -> gp.objArray[idx] = new Key();
                    case "Heart" -> gp.objArray[idx] = new Heart();
                    case "Rum" -> gp.objArray[idx] = new Rum();
                    case "Sword" -> gp.objArray[idx] = new Sword();
                    case "BlackKey" -> gp.objArray[idx] = new BlackKey();
                    case "Map" -> gp.objArray[idx] = new Map();
                }

                // Set the object's coordinates.
                gp.objArray[idx].worldX = (int) ((long) coordArray.get(0)) * gp.tileSize;
                gp.objArray[idx].worldY = (int) ((long) coordArray.get(1)) * gp.tileSize;
            }
        } catch (ParseException | IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }

        // Place items on the map that cannot be changed by the user.
        gp.objArray[0] = new RDoor();
        gp.objArray[0].worldX = 10 * gp.tileSize;
        gp.objArray[0].worldY = 88 * gp.tileSize;

        gp.objArray[1] = new LDoor();
        gp.objArray[1].worldX = 9 * gp.tileSize;
        gp.objArray[1].worldY = 88 * gp.tileSize;

        gp.objArray[2] = new RotatedLDoor();
        gp.objArray[2].worldX = 78 * gp.tileSize;
        gp.objArray[2].worldY = 43 * gp.tileSize;

        gp.objArray[3] = new RotatedRDoor();
        gp.objArray[3].worldX = 78 * gp.tileSize;
        gp.objArray[3].worldY = 44 * gp.tileSize;

        gp.objArray[4] = new HellRDoor();
        gp.objArray[4].worldX = 90 * gp.tileSize;
        gp.objArray[4].worldY = 33 * gp.tileSize;

        gp.objArray[5] = new HellLDoor();
        gp.objArray[5].worldX = 89 * gp.tileSize;
        gp.objArray[5].worldY = 33 * gp.tileSize;

        gp.objArray[6] = new BushLine();
        gp.objArray[6].worldX = 41 * gp.tileSize;
        gp.objArray[6].worldY = 54 * gp.tileSize;

        gp.objArray[7] = new BushLine();
        gp.objArray[7].worldX = 42 * gp.tileSize;
        gp.objArray[7].worldY = 53 * gp.tileSize;
    }

    /**
     The method places a gold key on the specified tile coordinates on the game map.
     The key appears at the place where the boss died.
     */
    public void PlaceGoldKey(int x, int y) {
        gp.objArray[8] = new Key();
        gp.objArray[8].worldX = x * gp.tileSize;
        gp.objArray[8].worldY = y * gp.tileSize;
    }

    /**
     The method places an iron key on a predefined location on the game map.
     The key appears after all the pirates on the first level have died.
     */
    public void PlaceFirstKey() {
        gp.objArray[9] = new BlackKey();
        gp.objArray[9].worldX = 14 * gp.tileSize;
        gp.objArray[9].worldY = 92 * gp.tileSize;
    }

    /**
     The method reads in a JSON file containing coordinates of pirates and places them on the map.
     It also reads in the health of each pirate and assigns it to the corresponding pirate object.
     @param filename String name of the JSON file containing the pirates' coordinates and health.
     */
    public void PlacePirate(String filename) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filename));

            // Get the "Pirates" array from the JSON object.
            JSONArray jArray = (JSONArray) jsonObject.get("Pirates");

            for (int i = 0; i < jArray.size(); ++i) {  // Go through each pirate object in the array.

                JSONObject obj = (JSONObject) jArray.get(i);
                JSONArray jsonArray = (JSONArray) obj.get("coordinates");

                gp.pirates[i] = new Pirate(gp);  // Create a new Pirate object in the pirates array

                gp.pirates[i].heart_count = (int) ((long) obj.get("health")); // Set the pirate's health.
                gp.pirates[i].worldX = (int) ((long) jsonArray.get(0)) * gp.tileSize;  // Set the pirate's world coordinates
                gp.pirates[i].worldY = (int) ((long) jsonArray.get(1)) * gp.tileSize;
            }
        } catch (ParseException | IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
