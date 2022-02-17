package play.mickedplay.ctf.game.stages;

import org.bukkit.Bukkit;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.game.GameStage;

/**
 * Created by mickedplay on 01.05.2016 at 14:13 UTC+1.
 * You are not allowed to remove this comment.
 */
public class Restart implements GameStage {

    private CaptureTheFlag ctf;

    public Restart(CaptureTheFlag ctf) {
        this.ctf = ctf;
    }

    @Override
    public int getDuration() {
        return 11;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRun() {
        Bukkit.broadcastMessage("§4Server restarts in §c" + this.ctf.getGameTimer().getTime() + " seconds§4.");
    }

    @Override
    public void onEnd() {
        Bukkit.reload();
    }
}