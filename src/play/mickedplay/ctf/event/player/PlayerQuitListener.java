package play.mickedplay.ctf.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.stages.Lobby;

/**
 * Created by mickedplay on 25.04.2016 at 14:20 CEST.
 * You are not allowed to remove this comment.
 */
public class PlayerQuitListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerQuitListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        if (!(this.ctf.getGameStage() instanceof Lobby)) {
            CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
            if (this.ctf.getGameStage() instanceof Ingame) {
                if (!ctfPlayer.isSpectator()) {
                    ctfPlayer.checkForEnemyFlag();
                    ctfPlayer.cancelArcherClassTask();
                    ctfPlayer.getTeam().removePlayer(ctfPlayer);
                    this.ctf.checkForEnd();
                    e.setQuitMessage("§c\u00BB " + ctfPlayer.getDisplayName() + "§e left the game.");
                } else {
                    e.setQuitMessage(null);
                }
            } else {
                e.setQuitMessage(ctfPlayer.isSpectator() ? null : "§c\u00BB " + ctfPlayer.getDisplayName() + "§e left the game.");
            }
        }
        this.ctf.getCTFPlayer(e.getPlayer()).getGameStats().saveGameStatistics();
        this.ctf.removeCTFPlayer(e.getPlayer());
    }
}