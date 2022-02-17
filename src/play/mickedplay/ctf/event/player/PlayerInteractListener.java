package play.mickedplay.ctf.event.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.DecisionPhase;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.gameclass.classes.WizardClass;
import play.mickedplay.ctf.player.gameclass.task.WizardWandEffect;
import play.mickedplay.ctf.shop.tasks.BoostPlayerEffect;
import play.mickedplay.ctf.shop.tasks.CoalSmokeEffect;
import play.mickedplay.ctf.shop.tasks.RailgunEffect;
import play.mickedplay.ctf.shop.tasks.RandomEnemyKill;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.stages.Lobby;

/**
 * Created by mickedplay on 27.04.2016 at 15:55 UTC+1.
 * You are not allowed to remove this comment.
 */
public class PlayerInteractListener extends GameEvent {

    private CaptureTheFlag ctf;

    public PlayerInteractListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getItem() != null) {
            if (this.ctf.getGameStage() instanceof Lobby) {
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (e.getItem().getType() == Material.COMPASS) {
                        e.getPlayer().openInventory(this.ctf.getVoteManager().getVotingInventory());
                    } else if (e.getItem().getType() == Material.NETHER_STAR) {
                        e.getPlayer().openInventory(this.ctf.getCTFPlayer(e.getPlayer()).getAchievementManager().getInventory());
                    }
                }
            } else if (this.ctf.getGameStage() instanceof DecisionPhase) {
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (e.getItem().getType() == Material.SKULL_ITEM) {
                        e.getPlayer().openInventory(this.ctf.getTeamChooser().getInventory());
                    } else if (e.getItem().getType() == Material.COMPASS) {
                        e.getPlayer().openInventory(this.ctf.getInventoryChangeClass().getInventory());
                    }
                }
            } else if (this.ctf.getGameStage() instanceof Ingame) {
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    CTFPlayer ctfPlayer = this.ctf.getCTFPlayer(e.getPlayer());
                    if (e.getItem().getType() == Material.ENDER_CHEST) {
                        e.getPlayer().openInventory(this.ctf.getItemShop().getInventory());
                    } else if (e.getItem().getType() == Material.COMPASS) {
                        if (ctfPlayer.isNearSpawn()) {
                            if (ctfPlayer.hasEnemyFlag()) {
                                ctfPlayer.sendMessage("§cYou can't change your class while capturing enemies flag!");
                            } else {
                                e.getPlayer().openInventory(this.ctf.getInventoryChangeClass().getInventory());
                            }
                        } else {
                            ctfPlayer.sendMessage("§cYou must be at spawn to change your class!");
                        }
                    } else if (e.getItem().getType() == Material.COAL) {
                        new CoalSmokeEffect(this.ctf, ctfPlayer);
                        ctfPlayer.removeClaimedShopItem(e.getItem());
                    } else if (e.getItem().getType() == Material.WOOD_HOE) {
                        new RailgunEffect(this.ctf, ctfPlayer);
                        ctfPlayer.removeClaimedShopItem(e.getItem());
                        e.setCancelled(true);
                    } else if (e.getItem().getType() == Material.BLAZE_POWDER) {
                        new RandomEnemyKill(this.ctf, ctfPlayer);
                        ctfPlayer.removeClaimedShopItem(e.getItem());
                    } else if (e.getItem().getType() == Material.SLIME_BLOCK) {
                        new BoostPlayerEffect(this.ctf, ctfPlayer);
                        ctfPlayer.removeClaimedShopItem(e.getItem());
                    } else if (e.getItem().getType() == Material.WOOD_PLATE || e.getItem().getType() == Material.CAKE) {
                        ctfPlayer.removeClaimedShopItem(e.getItem());
                    } else if (e.getItem().getType() == Material.BLAZE_ROD && ctfPlayer.getGameClass() instanceof WizardClass) {
                        new WizardWandEffect(this.ctf, ctfPlayer);
                    }
                }
            }
        }
    }
}