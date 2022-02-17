package play.mickedplay.ctf.game.stages;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.ctf.team.TeamManager;
import play.mickedplay.gameapi.game.GameStage;
import play.mickedplay.gameapi.utilities.Title;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mickedplay on 25.04.2016 at 13:51 UTC+1.
 * You are not allowed to remove this comment.
 */
public class DecisionPhase implements GameStage {

    private CaptureTheFlag ctf;

    public DecisionPhase(CaptureTheFlag ctf) {
        this.ctf = ctf;
    }

    public int getDuration() {
        return 16;
    }

    public void onStart() {
        this.ctf.setGameMap(this.ctf.getMapManager().getHighestVotedMap());
        this.ctf.setTeamManager(new TeamManager(this.ctf));
        this.ctf.getTeamManager().refreshMaxTeamSize();

        this.ctf.getCTFPlayers().values().forEach(ctfPlayer -> {
            ctfPlayer.getGameStats().setGameLogin();
            ctfPlayer.teleport(this.ctf.getLobbyManager().getDecisionSpawn());
            ctfPlayer.clean(false);
            ctfPlayer.getInventory().setHeldItemSlot(4);
            ctfPlayer.setHelmet(this.ctf.getActionManager().getRandomSelectItemStack());
            ctfPlayer.getInventory().setItem(4, this.ctf.getTeamChooser().getTeamChooseItem());
        });
    }

    public void onRun() {
        int timeLeft = this.ctf.getGameTimer().getTime();
        new Title((timeLeft > 5 ? "§a" : timeLeft <= 5 && timeLeft > 3 ? "§6" : "§c") + timeLeft, "§eChoose your team and class...", 0, 25, 5).sendToAll();
        if (timeLeft == 5) {
            Bukkit.getScheduler().runTaskAsynchronously(this.ctf.getPlugin(), () -> {
                ctf.getGameMap().load();
                this.ctf.getTeamManager().getTeams().forEach(Team::initTeamLocations);
            });
        }
    }

    public void onEnd() {
//        this.ctf.checkForEnd();
        if (!this.ctf.hasGameEnds()) {
            List<CTFPlayer> allPlayers = new ArrayList<>(this.ctf.getCTFPlayers().values());
            Collections.shuffle(allPlayers);
            for (CTFPlayer ctfPlayer : allPlayers) {
                if (ctfPlayer.isSpectator()) {
                    // setup spectator inventory;
                    ctfPlayer.teleport(this.ctf.getGameMap().getCenterLocation());
                    continue;
                }
                this.ctf.getTeamManager().sortTeams();
                if (ctfPlayer.getTeam() == null) ctfPlayer.setTeam(this.ctf.getTeamManager().getTeams().get(0));

                ctfPlayer.setPlayerListName(ctfPlayer.getTeam().getChatColor() + ctfPlayer.getName());
                ctfPlayer.clean(false);
                ctfPlayer.prepareForGame();
                Bukkit.getScheduler().runTaskLater(this.ctf.getPlugin(), () -> ctfPlayer.setGameMode(GameMode.SURVIVAL), 5L);
            }
            new Title("", "").sendToAll();
            this.ctf.getTeamManager().getTeams().forEach(Team::setup);
            this.ctf.setGameStage(new Ingame(this.ctf));
        }
    }
}