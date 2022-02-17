package play.mickedplay.ctf.event.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.stages.Lobby;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 25.04.2016 at 13:53 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerJoinListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerJoinListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        CTFPlayer ctfPlayer = this.ctf.addCTFPlayer(e.getPlayer());
        if (this.ctf.getGameStage() instanceof Lobby) {
            ctfPlayer.setHeldItemSlot(4);
            ctfPlayer.setItem(0, new ItemBuilder(Material.COMPASS).withName("§fVoting").build());
            ctfPlayer.setItem(8, new ItemBuilder(Material.NETHER_STAR).withName("§fAchievements").build());
        } else {
            e.setJoinMessage(null);
            ctfPlayer.setAsSpectator();
        }
    }
}