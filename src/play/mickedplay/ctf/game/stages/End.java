package play.mickedplay.ctf.game.stages;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.game.GameStage;
import play.mickedplay.gameapi.utilities.Title;
import play.mickedplay.gameapi.utilities.builder.FireworkBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by mickedplay on 01.05.2016 at 14:08 UTC+1.
 * You are not allowed to remove this comment.
 */
public class End implements GameStage {

    private CaptureTheFlag ctf;

    public End(CaptureTheFlag ctf) {
        this.ctf = ctf;
    }

    @Override
    public int getDuration() {
        return 10;
    }

    @Override
    public void onStart() {
        Bukkit.getScheduler().runTaskLater(this.ctf.getPlugin(), () -> ctf.getCTFPlayers().values().forEach(ctfPlayer -> {
            ctfPlayer.cancelArcherClassTask();
            ctfPlayer.cancelFlagCatchTask();
            ctfPlayer.clean(false);
            ctfPlayer.teleport(ctf.getLobbyManager().getSpawn());
            ctfPlayer.sendRoundStats();
            ctfPlayer.getGameStats().saveGameStatistics();
        }), 2L);

        List<Team> teamList = this.ctf.getTeamManager().getTeams();
        Collections.shuffle(teamList);
        Collections.sort(teamList, (first, second) -> Integer.valueOf(first.getCaptures()).compareTo(second.getCaptures()));

        Team winnerTeam = teamList.get(teamList.size() - 1);
        this.ctf.getActionManager().setWinningTeam(winnerTeam);
        if (winnerTeam.getCaptures() != teamList.get(teamList.size() - 2).getCaptures()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.ctf.getPlugin(), () -> {
                new Title(winnerTeam.getDisplayName(), winnerTeam.getChatColor() + "won the game!").sendToAll();
                for (CTFPlayer ctfPlayer : winnerTeam.getPlayers()) {
                    for (int i = 1; i <= 3; i++) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(this.ctf.getPlugin(), () -> {
                            new FireworkBuilder(ctfPlayer.getLocation()).withPower(1).withColor(winnerTeam.getArmorColor()).withType(FireworkEffect.Type.BURST).build();
                            new FireworkBuilder(ctfPlayer.getLocation()).withPower(1).withColor(winnerTeam.getArmorColor()).withType(FireworkEffect.Type.BALL).build();
                        }, 10 + i + 5);
                    }
                }
            }, 10L);
            return;
        }
        this.ctf.getActionManager().setWinningTeam(null);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.ctf.getPlugin(), () -> new Title("§7Nobody", "§7won the game!").sendToAll(), 10);
    }

    @Override
    public void onRun() {

    }

    @Override
    public void onEnd() {
        this.ctf.setGameStage(new Restart(this.ctf));
    }
}