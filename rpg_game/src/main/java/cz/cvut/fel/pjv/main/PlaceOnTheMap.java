package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.monster.Pirate;
import cz.cvut.fel.pjv.object.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class PlaceOnTheMap {
    GamePanel gp;

    public PlaceOnTheMap(GamePanel gp) {
        this.gp = gp;
    }

    public void PlaceObject() {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("object_data.json"));

            JSONArray jArray = (JSONArray) jsonObject.get("Array");
            System.out.println(jArray.size());

            for (int i = 0; i < jArray.size(); ++i) {

                JSONObject obj = (JSONObject) jArray.get(i);
                JSONArray jsonArray = (JSONArray) obj.get("coordinates");

                String name = (String) obj.get("Name");

                if (name.equals("Key")) gp.obj_arr[i + 10] = new Key();
                if (name.equals("Heart")) gp.obj_arr[i + 10] = new Heart();
                if (name.equals("Rum")) gp.obj_arr[i + 10] = new Rum();
                if (name.equals("Sword")) gp.obj_arr[i + 10] = new Sword();
                if (name.equals("BlackKey")) gp.obj_arr[i + 10] = new BlackKey();
                if (name.equals("Map")) gp.obj_arr[i + 10] = new Map();

                gp.obj_arr[i + 10].worldX = (int) ((long) jsonArray.get(0)) * gp.tileSize;
                gp.obj_arr[i + 10].worldY = (int) ((long) jsonArray.get(1)) * gp.tileSize;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        gp.obj_arr[0] = new RDoor();
        gp.obj_arr[0].worldX = 10 * gp.tileSize;
        gp.obj_arr[0].worldY = 88 * gp.tileSize;

        gp.obj_arr[1] = new LDoor();
        gp.obj_arr[1].worldX = 9 * gp.tileSize;
        gp.obj_arr[1].worldY = 88 * gp.tileSize;

        gp.obj_arr[2] = new RotatedLDoor();
        gp.obj_arr[2].worldX = 78 * gp.tileSize;
        gp.obj_arr[2].worldY = 43 * gp.tileSize;

        gp.obj_arr[3] = new RotatedRDoor();
        gp.obj_arr[3].worldX = 78 * gp.tileSize;
        gp.obj_arr[3].worldY = 44 * gp.tileSize;

        gp.obj_arr[4] = new HellRDoor();
        gp.obj_arr[4].worldX = 90 * gp.tileSize;
        gp.obj_arr[4].worldY = 33 * gp.tileSize;

        gp.obj_arr[5] = new HellLDoor();
        gp.obj_arr[5].worldX = 89 * gp.tileSize;
        gp.obj_arr[5].worldY = 33 * gp.tileSize;

        gp.obj_arr[6] = new BushLine();
        gp.obj_arr[6].worldX = 41 * gp.tileSize;
        gp.obj_arr[6].worldY = 54 * gp.tileSize;

        gp.obj_arr[7] = new BushLine();
        gp.obj_arr[7].worldX = 42 * gp.tileSize;
        gp.obj_arr[7].worldY = 53 * gp.tileSize;
    }
    public void PlaceGoldKey(int x, int y) {
        gp.obj_arr[8] = new Key();
        gp.obj_arr[8].worldX = x * gp.tileSize;
        gp.obj_arr[8].worldY = y * gp.tileSize;
    }

    public void PlaceFirstKey() {
        gp.obj_arr[9] = new BlackKey();
        gp.obj_arr[9].worldX = 14 * gp.tileSize;
        gp.obj_arr[9].worldY = 92 * gp.tileSize;
    }


    public void PlaceMonster() {
        gp.pirates[0] = new Pirate(gp);
        gp.pirates[0].worldX = 14 * gp.tileSize;
        gp.pirates[0].worldY = 93 * gp.tileSize;

        gp.pirates[1] = new Pirate(gp);
        gp.pirates[1].worldX = 5 * gp.tileSize;
        gp.pirates[1].worldY = 93 * gp.tileSize;

        gp.pirates[2] = new Pirate(gp);
        gp.pirates[2].worldX = 10 * gp.tileSize;
        gp.pirates[2].worldY = 89 * gp.tileSize;

        gp.pirates[3] = new Pirate(gp);
        gp.pirates[3].worldX = 3 * gp.tileSize;
        gp.pirates[3].worldY = 54 * gp.tileSize;

        gp.pirates[4] = new Pirate(gp);
        gp.pirates[4].worldX = 8 * gp.tileSize;
        gp.pirates[4].worldY = 49 * gp.tileSize;

        gp.pirates[5] = new Pirate(gp);
        gp.pirates[5].worldX = 16 * gp.tileSize;
        gp.pirates[5].worldY = 56 * gp.tileSize;

        gp.pirates[6] = new Pirate(gp);
        gp.pirates[6].worldX = 10 * gp.tileSize;
        gp.pirates[6].worldY = 40 * gp.tileSize;

        gp.pirates[7] = new Pirate(gp);
        gp.pirates[7].worldX = 28 * gp.tileSize;
        gp.pirates[7].worldY = 44 * gp.tileSize;

        gp.pirates[8] = new Pirate(gp);
        gp.pirates[8].worldX = 28 * gp.tileSize;
        gp.pirates[8].worldY = 58 * gp.tileSize;

        gp.pirates[9] = new Pirate(gp);
        gp.pirates[9].worldX = 33 * gp.tileSize;
        gp.pirates[9].worldY = 55 * gp.tileSize;

        gp.pirates[10] = new Pirate(gp);
        gp.pirates[10].worldX = 22 * gp.tileSize;
        gp.pirates[10].worldY = 39 * gp.tileSize;

    }
}
