package play.mickedplay.ctf.shop.tasks;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;

/**
 * Created by mickedplay on 19.06.2016 at 22:27 UTC+1.
 * You are not allowed to remove this comment.
 */
public class BoostPlayerEffect extends BukkitRunnable {

    private CTFPlayer ctfPlayer;

    public BoostPlayerEffect(CaptureTheFlag ctf, CTFPlayer ctfPlayer) {
        this.ctfPlayer = ctfPlayer;
        ctfPlayer.removeItemInMainHand();
        this.runTaskLater(ctf.getPlugin(), 0L);
    }

    @Override
    public void run() {
        this.ctfPlayer.playSound(Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
        this.ctfPlayer.setVelocity(ctfPlayer.getLocation().getDirection().multiply(0.8).add(new Vector(0, 0.45, 0)));
    }
}