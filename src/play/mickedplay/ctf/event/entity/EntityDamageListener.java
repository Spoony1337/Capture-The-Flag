package play.mickedplay.ctf.event.entity;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.gameclass.classes.PitcherClass;
import play.mickedplay.ctf.player.gameclass.classes.WarriorClass;
import play.mickedplay.ctf.player.gameclass.classes.WizardClass;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.GameStage;

import java.util.Random;

/**
 * Created by mickedplay on 05.04.2016 at 23:09 UTC+1.
 * You are not allowed to remove this comment.
 */
public class EntityDamageListener extends GameEvent {

    private CaptureTheFlag ctf;

    public EntityDamageListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        GameStage gameStage = this.ctf.getGameStage();
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (gameStage instanceof Ingame) {
                if (e.getEntity() instanceof Player) {
                    CTFPlayer victim = this.ctf.getCTFPlayer((Player) e.getEntity());
                    if (e.getDamager() instanceof Player) {
                        CTFPlayer damager = this.ctf.getCTFPlayer((Player) e.getDamager());
                        if (!damager.isSpectator() && (damager.getTeam() == victim.getTeam() || damager.distance(damager.getTeam().getSpawnLocation()) <= GameSettings.TEAM_SPAWN_RADIUS)) {
                            e.setCancelled(true);
                            return;
                        }
                        if (victim.isNearSpawn()) {
                            e.setCancelled(true);
                        } else {
                            if (damager.getGameClass() instanceof PitcherClass) {
                                if (new Random().nextInt(10) == 0) e.setDamage(e.getDamage() + (GameSettings.PITCHER_SPECIAL_DAMAGE * 2));
                            } else if (damager.getGameClass() instanceof WarriorClass) {
                                if (new Random().nextInt(4) == 0) e.setDamage(e.getDamage() * 2);
                            } else if (damager.getGameClass() instanceof WizardClass && damager.getItemInMainHand().getType() == Material.BLAZE_ROD) {
                                victim.addPotionEffect(30, PotionEffectType.POISON, 5);
                            }
                            e.setCancelled(false);
                        }
                    } else if (e.getDamager() instanceof Arrow) {
                        Arrow arrow = (Arrow) e.getDamager();
                        CTFPlayer damager = this.ctf.getCTFPlayer((Player) (arrow).getShooter());
                        if (!damager.isSpectator() && (damager.getTeam() == victim.getTeam() || damager.distance(damager.getTeam().getSpawnLocation()) <= GameSettings.TEAM_SPAWN_RADIUS)) {
                            e.setCancelled(true);
                            return;
                        }
                        e.setCancelled(victim.isNearSpawn());
                    }
                    return;
                }
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            return;
        }
        if (!(gameStage instanceof Ingame)) {
            event.setCancelled(true);
        }
    }
}