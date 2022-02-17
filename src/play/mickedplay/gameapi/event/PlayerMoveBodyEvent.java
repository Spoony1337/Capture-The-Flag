package play.mickedplay.gameapi.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by mickedplay on 25.06.2016 at 12:27 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerMoveBodyEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Location from;
    private Location to;

    public PlayerMoveBodyEvent(Player player, Location from, Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Location getFrom() {
        return this.from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return this.to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}