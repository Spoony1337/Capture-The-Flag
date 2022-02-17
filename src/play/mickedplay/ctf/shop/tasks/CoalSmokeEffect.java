package play.mickedplay.ctf.shop.tasks;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;

/**
 * Created by mickedplay on 19.06.2016 at 22:27 UTC+1.
 * You are not allowed to remove this comment.
 */
public class CoalSmokeEffect extends BukkitRunnable {

    private CTFPlayer ctfPlayer;
    private Item grenade;

    private double radius = 4.5;

    public CoalSmokeEffect(CaptureTheFlag ctf, CTFPlayer ctfPlayer) {
        this.ctfPlayer = ctfPlayer;
        this.grenade = this.ctfPlayer.getWorld().dropItem(ctfPlayer.getLocation(), this.ctfPlayer.getItemInMainHand());
        this.grenade.setVelocity(this.ctfPlayer.getEyeLocation().getDirection());
        this.ctfPlayer.getPlayer().getNearbyEntities(this.radius, this.radius, this.radius).stream().filter(entity -> entity instanceof Player).forEach(entity -> {
            Player player = (Player) entity;
            player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 1F, 1F);
        });
        ctfPlayer.removeItemInMainHand();
        this.runTaskLater(ctf.getPlugin(), 40L);
    }

    @Override
    public void run() {
        this.grenade.getWorld().playSound(this.grenade.getLocation(), Sound.EXPLODE, 1F, 3F);
        this.grenade.getWorld().spigot().playEffect(this.grenade.getLocation(), Effect.LARGE_SMOKE, 0, 0, 0F, 0F, 0F, 0.1F, 20, 50);
        this.grenade.getNearbyEntities(this.radius, this.radius, this.radius).stream().filter(entity -> entity instanceof Player).forEach(entity -> {
            Player target = (Player) entity;
            if (target != this.ctfPlayer.getPlayer()) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 130, 127));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 130, 2));
            }
        });
        this.grenade.remove();
    }
}
