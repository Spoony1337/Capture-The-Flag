package play.mickedplay.gameapi.game.stages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import play.mickedplay.gameapi.game.GameStage;
import play.mickedplay.gameapi.game.Minigame;

public abstract class Restart implements GameStage {

    private Minigame minigame;

    public Restart(Minigame minigame) {
        this.minigame = minigame;
    }


    @Override
    public void onRun() {
        if (this.minigame.getGameTimer().getTime() <= 5) Bukkit.broadcastMessage(ChatColor.RED + "Der Server startet in " + this.minigame.getGameTimer().getTime() + " Sekunden neu.");
    }

    @Override
    public void onEnd() {
        Bukkit.shutdown();
    }

//    public void showFireworks(Color color, Color fade, int power){
//         game.getMapManager()
//                .getGameMap().getLocations().stream().forEach((loc) -> {
//                    new FireworkBuilder(loc).withColor(color)
//                            .withFade(fade).withPower(power).build();
//        });
//    }

    public Minigame getGame() {
        return minigame;
    }
}