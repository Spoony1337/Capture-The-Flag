package play.mickedplay.ctf.map;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.gameapi.utilities.JSONReader;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.io.File;

/**
 * Created by mickedplay on 19.06.2016 at 13:01 UTC+1.
 * You are not allowed to remove this comment.
 */
public class GameMap {

    private final String worldName;
    private final JSONReader jsonReader;
    private MapManager mapManager;
    private World world;
    private ItemStack voteItemStack;
    private int votes;

    private Location centerLocation;

    public GameMap(MapManager mapManager, String worldName, File file) {
        this.mapManager = mapManager;
        this.worldName = ChatColor.stripColor(worldName); /* stripColor(string) -> Security fix */
        this.jsonReader = new JSONReader(file);

        String[] itemData = jsonReader.readJSONData("material").split(":");
        this.voteItemStack = new ItemBuilder(Material.valueOf(itemData[0])).withName("§e" + worldName).withDamage(Short.valueOf(itemData[1])).withLore("§7Autor: §f" + jsonReader.readJSONData("author")).build();
        mapManager.addVoteItem(this);
    }

    public void load() {
        this.world = Bukkit.createWorld(new WorldCreator(worldName));
        this.world.setAutoSave(false);
        this.world.getEntities().forEach(Entity::remove);
        this.world.setGameRuleValue("showDeathMessages", "true");
        this.world.setThundering(false);
        this.world.setStorm(false);
        this.world.setWeatherDuration(Integer.MAX_VALUE);

        Location[] mapCenters = this.jsonReader.getLocationsFromJson(this.worldName, "center");
        this.centerLocation = mapCenters[0];
    }

    public World getWorld() {
        return world;
    }

    public String getWorldName() {
        return worldName;
    }

    public JSONReader getJSONReader() {
        return jsonReader;
    }

    public ItemStack getVoteItemStack() {
        return voteItemStack;
    }

    public void addVote() {
        this.votes++;
    }

    public void removeVote() {
        if (this.votes >= 1) this.votes--;
    }

    public int getVotes() {
        return votes;
    }

    public Location getCenterLocation() {
        return centerLocation;
    }
}