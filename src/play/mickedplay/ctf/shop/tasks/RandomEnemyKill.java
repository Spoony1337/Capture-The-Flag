package play.mickedplay.ctf.shop.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.utilities.ActionBar;
import play.mickedplay.gameapi.utilities.Title;

import java.util.Random;

/**
 * Created by mickedplay on 23.06.2016 at 13:07 CEST.
 * You are not allowed to remove this comment.
 */
public class RandomEnemyKill extends BukkitRunnable {

    private CaptureTheFlag ctf;
    private CTFPlayer ctfPlayer;
    private Team targetTeam;
    private int loop = 0, time = 40, maxTicks;
    private ActionBar actionBar;

    public RandomEnemyKill(CaptureTheFlag ctf, CTFPlayer ctfPlayer) {
        this.ctf = ctf;
        this.ctfPlayer = ctfPlayer;
        this.actionBar = new ActionBar("");
        this.targetTeam = this.getRandomTeam();
        this.maxTicks = time / 2;
        this.ctfPlayer.removeItemInMainHand();
        this.runTaskTimerAsynchronously(ctf.getPlugin(), 0L, 2L);
    }

    public void run() {
        if (this.maxTicks > 0) {
            this.maxTicks--;
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ARROW_HIT, 1F, 1F));
        }
        if (this.maxTicks == 0) {
            if (this.targetTeam.getPlayers().size() > 0) {
                CTFPlayer targetEnemy = this.targetTeam.getPlayers().get(this.loop);
                new Title("", "§7Getötet: " + targetEnemy.getDisplayName(), 0, 30, 5).sendTo(this.ctfPlayer.getPlayer());
                targetEnemy.setHealth(0);
                this.ctfPlayer.getTeam().addExperience(this.ctfPlayer, targetEnemy.hasEnemyFlag() && targetEnemy.getTeamOfStolenFlag() == this.ctfPlayer.getTeam() ? LevelType.RANDOM_ENEMY_KILL_CAPTURER : LevelType.RANDOM_ENEMY_KILL);
                this.ctfPlayer.getGameStats().addKill();
                this.cancel();
            }
            return;
        }
        this.loop++;
        if (this.loop == this.targetTeam.getPlayers().size()) {
            this.loop = 0;
        }
        this.actionBar.setMessage(this.targetTeam.getPlayers().get(this.loop).getDisplayName());
        this.actionBar.sendTo(this.ctfPlayer.getPlayer());
    }

    /*
        Returns a random team
     */
    private Team getRandomTeam() {
        Team team = this.ctf.getTeamManager().getTeams().get(new Random().nextInt(this.ctf.getTeamManager().getTeams().size()));
        return team == this.ctfPlayer.getTeam() ? this.getRandomTeam() : team;
    }
}