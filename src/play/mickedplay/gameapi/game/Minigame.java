package play.mickedplay.gameapi.game;

import org.bukkit.plugin.Plugin;

public abstract class Minigame {

    private final Plugin plugin;
    private final GameTimer gameTimer;
    private GameStage gameStage;

    public Minigame(Plugin plugin) {
        this.plugin = plugin;
        this.gameTimer = new GameTimer(this);
    }

    public abstract String getName();

    public abstract int getMaxPlayers();

    public abstract int getMinPlayers();

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
        this.gameStage.onStart();
        this.gameTimer.setTime(this.gameStage.getDuration());
    }

    public Plugin getPlugin() {
        return plugin;
    }
}