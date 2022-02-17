package play.mickedplay.gameapi.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

    private JSONObject jsonObject;

    public JSONReader(File jsonFile) {
        try {
            this.jsonObject = (JSONObject) new JSONParser().parse(new FileReader(jsonFile));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String readJSONData(String data) {
        return (String) jsonObject.get(data);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public JSONArray readJSONArray(String data) {
        return (JSONArray) jsonObject.get(data);
    }

    public Location[] getLocationsFromJson(String worldName, String key) {
        JSONArray jsonArray = this.readJSONArray(key);
        Location[] locations = new Location[jsonArray.size()];
        for (int i = 0; i < locations.length; i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            double x = (double) jsonObject.get("x");
            double y = (double) jsonObject.get("y");
            double z = (double) jsonObject.get("z");
            double yaw = 0.0, pitch = 0.0;
            if (jsonObject.get("yaw") != null) yaw = (double) jsonObject.get("yaw");
            if (jsonObject.get("pitch") != null) pitch = (double) jsonObject.get("pitch");
            locations[i] = new Location(Bukkit.getWorld(worldName), x, y, z, (float) yaw, (float) pitch);
        }
        return locations;
    }
}