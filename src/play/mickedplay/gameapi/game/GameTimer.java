package play.mickedplay.gameapi.game;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.gameapi.game.stages.Lobby;

public class GameTimer extends BukkitRunnable {

    private final Minigame game;
    private int time, originalTime;
    private boolean started;

    public GameTimer(Minigame game) {
        this.game = game;
        this.started = false;
    }

    @Override
    public void run() {
        if (time >= 0) {
            if (time > 0) {
                if (game.getPlugin() != null) {
                    game.getGameStage().onRun();
                }
            } else if (game.getGameStage() != null) {
                game.getGameStage().onEnd();
            }
            if (game.getGameStage() instanceof Lobby) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.setLevel(time);
                    player.setExp((float) ((double) time / originalTime));
                });
            }
            time--;
        }
    }

    public void start() {
        if (started) {
            return;
        }
        this.started = true;
        this.runTaskTimer(this.game.getPlugin(), 0, 20L);
    }

    public void stop() {
        this.cancel();
        this.started = false;
    }

    public String getFormattedTime() {
        return String.format("%02d:%02d", this.time / 60, this.time % 60);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        this.originalTime = time;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Minigame getGame() {
        return game;
    }
}