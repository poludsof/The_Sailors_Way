package cz.cvut.fel.pjv.main;
import cz.cvut.fel.pjv.object.BlackKey;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class TryJson {
    public static void main(String[] args) {
        JSONArray big_obj = new JSONArray();
        JSONObject bigger_obj = new JSONObject();

        for (int i = 0; i <= 2; ++i) {
            JSONObject obj = new JSONObject();
            JSONArray subjects = new JSONArray();
            subjects.add(i);
            subjects.add(i + 1);
            obj.put("Coordinates:", subjects);
            obj.put("ID", 1);
            big_obj.add(obj);
            bigger_obj.put("array", big_obj);
        }
        try (FileWriter file = new FileWriter("rpg_game/target/new_boss.json")) {
            file.write(bigger_obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        read();
    }

    public static void read() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("load_object_data.json"))  {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("load_object_data.json"));

            JSONArray jArray = (JSONArray) jsonObject.get("Array");
            System.out.println(jArray);

            for (int i = 0; i < jArray.size(); ++i) {
                JSONObject obj = (JSONObject) jArray.get(i);
                String name = (String) obj.get("Name");

                JSONArray jsonArray = (JSONArray) obj.get("coordinates");
                int a = (int) ((long) jsonArray.get(0));
                System.out.println(a);
                System.out.println(jsonArray.get(1));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
