package play.mickedplay.ctf.map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.utilities.JSONReader;

import java.io.File;

/**
 * Created by mickedplay on 25.04.2016 at 17:24 UTC+1.
 * You are not allowed to remove this comment.
 */
public class LobbyManager {

    private CaptureTheFlag ctf;
    private String worldName;
    private World world;
    private Location lobbySpawn, decisionSpawn;

    public LobbyManager(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.worldName = "Lobby";
        this.initializeWorld();
    }

    private void initializeWorld() {
        this.world = Bukkit.getWorld(this.worldName);
        if (this.world != null) {
            this.world.getEntities().forEach(Entity::remove);
            this.world.setThundering(false);
            this.world.setStorm(false);
            this.world.setTime(6000L);
            this.world.setGameRuleValue("doDaylightCycle", "false");

            String serverPath = Bukkit.getServer().getWorldContainer().getAbsolutePath();
            JSONReader jsonReader = new JSONReader(new File(serverPath.substring(0, serverPath.length() - 1) + this.worldName, "info.json"));
            Location[] savedSpawns = jsonReader.getLocationsFromJson(this.worldName, "spawns");
            this.lobbySpawn = savedSpawns[0];
            this.decisionSpawn = savedSpawns[1];

        } else {
            Bukkit.getLogger().severe("==========================");
            Bukkit.getLogger().severe("NO LOBBY DETECTED!");
            Bukkit.getLogger().severe("==========================");
            Bukkit.getPluginManager().disablePlugin(this.ctf.getPlugin());
        }
    }

    public World getWorld() {
        return world;
    }

    public Location getSpawn() {
        return lobbySpawn;
    }

    public Location getDecisionSpawn() {
        return decisionSpawn;
    }
}
