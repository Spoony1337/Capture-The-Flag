package play.mickedplay.ctf.player.gameclass.task;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 26.04.2016 at 22:34 UTC+1.
 * You are not allowed to remove this comment.
 */
public class ArcherClassTask extends BukkitRunnable {

    private CTFPlayer ctfPlayer;
    private int tick, period;

    public ArcherClassTask(CTFPlayer ctfPlayer) {
        this.ctfPlayer = ctfPlayer;
        this.period = 8 * 20;
        this.tick = 0;
        this.runTaskTimerAsynchronously(ctfPlayer.getCaptureTheFlag().getPlugin(), 5L, 1L);
    }

    @Override
    public void run() {

        float expProgress = (float) ((double) tick / period);
        if (expProgress <= 1.0F) {
            this.ctfPlayer.getPlayer().setExp(expProgress);
        }
        if (tick <= period) {
            this.tick += this.ctfPlayer.isNearSpawn() ? 2 : 1;
        } else {
            this.tick = 0;
            this.ctfPlayer.getInventory().addItem(new ItemBuilder(Material.ARROW).hideFlags(ItemFlag.values()).build());
            this.ctfPlayer.getPlayer().setExp(0);
            this.ctfPlayer.setArcherArrows(this.ctfPlayer.getArcherArrows() + 1);
            if (this.ctfPlayer.getArcherArrows() == GameSettings.MAX_ARCHER_ARROWS) {
                this.ctfPlayer.setArcherClassTask(null);
                this.cancel();
            }
        }
    }
}