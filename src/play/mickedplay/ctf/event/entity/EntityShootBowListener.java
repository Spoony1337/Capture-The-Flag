package play.mickedplay.ctf.event.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.gameclass.task.ArcherClassTask;
import play.mickedplay.ctf.team.task.ArrowEffectTask;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 02.05.2016 at 18:05 UTC+1.
 * You are not allowed to remove this comment.
 */
public class EntityShootBowListener extends GameEvent {

    private CaptureTheFlag ctf;

    public EntityShootBowListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void entityShootBow(EntityShootBowEvent e) {
        if (this.ctf.getGameStage() instanceof Ingame) {
            if (e.getEntity() instanceof Player) {
                CTFPlayer ctfPlayer = this.ctf.getCTFPlayer((Player) e.getEntity());
                if (ctfPlayer.getArcherArrows() <= GameSettings.MAX_ARCHER_ARROWS) {
                    ctfPlayer.setArcherArrows(ctfPlayer.getArcherArrows() - 1);
                    if (ctfPlayer.getArcherClassTask() == null) ctfPlayer.setArcherClassTask(new ArcherClassTask(ctfPlayer));
                }
                new ArrowEffectTask((Projectile) e.getProjectile(), ctfPlayer.getTeam());
            }
        }
    }
}
