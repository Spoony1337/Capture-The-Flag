package play.mickedplay.ctf.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import play.mickedplay.gameapi.utilities.game.Cardinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 25.04.2016 at 16:59 UTC+1.
 * You are not allowed to remove this comment.
 */
public class Helper {

    public static final String serverDir = Bukkit.getServer().getWorldContainer().getAbsolutePath();

    public static Location[] getLocationsFromJson(JSONArray jsonArray, String worldName) {
        Location[] locations = new Location[jsonArray.size()];
        for (int i = 0; i < locations.length; ++i) {
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

    public static ArrayList<Location> getCircle(Cardinal cardinal, Location center, double radius, int amountOfLocations) {
        ArrayList<Location> locations = new ArrayList<>();
        double increment = (2 * Math.PI) / amountOfLocations;
        double x, y, z;
        for (int i = 0; i < amountOfLocations; i++) {
            switch (cardinal) {
                case UP:
                case DOWN:
                    x = center.getX() + (radius * Math.cos(i * increment));
                    y = center.getY();
                    z = center.getZ() + (radius * Math.sin(i * increment));
                    break;
                case NORTH:
                case SOUTH:
                    x = center.getX() + (radius * Math.cos(i * increment));
                    y = center.getY() + (radius * Math.sin(i * increment));
                    z = center.getZ();
                    break;
                case EAST:
                case WEST:
                    x = center.getX();
                    y = center.getY() + (radius * Math.cos(i * increment));
                    z = center.getZ() + (radius * Math.sin(i * increment));
                    break;
                default:
                    x = center.getX();
                    y = center.getY();
                    z = center.getZ();
            }
            locations.add(new Location(center.getWorld(), x, y, z));
        }
        return locations;
    }

    public static ArrayList<Location> getLine(Location start, int length) {
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Location loc = start.add(start.getDirection());
            locations.add(new Location(start.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
        }
        return locations;
    }

    public static List<Player> getNearbyPlayers(Location location, double range) {
        List<Player> found = new ArrayList<>();
        location.getWorld().getEntities().stream().filter(entity -> entity instanceof Player).forEach(entity -> {
            Player player = (Player) entity;

            int x = location.getBlockX(), z = location.getBlockZ();
            int x1 = player.getLocation().getBlockX(), z1 = player.getLocation().getBlockZ();
            if (!(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range))) {
                found.add(player);
            }
        });
        return found;
    }
}