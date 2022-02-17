package play.mickedplay.ctf.team;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.utilities.game.TeamData;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mickedplay on 25.04.2016 at 18:05 UTC+1.
 * You are not allowed to remove this comment.
 */
public class TeamManager {

    private CaptureTheFlag ctf;
    private List<Team> teams;
    private HashMap<Block, Team> teamFlags;
    private int maxTeamSize;

    public TeamManager(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.teams = new CopyOnWriteArrayList<>();
        this.teamFlags = new HashMap<>();
        this.initializeTeams();
        this.ctf.getTeamChooser().setupVoteInventory(this.teams);
    }

    private void initializeTeams() {
        JSONObject jsonObject = this.ctf.getGameMap().getJSONReader().getJsonObject();
        JSONArray teamSpawnsArray = (JSONArray) jsonObject.get("spawns");
        List<TeamData> fullTeamData = new ArrayList<>(Arrays.asList(TeamData.RED, TeamData.LIGHT_BLUE));

        for (Object singleSpawn : teamSpawnsArray) {
            TeamData randomTeamData = fullTeamData.get(new Random().nextInt(fullTeamData.size()));
            new Team(this, randomTeamData, (JSONObject) singleSpawn);
            fullTeamData.remove(randomTeamData);
        }
        Collections.shuffle(this.teams);
    }

    public void copyOriginalPlayers() {
        this.teams.forEach(Team::createPlayerCopy);
    }

    public void updateSpectatorInventory() {
        this.ctf.getSpectatorInventoryHandler().initializeSpectatorItems();
        this.ctf.getCTFPlayers().values().forEach(ctfPlayer -> {
            if (ctfPlayer.isSpectator()) {
                this.ctf.getSpectatorInventoryHandler().setInventory(ctfPlayer);
            }
        });
    }

    public void refreshMaxTeamSize() {
        this.maxTeamSize = Bukkit.getOnlinePlayers().size() / this.ctf.getTeamManager().getTeams().size();
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public Team getEnemyTeamByFlag(Block block) {
        return this.teamFlags.get(block);
    }

    public void addFlag(Block block, Team team) {
        this.teamFlags.put(block, team);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void sortTeams() {
        Collections.sort(this.teams, (first, second) -> Integer.valueOf(first.getSize()).compareTo(second.getSize()));
    }

    public CaptureTheFlag getCaptureTheFlag() {
        return ctf;
    }
}