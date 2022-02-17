package play.mickedplay.ctf.shop.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.player.CTFPlayer;

/**
 * Created by mickedplay on 23.06.2016 at 13:25.
 * You are not allowed to remove this comment.
 */
public class TeamCircleHealer extends BukkitRunnable {

    private CaptureTheFlag ctf;
    private CTFPlayer ctfPlayer;

    private Location healCenter;
    private int healTime = 20;
    private double healthToAdd = 2.0;

    private TeamCircleHealerEffect teamCircleHealerEffect;

    public TeamCircleHealer(CaptureTheFlag ctf, CTFPlayer ctfPlayer, Location healCenter) {
        this.ctf = ctf;
        this.ctfPlayer = ctfPlayer;
        this.healCenter = healCenter;
        this.teamCircleHealerEffect = new TeamCircleHealerEffect(this);
        this.runTaskTimerAsynchronously(ctf.getPlugin(), 0, 20L);
    }

    @Override
    public void run() {
        this.healTime--;
        if (this.healTime <= 0) {
            Bukkit.getScheduler().runTaskLater(this.ctf.getPlugin(), () -> healCenter.getBlock().setType(Material.AIR), 0L);
            this.teamCircleHealerEffect.cancel();
            this.cancel();
            return;
        }
        this.ctfPlayer.getTeam().getPlayers().forEach(teamPlayer -> {
            if (teamPlayer.getHealth() + this.healthToAdd <= teamPlayer.getMaxHealth()) {
                if (teamPlayer.distance(healCenter) <= GameSettings.CAKE_HEAL_RADIUS && !teamPlayer.isDead()) {
                    teamPlayer.setHealth(teamPlayer.getHealth() + this.healthToAdd);
                }
            }
        });
    }

    public Location getHealCenter() {
        return healCenter;
    }

    public CaptureTheFlag getCTF() {
        return this.ctf;
    }
}