package play.mickedplay.ctf.event.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.gameclass.classes.NinjaClass;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 05.04.2016 at 23:09 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerMoveListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerMoveListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void playerMoveBody(PlayerMoveEvent e) {
        if (e.getTo().distance(e.getFrom()) == 0) {
            return;
        }

        if (this.ctf.getGameStage() instanceof Ingame) {
            CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
            if (ctfPlayer.isSpectator()) {
                return;
            }
            for (Team team : this.ctf.getTeamManager().getTeams()) {
                if ((team != ctfPlayer.getTeam() || ctfPlayer.hasEnemyFlag()) && ctfPlayer.distance(team.getSpawnLocation()) <= GameSettings.TEAM_SPAWN_RADIUS) {
                    ctfPlayer.setVelocity(ctfPlayer.getLocation().getDirection().multiply(ctfPlayer.getLocation().getPitch() <= -65 ? 0.8 : -0.8).add(new Vector(0, 0.45, 0)));
                    break;
                }
            }
            if (e.getPlayer().getLocation().getBlock().getType() == Material.WOOD_PLATE) {
                Block block = e.getPlayer().getLocation().getBlock();
                if (this.ctf.getActionManager().getTeamByMine(block) != ctfPlayer.getTeam()) {
                    if (this.ctf.getActionManager().getTeamByMine(block) != null) {
                        block.getLocation().getWorld().createExplosion(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 4F, false, false);
                        block.setType(Material.AIR);
                        ctfPlayer.setHealth(0D);
                        this.ctf.getActionManager().getTeamByMine(block).addExperience(LevelType.MINE_EXPLOSION);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerToggleSneak(PlayerToggleSneakEvent e) {
        if (this.ctf.getGameStage() instanceof Ingame) {
            if (this.ctf.getCTFPlayer(e.getPlayer()).getGameClass() instanceof NinjaClass) {
                if (e.isSneaking()) {
                    this.ctf.getCTFPlayer(e.getPlayer()).addPotionEffect(PotionEffectType.INVISIBILITY, 0);
                } else {
                    e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
                }
            }
        }
    }
}