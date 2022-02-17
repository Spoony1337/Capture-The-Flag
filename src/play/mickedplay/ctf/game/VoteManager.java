package play.mickedplay.ctf.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.map.GameMap;
import play.mickedplay.ctf.map.MapManager;
import play.mickedplay.ctf.player.CTFPlayer;

/**
 * Created by mickedplay on 19.06.2016 at 17:31 UTC+1.
 * You are not allowed to remove this comment.
 */
public class VoteManager implements Listener {

    private MapManager mapManager;
    private Inventory inventory;

    public VoteManager(MapManager mapManager) {
        this.mapManager = mapManager;
        this.setupVotingInventory();
        Bukkit.getPluginManager().registerEvents(this, mapManager.getCTF().getPlugin());
    }

    private void setupVotingInventory() {
        this.inventory = Bukkit.createInventory(null, 27, "Stimme für eine Map ab...");
        int slot = 9;
        for (ItemStack voteItem : this.mapManager.getVoteItems().keySet()) {
            slot += 2;
            this.inventory.setItem(slot, voteItem);
        }
    }

    public Inventory getVotingInventory() {
        return inventory;
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(this.inventory.getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() != Material.AIR) {
                    ItemStack itemStack = e.getCurrentItem();
                    Material material = itemStack.getType();
                    if (material != Material.AIR) {
                        vote(this.mapManager.getCTF().getCTFPlayer((Player) e.getWhoClicked()), this.mapManager.getMapByVoteItem(itemStack));
                    }
                }
            }
        }
    }

    private void vote(CTFPlayer ctfPlayer, GameMap gameMap) {
        if (ctfPlayer.getVotedMap() != null) {
            this.mapManager.getGameMaps().get(ctfPlayer.getVotedMap()).removeVote();
        }
        gameMap.addVote();
        ctfPlayer.setVotedMap(gameMap.getWorldName());
        ctfPlayer.playSound(Sound.VILLAGER_YES);
        ctfPlayer.closeInventory();
    }
}
