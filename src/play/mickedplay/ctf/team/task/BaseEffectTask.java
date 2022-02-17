package play.mickedplay.ctf.team.task;

import de.slikey.effectlib.effect.ShieldEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.team.Team;

/**
 * Created by mickedplay on 25.04.2016 at 18:47 UTC+1.
 * You are not allowed to remove this comment.
 */
public class BaseEffectTask extends BukkitRunnable {

    private CaptureTheFlag captureTheFlag;
    private Location location;

    private ShieldEffect shieldEffect;

    public BaseEffectTask(Team team) {
        this.captureTheFlag = team.getTeamManager().getCaptureTheFlag();
        this.location = team.getSpawnLocation().clone();
        this.runTaskTimerAsynchronously(captureTheFlag.getPlugin(), 0L, 500);
    }

    @Override
    public void run() {
        this.shieldEffect = new ShieldEffect(this.captureTheFlag.getEffectManager());
        this.shieldEffect.radius = GameSettings.TEAM_SPAWN_RADIUS;
        this.shieldEffect.particle = ParticleEffect.SPELL_WITCH;
        this.shieldEffect.particles = 40;
        this.shieldEffect.setDynamicOrigin(new DynamicLocation(this.location.add(0, -2, 0)));
        this.shieldEffect.setDynamicTarget(new DynamicLocation(this.location.add(0, 2, 0)));
        this.shieldEffect.start();
    }
}