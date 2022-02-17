package play.mickedplay.ctf.event.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.utilities.Title;

/**
 * Created by mickedplay on 25.04.2016 at 14:20 CEST.
 * You are not allowed to remove this comment.
 */
public class BlockBreakListener extends GameEvent {

    private CaptureTheFlag ctf;

    public BlockBreakListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (!(this.ctf.getGameStage() instanceof Ingame)) {
            e.setCancelled(true);
            return;
        }
        Block block = e.getBlock();
        e.setCancelled(e.getPlayer().getGameMode() != GameMode.CREATIVE);
        CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
        if (block.getType() == Material.WOOL) {
            Team enemyTeam = this.ctf.getTeamManager().getEnemyTeamByFlag(block);
            if (enemyTeam == null) return;

            Team playerTeam = ctfPlayer.getTeam();
            if (enemyTeam == playerTeam) {
                if (!ctfPlayer.hasEnemyFlag()) {
                    ctfPlayer.sendMessage("§cYou can't break your own flag!");
                    return;
                }
                block.getWorld().strikeLightningEffect(block.getLocation());
                playerTeam.addCapture(ctfPlayer.getTeamOfStolenFlag(), ctfPlayer);
                ctfPlayer.getTeamOfStolenFlag().refreshFlagEffectTask(ctfPlayer);
                ctfPlayer.getGameStats().addCapturedFlag();
                return;
            }
            Bukkit.broadcastMessage(playerTeam.getChatColor() + ctfPlayer.getName() + " §estole the flag of " + enemyTeam.getDisplayName() + "§e!");
            playerTeam.addExperience(ctfPlayer, LevelType.FLAG_STEAL);
            playerTeam.setCurrentCapturer(ctfPlayer);
            enemyTeam.getPlayers().forEach(enemyPlayer -> new Title("", enemyTeam.getChatColor() + "Your flag §ehas been stolen!").sendTo(enemyPlayer.getPlayer()));
            enemyTeam.cancelFlagEffectTask();
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 1F, 1F);
            });
            block.getWorld().strikeLightningEffect(block.getLocation());
            block.setType(Material.AIR);
            ctfPlayer.addPotionEffect(110, PotionEffectType.BLINDNESS);
            ctfPlayer.addPotionEffect(180, PotionEffectType.SPEED, 1);
            ctfPlayer.setTeamOfStolenFlag(enemyTeam);
            ctfPlayer.setFlagCatchTask(enemyTeam);
            ctfPlayer.getGameStats().addStolenFlag();
            this.ctf.getTeamManager().updateSpectatorInventory();
        } else if (block.getType() == Material.PACKED_ICE) {
            e.getPlayer().sendMessage("§cThis flag is still frozen!");
        } else if (block.getType() == Material.CAKE_BLOCK) {
            if (ctfPlayer.getTeam() != this.ctf.getActionManager().getTeamByCake(block)) {
                this.ctf.getActionManager().getCakes().remove(block);
                e.setCancelled(false);
            }
        }
    }
}