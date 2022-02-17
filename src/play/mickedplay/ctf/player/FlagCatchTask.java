package play.mickedplay.ctf.player;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.utilities.Title;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.Collections;

/**
 * Created by mickedplay on 25.04.2016 at 21:36 UTC+1.
 * You are not allowed to remove this comment.
 */
public class FlagCatchTask extends BukkitRunnable {

    private CTFPlayer ctfPlayer;
    private int catchTime = 90;
    private ItemStack headFlag;
    private Title timeoutTitle;

    public FlagCatchTask(CTFPlayer ctfPlayer, Team enemyTeam) {
        this.ctfPlayer = ctfPlayer;
        this.ctfPlayer.hasEnemyFlag(true);
        this.headFlag = new ItemBuilder(Material.BANNER).withName("§rFlag of " + enemyTeam.getDisplayName()).withDamage(enemyTeam.getFlagBannerColor()).build();
        this.timeoutTitle = new Title("", "", 0, 25, 5);
        this.runTaskTimerAsynchronously(ctfPlayer.getTeam().getTeamManager().getCaptureTheFlag().getPlugin(), 0L, 20L);
    }

    @Override
    public void run() {
        this.catchTime--;
        ChatColor chatColor = this.catchTime <= 10 ? ChatColor.RED : this.catchTime > 10 && this.catchTime <= 20 ? ChatColor.GOLD : ChatColor.GREEN;
        if (this.headFlag.hasItemMeta()) {
            ItemMeta itemMeta = this.headFlag.getItemMeta();
            itemMeta.setLore(Collections.singletonList(chatColor + "" + this.catchTime + " §bseconds until explosion"));
            this.headFlag.setItemMeta(itemMeta);
        }
        this.headFlag.setAmount(this.catchTime >= 127 ? 127 : this.catchTime);
        this.ctfPlayer.setHelmet(this.headFlag);

        if (this.catchTime <= 15 && this.catchTime > 0) {
            this.timeoutTitle.setTitle(chatColor + "" + this.catchTime);
            this.timeoutTitle.setSubTitle("§7seconds until flag explosion!");
            this.timeoutTitle.sendTo(this.ctfPlayer.getPlayer());
            this.ctfPlayer.getWorld().spigot().playEffect(this.ctfPlayer.getLocation().clone().add(0D, 2D, 0D), Effect.LARGE_SMOKE, 0, 0, 0F, 0F, 0F, 0.1F, 20, 50);
        } else if (catchTime == 0) {
            this.ctfPlayer.getPlayer().setHealth(0);
            this.ctfPlayer.getWorld().createExplosion(this.ctfPlayer.getX(), this.ctfPlayer.getY(), this.ctfPlayer.getZ(), 2.7F, false, false);
            this.ctfPlayer.getWorld().spigot().playEffect(this.ctfPlayer.getLocation().clone().add(0D, 1.8D, 0D), Effect.MOBSPAWNER_FLAMES, 0, 0, 0F, 0F, 0F, 0.1F, 20, 50);
        }
    }
}