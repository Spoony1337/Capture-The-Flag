package play.mickedplay.ctf;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mickedplay on 30.04.2016 at 21:08 CEST.
 * You are not allowed to remove this comment.
 */
public class TeamChooser implements Listener {

    private CaptureTheFlag ctf;
    private ItemStack teamChooseItem;
    private Inventory teamInventory;
    private HashMap<ItemStack, Team> voteItems;

    public TeamChooser(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.voteItems = new HashMap<>();
        this.teamChooseItem = new ItemBuilder(this.ctf.getActionManager().getRandomSelectItemStack()).withName("§7Choose your team...").build();
        this.teamInventory = Bukkit.createInventory(null, 27, "Choose a team...");
        Bukkit.getPluginManager().registerEvents(this, ctf.getPlugin());
    }

    public void setupVoteInventory(List<Team> teamList) {
        this.teamInventory.setItem(13, this.ctf.getActionManager().getRandomSelectItemStack());

        int slot = 7;
        for (Team team : teamList) {
            slot += 4;
            ItemStack voteItem = new ItemBuilder(Material.WOOL).withName(team.getDisplayName()).withDamage(team.getFlagBlockColor()).build();
            this.voteItems.put(voteItem, team);
            this.teamInventory.setItem(slot, voteItem);
        }
    }

    public Inventory getInventory() {
        return teamInventory;
    }

    public Team getTeamByItem(ItemStack itemStack) {
        return this.voteItems.get(itemStack);
    }

    public ItemStack getTeamChooseItem() {
        return teamChooseItem;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().equals(this.teamInventory.getName()) && e.getCurrentItem() != null) {
                e.setCancelled(true);
                ItemStack itemStack = e.getCurrentItem();
                if (itemStack.getType() != Material.AIR) {
                    CTFPlayer ctfPlayer = this.ctf.getCTFPlayer((Player) e.getWhoClicked());
                    if (itemStack.getType() == Material.SKULL_ITEM) {
                        ctfPlayer.resetTeam();
                        ctfPlayer.setHelmet(null);
                        ctfPlayer.sendActionBar("§eYou're now in a §frandom team§e!");
                        ctfPlayer.setPlayerListName("§7" + ctfPlayer.getName());
                        this.setupVoted(ctfPlayer, itemStack);
                    } else if (itemStack.getType() == Material.WOOL) {
                        Team team = this.ctf.getTeamChooser().getTeamByItem(itemStack);
                        if (team.getPlayers().contains(ctfPlayer)) {
                            ctfPlayer.sendMessage("§cYou're already in that team!");
                        } else if (team.getSize() < this.ctf.getTeamManager().getMaxTeamSize()) {
                            ctfPlayer.setTeam(team);
                            ctfPlayer.sendActionBar("§eYou're now in " + team.getDisplayName() + "§e!");
                            ctfPlayer.setPlayerListName(ctfPlayer.getDisplayName());
                            this.setupVoted(ctfPlayer, itemStack);
                        } else {
                            ctfPlayer.sendMessage("§cThis team is full!");
                        }
                    }
                    ctfPlayer.playSound(Sound.ORB_PICKUP);
                    ctfPlayer.closeInventory(); //EASY FİX
                }
            }
        }
    }

    private void setupVoted(CTFPlayer ctfPlayer, ItemStack itemStack) {
        ctfPlayer.setHelmet(itemStack);
    }
}