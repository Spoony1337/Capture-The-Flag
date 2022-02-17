package play.mickedplay.ctf;

import de.slikey.effectlib.EffectManager;
import org.bukkit.entity.Player;
import play.mickedplay.ctf.game.ActionManager;
import play.mickedplay.ctf.game.InventoryChangeClass;
import play.mickedplay.ctf.game.ItemShop;
import play.mickedplay.ctf.game.VoteManager;
import play.mickedplay.ctf.game.stages.DecisionPhase;
import play.mickedplay.ctf.game.stages.End;
import play.mickedplay.ctf.map.GameMap;
import play.mickedplay.ctf.map.LobbyManager;
import play.mickedplay.ctf.map.MapManager;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.SpectatorInventoryHandler;
import play.mickedplay.ctf.player.gameclass.GameClassManager;
import play.mickedplay.ctf.sql.MySQL;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.ctf.team.TeamManager;
import play.mickedplay.gameapi.game.Minigame;
import play.mickedplay.gameapi.game.stages.Lobby;

import java.util.HashMap;

/**
 * Created by mickedplay on 25.04.2016 at 13:48 UTC+1.
 * You are not allowed to remove this comment.
 */
public class CaptureTheFlag extends Minigame {

    private CTFPlugin ctfPlugin;
    private LobbyManager lobbyManager;
    private MapManager mapManager;
    private VoteManager voteManager;

    private TeamManager teamManager;
    private GameClassManager gameClassManager;

    private ActionManager actionManager;

    private HashMap<Player, CTFPlayer> ctfPlayers;

    private InventoryChangeClass inventoryChangeClass;
    private TeamChooser teamChooser;
    private ItemShop itemShop;
    private boolean gameEnds;

    private GameMap gameMap;
    private EffectManager effectManager;

    private SpectatorInventoryHandler spectatorInventoryHandler;
    private MySQL mySQL;

    public CaptureTheFlag(CTFPlugin ctfPlugin) {
        super(ctfPlugin);
        this.ctfPlugin = ctfPlugin;
        this.gameClassManager = new GameClassManager();
        this.lobbyManager = new LobbyManager(this);
        this.mapManager = new MapManager(this);
        this.voteManager = new VoteManager(this.mapManager);
        this.actionManager = new ActionManager(this);

        this.teamManager = null;
        this.ctfPlayers = new HashMap<>();
        this.inventoryChangeClass = new InventoryChangeClass(this);
        this.teamChooser = new TeamChooser(this);
        this.gameEnds = false;
        this.itemShop = new ItemShop(this);
        this.effectManager = new EffectManager(ctfPlugin);

        this.mySQL = new MySQL(this);

        this.setGameStage(new Lobby(this, new DecisionPhase(this)));
        this.getGameTimer().setTime(60);
    }

    @Override
    public String getName() {
        return "CaptureTheFlag";
    }

    @Override
    public int getMaxPlayers() {
        return 24;
    }

    @Override
    public int getMinPlayers() {
        return 2;
    }

    public CTFPlugin getCTFPlugin() {
        return this.ctfPlugin;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public GameClassManager getGameClassManager() {
        return gameClassManager;
    }

    public ItemShop getItemShop() {
        return itemShop;
    }

    public CTFPlayer addCTFPlayer(Player player) {
        if (this.ctfPlayers.containsKey(player)) {
            return this.ctfPlayers.get(player);
        }
        CTFPlayer ctfPlayer = new CTFPlayer(this, player);
        this.ctfPlayers.put(player, ctfPlayer);
        return ctfPlayer;
    }

    public void removeCTFPlayer(Player player) {
        this.ctfPlayers.remove(player);
    }

    public CTFPlayer getCTFPlayer(Player player) {
        return this.ctfPlayers.get(player);
    }

    public HashMap<Player, CTFPlayer> getCTFPlayers() {
        return ctfPlayers;
    }

    public InventoryChangeClass getInventoryChangeClass() {
        return inventoryChangeClass;
    }

    public TeamChooser getTeamChooser() {
        return teamChooser;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public boolean hasGameEnds() {
        return gameEnds;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public void checkForEnd() {
        for (Team team : teamManager.getTeams()) {
            if (team.getSize() <= 0) {
                this.setGameStage(new End(this));
                this.gameEnds = true;
                break;
            }
        }
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setupSpectatorInventoryHandler() {
        this.spectatorInventoryHandler = new SpectatorInventoryHandler(this);
    }

    public SpectatorInventoryHandler getSpectatorInventoryHandler() {
        return spectatorInventoryHandler;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}