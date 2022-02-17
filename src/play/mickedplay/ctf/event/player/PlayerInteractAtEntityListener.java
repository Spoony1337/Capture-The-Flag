package play.mickedplay.ctf.event.player;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 06.07.2016 at 07:57 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerInteractAtEntityListener extends GameEvent {

    public PlayerInteractAtEntityListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
    }

    @EventHandler
    public void entityInteractAtEntity(PlayerInteractAtEntityEvent e) {
        e.setCancelled(true);
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            e.getPlayer().sendMessage("§cThis feature is still in progress.");
        }
    }
}