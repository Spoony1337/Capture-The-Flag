package play.mickedplay.ctf.event.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.DecisionPhase;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.stages.Lobby;

/**
 * Created by mickedplay on 25.04.2016 at 14:20 UTC+1.
 * You are not allowed to remove this comment.
 */
public class AsyncPlayerChatListener extends GameEvent {

    private CaptureTheFlag ctf;
    private String separator = " \u00BB ";

    public AsyncPlayerChatListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent e) {
        if (this.ctf.getGameStage() instanceof Lobby) {
            e.setFormat("§7" + e.getPlayer().getName() + this.separator + "§r" + e.getMessage());
        } else if (this.ctf.getGameStage() instanceof DecisionPhase) {
            e.setCancelled(true);
            if (this.ctf.getCTFPlayer(e.getPlayer()).isSpectator()) {
                this.ctf.getCTFPlayers().values().forEach(ctfPlayer -> {
                    if (ctfPlayer.isSpectator()) {
                        ctfPlayer.sendMessage("§7" + e.getPlayer().getName() + this.separator + "§r" + e.getMessage());
                    }
                });
            } else {
                CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§7" + e.getPlayer().getName() + this.separator + "§r" + e.getMessage()));
            }
        } else {
            e.setCancelled(true);
            if (this.ctf.getCTFPlayer(e.getPlayer()).isSpectator()) {
                this.ctf.getCTFPlayers().values().forEach(ctfPlayer -> {
                    if (ctfPlayer.isSpectator()) {
                        ctfPlayer.sendMessage("§7" + e.getPlayer().getName() + this.separator + "§r" + e.getMessage());
                    }
                });
            } else {
                CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ctfPlayer.getTeam().getChatColor() + (ctfPlayer.hasEnemyFlag() ? "\u2691 \u2758 " : "") + ctfPlayer.getName() + this.separator + "§r" + e.getMessage()));
            }
        }
    }
}