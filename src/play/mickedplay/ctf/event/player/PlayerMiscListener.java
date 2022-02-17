package play.mickedplay.ctf.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.utilities.Utilities;

/**
 * Created by mickedplay on 25.04.2016 at 14:00 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerMiscListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerMiscListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void achievementAwarded(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void craftItem(CraftItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void entityRegainHealth(EntityRegainHealthEvent e) {
        if (this.ctf.getGameStage() instanceof Ingame) {
            if (e.getEntity() instanceof Player) {
                e.setCancelled(true);
                CTFPlayer ctfPlayer = this.ctf.getCTFPlayer((Player) e.getEntity());
                if (ctfPlayer.isNearSpawn()) {
                    double randomHealth = Utilities.randomInt(1, 5);
                    double currentHealth = ctfPlayer.getHealth();
                    ctfPlayer.getPlayer().setHealth(currentHealth + randomHealth > 20.0 ? 20.0 : currentHealth + randomHealth);
                }
            }
        }
    }
}