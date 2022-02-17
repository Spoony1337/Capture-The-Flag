package play.mickedplay.ctf.team;

import org.bukkit.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import play.mickedplay.ctf.misc.Helper;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.shop.LevelType;
import play.mickedplay.ctf.team.task.BaseEffectTask;
import play.mickedplay.ctf.team.task.FlagEffectTask;
import play.mickedplay.gameapi.utilities.ActionBar;
import play.mickedplay.gameapi.utilities.Title;
import play.mickedplay.gameapi.utilities.game.TeamData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 25.04.2016 at 18:09 UTC+1.
 * You are not allowed to remove this comment.
 */
public class Team {

    private TeamManager teamManager;
    private TeamData data;
    private List<CTFPlayer> players, originalPlayers;
    private CTFPlayer currentCapturer;
    private Location spawnLocation, flagCenter, flagEffectLocation;
    private FlagEffectTask flagEffectTask;
    private JSONObject jsonObject;
    private int captures, experience;

    public Team(TeamManager teamManager, TeamData data, JSONObject jsonObject) {
        this.teamManager = teamManager;
        this.data = data;
        this.players = new ArrayList<>();
        this.jsonObject = jsonObject;
        teamManager.addTeam(this);
    }

    public void initTeamLocations() {
        Location[] locations = Helper.getLocationsFromJson((JSONArray) this.jsonObject.get("locations"), this.teamManager.getCaptureTheFlag().getGameMap().getWorldName());
        this.spawnLocation = locations[0];
        this.flagCenter = locations[1];

        this.flagEffectLocation = this.flagCenter.getBlock().getLocation();
        this.teamManager.addFlag(this.flagCenter.getBlock(), this);
    }

    public void setup() {
        new BaseEffectTask(this);
        this.flagEffectTask = new FlagEffectTask(this, true);
    }

    public void cancelFlagEffectTask() {
        if (this.flagEffectTask != null) {
            this.flagEffectTask.cancel();
            this.flagEffectTask = null;
        }
    }

    public void refreshFlagEffectTask(CTFPlayer capturer) {
        capturer.getTeam().setCurrentCapturer(null);
        capturer.hasEnemyFlag(false);
        this.teamManager.updateSpectatorInventory();
        this.cancelFlagEffectTask();
        this.flagEffectTask = new FlagEffectTask(this, false);
    }

    public void addCapture(Team targetTeam, CTFPlayer capturer) {
        this.captures++;
        this.addExperience(capturer, LevelType.FLAG_CAPTURE);
        new Title("", "§eThe flag of " + targetTeam.getDisplayName() + " §ehas been captured!").sendToAll();
        if (capturer.getFlagCatchTask() != null) {
            capturer.getFlagCatchTask().cancel();
            capturer.hasEnemyFlag(false);
        }

        this.teamManager.getTeams().forEach(team -> team.getPlayers().forEach(ctfPlayer -> {
            ctfPlayer.clean(false);
            ctfPlayer.prepareForGame();
            Bukkit.getScheduler().runTaskLater(this.teamManager.getCaptureTheFlag().getPlugin(), () -> ctfPlayer.setGameMode(GameMode.SURVIVAL), 5L);
        }));
    }

    public void addExperience(LevelType levelType) {
        this.addExperience(null, levelType);
    }

    public void addExperience(CTFPlayer ctfPlayer, LevelType levelType) {
        if (ctfPlayer != null) {
            ctfPlayer.getGameStats().addGainedExp(levelType.getLevel());
        }
        this.experience += levelType.getLevel();
        this.players.forEach(teamPlayer -> {
            new ActionBar("§7+§5" + levelType.getLevel() + " §dExperience §3(§b" + levelType.getReason() + "§3)").sendTo(teamPlayer.getPlayer());
            teamPlayer.setLevel(this.experience);
        });
    }

    public void removeExperience(int experience) {
        this.experience -= experience;
        this.getPlayers().forEach(teamPlayer -> teamPlayer.setLevel(this.experience));
    }

    public CTFPlayer getCurrentCapturer() {
        return currentCapturer;
    }

    public void setCurrentCapturer(CTFPlayer currentCapturer) {
        this.currentCapturer = currentCapturer;
    }

    public void createPlayerCopy() {
        this.originalPlayers = new ArrayList<>(this.players);
    }

    public List<CTFPlayer> getOriginalPlayers() {
        return originalPlayers;
    }

    public TeamData getData() {
        return data;
    }

    public int getExperience() {
        return experience;
    }

    public List<CTFPlayer> getPlayers() {
        return players;
    }

    public int getSize() {
        return players.size();
    }

    public void addPlayer(CTFPlayer ctfPlayer) {
        this.players.add(ctfPlayer);
    }

    public void removePlayer(CTFPlayer ctfPlayer) {
        this.players.remove(ctfPlayer);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getFlagCenter() {
        return flagCenter;
    }

    public Location getFlagEffectLocation() {
        return flagEffectLocation;
    }

    public byte getFlagBlockColor() {
        return (byte) this.data.getBlockMetaId();
    }

    public byte getFlagBannerColor() {
        return (byte) this.data.getBannerMetaId();
    }

    public String getName() {
        return this.data.getName();
    }

    public ChatColor getChatColor() {
        return this.data.getChatColor();
    }

    public String getDisplayName() {
        return this.data.getChatColor() + "§lTeam " + this.data.getName();
    }

    public Color getArmorColor() {
        return this.data.getColor();
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public int getCaptures() {
        return captures;
    }
}