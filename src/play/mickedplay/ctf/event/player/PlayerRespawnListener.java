package play.mickedplay.ctf.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.stages.Lobby;

/**
 * Created by mickedplay on 05.04.2016 at 23:09 CEST.
 * You are not allowed to remove this comment.
 */
public class PlayerRespawnListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerRespawnListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e) {
        if (this.ctf.getGameStage() instanceof Lobby) {
            e.setRespawnLocation(this.ctf.getLobbyManager().getSpawn());
        } else {
            CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
            ctfPlayer.prepareForGame();
            ctfPlayer.setDead(false);
            e.setRespawnLocation(ctfPlayer.getTeam().getSpawnLocation());
        }
    }
}