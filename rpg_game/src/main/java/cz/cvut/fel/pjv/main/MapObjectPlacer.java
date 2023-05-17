package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.enemy.Pirate;
import cz.cvut.fel.pjv.object.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class MapObjectPlacer {
    GamePanel gp;

    public MapObjectPlacer(GamePanel gp) {
        this.gp = gp;
    }

    public void PlaceObject(String filename) { // todo test
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filename));

            JSONArray jArray = (JSONArray) jsonObject.get("Objects");
            for (int i = 0; i < jArray.size(); ++i) {

                JSONObject obj = (JSONObject) jArray.get(i);
                JSONArray jsonArray = (JSONArray) obj.get("coordinates");
                String name = (String) obj.get("Name");

                if (name.equals("Key")) gp.objArray[i + 10] = new Key();
                if (name.equals("Heart")) gp.objArray[i + 10] = new Heart();
                if (name.equals("Rum")) gp.objArray[i + 10] = new Rum();
                if (name.equals("Sword")) gp.objArray[i + 10] = new Sword();
                if (name.equals("BlackKey")) gp.objArray[i + 10] = new BlackKey();
                if (name.equals("Map")) gp.objArray[i + 10] = new Map();

                gp.objArray[i + 10].worldX = (int) ((long) jsonArray.get(0)) * gp.tileSize;
                gp.objArray[i + 10].worldY = (int) ((long) jsonArray.get(1)) * gp.tileSize;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        // todo comment
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
    public void PlaceGoldKey(int x, int y) {
        gp.objArray[8] = new Key();
        gp.objArray[8].worldX = x * gp.tileSize;
        gp.objArray[8].worldY = y * gp.tileSize;
    }

    public void PlaceFirstKey() {
        gp.objArray[9] = new BlackKey();
        gp.objArray[9].worldX = 14 * gp.tileSize;
        gp.objArray[9].worldY = 92 * gp.tileSize;
    }


    public void PlacePirate(String filename) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filename));

            JSONArray jArray = (JSONArray) jsonObject.get("Pirates");

            for (int i = 0; i < jArray.size(); ++i) {

                JSONObject obj = (JSONObject) jArray.get(i);
                JSONArray jsonArray = (JSONArray) obj.get("coordinates");

                gp.pirates[i] = new Pirate(gp);

                gp.pirates[i].heart_count = (int) ((long) obj.get("health"));
                gp.pirates[i].worldX = (int) ((long) jsonArray.get(0)) * gp.tileSize;
                gp.pirates[i].worldY = (int) ((long) jsonArray.get(1)) * gp.tileSize;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
