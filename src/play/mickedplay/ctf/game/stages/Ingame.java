package play.mickedplay.ctf.game.stages;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.game.GameStage;
import play.mickedplay.gameapi.utilities.Title;
import play.mickedplay.gameapi.utilities.game.GameUtilities;
import play.mickedplay.gameapi.utilities.game.TimeType;

/**
 * Created by mickedplay on 25.04.2016 at 13:51 UTC+1.
 * You are not allowed to remove this comment.
 */
public class Ingame implements GameStage {

    private CaptureTheFlag ctf;

    public Ingame(CaptureTheFlag ctf) {
        this.ctf = ctf;
    }

    public int getDuration() {
        return 901;
    }

    public void onStart() {
        this.ctf.getTeamManager().copyOriginalPlayers();
        this.ctf.setupSpectatorInventoryHandler();
        this.ctf.getTeamManager().updateSpectatorInventory();
        new Title("", "§aGood luck and have fun!", 10, 30, 5).sendToAll();
    }

    public void onRun() {
        int timeLeft = this.ctf.getGameTimer().getTime();
        String formattedTimeLeft = GameUtilities.formatTime(timeLeft, TimeType.MINUTES_SECONDS);
        if (((timeLeft <= 120 && timeLeft % 60 == 0) || timeLeft <= 30 && timeLeft % 10 == 0) || timeLeft > 0 && timeLeft < 10) {
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F));
            new Title("", "§eGame ends in §c" + formattedTimeLeft + "§e!", 0, 30, 5).sendToAll();
        }
    }

    public void onEnd() {
        this.ctf.setGameStage(new End(this.ctf));
    }
}