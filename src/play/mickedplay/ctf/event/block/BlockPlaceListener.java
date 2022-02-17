package play.mickedplay.ctf.event.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.tasks.TeamCircleHealer;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 25.04.2016 at 14:20 UTC+1.
 * You are not allowed to remove this comment.
 */
public class BlockPlaceListener extends GameEvent {

    private CaptureTheFlag ctf;

    public BlockPlaceListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (this.ctf.getGameStage() instanceof Ingame) {
            CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
            if (e.getBlock().getType() == Material.WOOD_PLATE) {
                for (Team team : this.ctf.getTeamManager().getTeams()) {
                    if (e.getBlock().getLocation().distance(team.getSpawnLocation()) > GameSettings.TEAM_SPAWN_RADIUS) {
                        this.ctf.getActionManager().addMine(e.getBlock(), ctfPlayer.getTeam());
                        continue;
                    }
                    ctfPlayer.sendMessage("§cYou can not place anything near enemy spawn!");
                }
            } else if (e.getBlock().getType() == Material.CAKE_BLOCK) {
                this.ctf.getActionManager().addCake(e.getBlock(), ctfPlayer.getTeam());
                new TeamCircleHealer(this.ctf, this.ctf.getCTFPlayer(e.getPlayer()), e.getBlock().getLocation());
            } else {
                e.setCancelled(e.getPlayer().getGameMode() != GameMode.CREATIVE);
            }
            return;
        }
        e.setCancelled(true);
    }
}