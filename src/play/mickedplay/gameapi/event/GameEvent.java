package play.mickedplay.gameapi.event;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Created by mickedplay on 17.08.2016 at 19:21 CEST.
 * You are not allowed to remove this comment.
 */
public class GameEvent implements Listener {

    private Plugin plugin;

    public GameEvent(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}