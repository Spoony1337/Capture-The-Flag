package play.mickedplay.ctf.player.gameclass.task;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.misc.Helper;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.gameapi.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 06.07.2016 at 19:15 UTC+1.
 * You are not allowed to remove this comment.
 */
public class WizardWandEffect extends BukkitRunnable {

    private CaptureTheFlag ctf;
    private CTFPlayer ctfPlayer;

    private ArrayList<Location> locationList;
    private int length = 15;

    public WizardWandEffect(CaptureTheFlag ctf, CTFPlayer ctfPlayer) {
        this.ctf = ctf;
        this.ctfPlayer = ctfPlayer;
        this.locationList = Helper.getLine(ctfPlayer.getEyeLocation(), length);
        this.runTaskAsynchronously(ctf.getPlugin());
    }

    @Override
    public void run() {
        boolean cancelTask = false;
        this.ctfPlayer.playSound(Sound.ORB_PICKUP, 1F, Utilities.randomFloat(0.1F, 0.9F));

        for (int i = 0; i < this.locationList.size(); i++) {

            ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), this.locationList.get(i), this.ctfPlayer.getTeam().getData().getColor(), 50D, 0F, 0F, 0F, 1F, 1);

            if (cancelTask) break;
            if (this.locationList.get(i).getBlock().getType() != Material.AIR) break;
            List<Player> nearbyPlayers = Helper.getNearbyPlayers(this.locationList.get(i), 0.5);
            for (Player player : nearbyPlayers) {
                if (player != null) {
                    CTFPlayer target = this.ctf.getCTFPlayer(player);
                    if (target.getTeam() != this.ctfPlayer.getTeam() && !target.isNearSpawn() && !ctfPlayer.isNearSpawn()) {
                        if (target.isDead()) continue;
                        target.getPlayer().damage(3);
                        target.setVelocity(ctfPlayer.getLocation().getDirection().multiply(0.15));
                        if (target.getHealth() <= 0) {
                            this.ctfPlayer.getTeam().addExperience(this.ctfPlayer, LevelType.PLAYER_KILL);
                            this.ctfPlayer.getGameStats().addKill();
                        }
                        cancelTask = true;
                    }
                }
            }
        }
    }
}