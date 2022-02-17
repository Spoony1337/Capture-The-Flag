package play.mickedplay.ctf.team.task;

import org.bukkit.Effect;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.team.Team;

/**
 * Created by mickedplay on 28.04.2016 at 18:25 UTC+1.
 * You are not allowed to remove this comment.
 */
public class ArrowEffectTask extends BukkitRunnable {

    private Projectile projectile;
    private Team team;

    public ArrowEffectTask(Projectile projectile, Team team) {
        this.projectile = projectile;
        this.team = team;
        this.runTaskTimerAsynchronously(team.getTeamManager().getCaptureTheFlag().getPlugin(), 0, 1L);
    }

    @Override
    public void run() {
        if (!(this.projectile.isDead() || this.projectile.isOnGround())) {
            this.team.getPlayers().forEach(ctfPlayer -> ctfPlayer.getPlayer().spigot().playEffect(this.projectile.getLocation(), Effect.TILE_BREAK, 35, this.team.getFlagBlockColor(), 0, 0, 0, 0, 1, 100));
            return;
        }
        this.cancel();
    }
}