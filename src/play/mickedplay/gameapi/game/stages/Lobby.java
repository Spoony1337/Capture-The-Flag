package play.mickedplay.gameapi.game.stages;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.game.GameStage;
import play.mickedplay.gameapi.game.Minigame;
import play.mickedplay.gameapi.utilities.player.PlayerUtilities;

public class Lobby extends GameEvent implements GameStage {

    private Minigame minigame;
    private GameStage next;
    private World lobby;

    public Lobby(Minigame minigame, GameStage next) {
        super(minigame.getPlugin());
        this.minigame = minigame;
        this.next = next;

        this.lobby = Bukkit.createWorld(new WorldCreator("lobby"));
        this.lobby.setAutoSave(false);
        this.lobby.getEntities().clear();
        this.lobby.setTime(6000);
        this.lobby.setGameRuleValue("doDaylightCycle", "false");
    }

    @Override
    public int getDuration() {
        return 60;
    }

    @Override
    public void onStart() {
//        register();
//        minigame.getCommandManager().register();
//        minigame.getSettings().setCanChat(true);
//        minigame.getSettings().setCanPlayerJoin(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");
        if (!this.minigame.getGameTimer().isStarted()) {
            if (Bukkit.getOnlinePlayers().size() >= this.minigame.getMinPlayers()) {
                this.minigame.getGameTimer().start();
//                Bukkit.broadcastMessage(format(ChatColor.GREEN));
            }
        }
        PlayerUtilities.cleanPlayer(player);
        player.teleport(this.lobby.getSpawnLocation());
//        player.getInventory().setItem(8, new ItemBuilder(Material.BED).withName(ChatColor.YELLOW + "Back to " + ChatColor.BOLD + "Hub").build());
        Bukkit.broadcastMessage(ChatColor.GRAY + player.getDisplayName() + ChatColor.GOLD + " betritt das Spiel.");
        player.sendMessage(ChatColor.GOLD + "Willkommen zu " + ChatColor.GREEN + this.minigame.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Bukkit.broadcastMessage(ChatColor.GRAY + event.getPlayer().getDisplayName() + ChatColor.GOLD + " has left!");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() != Material.AIR) {
                if (event.getItem().getType() == Material.BED) {
                    //TODO: send player to hub.
                }
            }
        }
    }

    @Override
    public void onRun() {
        if (minigame.getGameTimer().getTime() == 30) Bukkit.broadcastMessage(format(ChatColor.YELLOW));
        else if (minigame.getGameTimer().getTime() <= 10) Bukkit.broadcastMessage(format(ChatColor.RED));
    }

    @Override
    public void onEnd() {
        if (Bukkit.getOnlinePlayers().size() >= minigame.getMinPlayers()) {
            this.minigame.setGameStage(this.next);
            unregister();
        } else {
            this.minigame.getGameTimer().stop();
            Bukkit.broadcastMessage(ChatColor.RED + "Start canceled! Timer reset.");
            Bukkit.broadcastMessage(format(ChatColor.GREEN));
        }
    }

    private String format(ChatColor color) {
        return ChatColor.GOLD + "Das Spiel beginnt in " + color + this.minigame.getGameTimer().getTime() + ChatColor.GOLD + " Sekunden!";
    }
}