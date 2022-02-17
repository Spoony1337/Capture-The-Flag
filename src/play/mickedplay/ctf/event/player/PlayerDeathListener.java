package play.mickedplay.ctf.event.player;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.utilities.player.PlayerUtilities;

/**
 * Created by mickedplay on 05.04.2016 at 23:09 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerDeathListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerDeathListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        e.getDrops().clear();
        if (this.ctf.getGameStage() instanceof Ingame) {
            CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getEntity());
            ctfPlayer.checkForEnemyFlag();
            ctfPlayer.cancelArcherClassTask();
            PlayerUtilities.respawn(this.ctf.getPlugin(), ctfPlayer.getPlayer(), 10);
            if (e.getEntity().getKiller() != null) {
                CTFPlayer killer = this.ctf.getCTFPlayer(e.getEntity().getKiller());
                killer.getTeam().addExperience(killer, LevelType.PLAYER_KILL);
                killer.getGameStats().addKill();
                e.setDeathMessage(ctfPlayer.getDisplayName() + " §ewas killed by " + killer.getDisplayName());
            } else if (e.getEntity().getKiller() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getEntity().getKiller();
                if (arrow.getShooter() instanceof Player) {
                    CTFPlayer killer = this.ctf.getCTFPlayer((Player) arrow.getShooter());
                    killer.getTeam().addExperience(killer, LevelType.PLAYER_KILL);
                    killer.getGameStats().addKill();
                    e.setDeathMessage(killer.getTeam().getChatColor() + killer.getName() + " §e-> " + ctfPlayer.getTeam().getChatColor() + ctfPlayer.getName());
                }
            } else {
                e.setDeathMessage(ctfPlayer.getTeam().getChatColor() + ctfPlayer.getName() + " §edied.");
            }
            ctfPlayer.getGameStats().addDeath();
            ctfPlayer.setDead(true);
            return;
        }
        e.setDeathMessage(null);
    }
}