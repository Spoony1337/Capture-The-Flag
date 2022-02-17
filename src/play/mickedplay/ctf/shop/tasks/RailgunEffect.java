package play.mickedplay.ctf.shop.tasks;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.misc.Helper;
import play.mickedplay.ctf.player.CTFPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 21.06.2016 at 19:59 UTC+1.
 * You are not allowed to remove this comment.
 */
public class RailgunEffect extends BukkitRunnable {

    private CaptureTheFlag ctf;
    private CTFPlayer ctfPlayer;

    private ArrayList<Location> locationList;
    private int length = 18;

    public RailgunEffect(CaptureTheFlag ctf, CTFPlayer ctfPlayer) {
        this.ctf = ctf;
        this.ctfPlayer = ctfPlayer;
        this.locationList = Helper.getLine(ctfPlayer.getEyeLocation(), length);
        this.runTaskAsynchronously(ctf.getPlugin());
    }

    @Override
    public void run() {
        boolean cancelTask = false;
        for (int i = 0; i < this.locationList.size(); i++) {
            this.ctfPlayer.getWorld().playEffect(this.locationList.get(i), Effect.VILLAGER_THUNDERCLOUD, 1);
            this.ctfPlayer.playSound(Sound.CREEPER_HISS);
            if (cancelTask) {
                break;
            }
            if (this.locationList.get(i).getBlock().getType() != Material.AIR) {
                // TODO: Break unsolid blocks like grass when colliding with trail
                break;
            }
            List<Player> nearbyPlayers = Helper.getNearbyPlayers(this.locationList.get(i), 0.5);
            for (Player player : nearbyPlayers) {
                CTFPlayer target = this.ctf.getCTFPlayer(player);
                if (target.getTeam() != this.ctfPlayer.getTeam() && target.distance(target.getTeam().getSpawnLocation()) > GameSettings.TEAM_SPAWN_RADIUS) {
                    if (target.isDead()) {
                        continue;
                    }
                    target.setHealth(0);
                    target.setDead(true);
                    cancelTask = true;
                }
            }
        }
        this.ctfPlayer.removeItemInMainHand();
    }
}