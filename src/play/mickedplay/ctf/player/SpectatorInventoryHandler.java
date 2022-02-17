package play.mickedplay.ctf.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.event.GameEvent;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 28.05.2016 at 19:25 CEST.
 * You are not allowed to remove this comment.
 */
public class SpectatorInventoryHandler extends GameEvent {

    private CaptureTheFlag ctf;
    private List<ItemStack> spectatorItems;
    private String[] teamBlockLore;

    public SpectatorInventoryHandler(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
        this.teamBlockLore = new String[]{"", "", "§7Linksklick: Teleport zu Spawn", "§7Rechtsklick: Teleport zur Flagge"};
        this.initializeSpectatorItems();
    }

    /*
        Initalisiert und erstellt eine Liste mit ItemStacks für das Zuschauer-Inventar
        TODO: Starten bei Slot neun, da die ersten neun Slots die vierte Inventarreihe sind
     */
    public SpectatorInventoryHandler initializeSpectatorItems() {
        this.spectatorItems = new ArrayList<>();
        this.ctf.getTeamManager().getTeams().forEach(team -> {
            this.spectatorItems.add(this.getTeamBlock(team));
            team.getOriginalPlayers().forEach(bwPlayer -> this.spectatorItems.add(this.getPlayerHead(team, bwPlayer)));
            while (spectatorItems.size() % 9 != 0) {
                spectatorItems.add(new ItemStack(Material.AIR));
            }
        });
        return this;
    }

    /*
       Füllt das Spielerinventar mit den Items
     */
    public void setInventory(CTFPlayer ctfPlayer) {
        ItemStack[] itemArray = new ItemStack[this.spectatorItems.size()];
        for (int i = 0; i < itemArray.length; i++) {
            itemArray[i] = this.spectatorItems.get(i);
        }
        ctfPlayer.getInventory().setContents(itemArray);
        ctfPlayer.updateInventory();
    }

    /*
        Erstellt Team-Blöcke
     */
    private ItemStack getTeamBlock(Team team) {
        CTFPlayer capturer = team.getCurrentCapturer();
        this.teamBlockLore[0] = "§7Flaggenhalter: " + (capturer == null ? "§8niemand" : capturer.getDisplayName());
        return new ItemBuilder(Material.STAINED_CLAY).withDamage(team.getData().getBlockMetaId()).withName(team.getDisplayName()).withLore(teamBlockLore).build();
    }

    /*
        Creates player heads
     */
    private ItemStack getPlayerHead(Team team, CTFPlayer ctfPlayer) {
        String name = (team.getPlayers().contains(ctfPlayer) ? "" + team.getChatColor() : "§8§m") + ctfPlayer.getName();
        return new ItemBuilder(Material.SKULL_ITEM).withName(ctfPlayer.hasEnemyFlag() ? ctfPlayer.getTeamOfStolenFlag().getChatColor() + "\u2691 " + name : name).setSkullOwner(ctfPlayer.getName()).withLore("§7Linksklick zum Teleportieren.", "§7§mRechtsklick zum Zuschauen").build();
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        CTFPlayer ctfPlayer = this.ctf.getCTFPlayer((Player) e.getWhoClicked());
        if (ctfPlayer.isSpectator()) {
            if (e.getInventory().getName().equals("container.crafting")) {
                if (e.getCurrentItem() != null) {
                    ItemStack itemStack = e.getCurrentItem();
                    ClickType clickType = e.getClick();
                    if (itemStack.getType() == Material.SKULL_ITEM) {
                        String itemName = itemStack.getItemMeta().getDisplayName();
                        if (ctfPlayer.hasEnemyFlag()) {
                            itemName = itemName.substring(2);
                        }
                        Player targetPlayer = Bukkit.getPlayer(ChatColor.stripColor(itemName.contains("\u2691") ? itemName.substring(4) : itemName));
                        if (ctfPlayer.getPlayer().equals(targetPlayer)) {
                            ctfPlayer.sendMessage("§cDu kannst nicht nicht selber teleportieren!");
                        } else {
                            if (clickType == ClickType.LEFT) {
                                ctfPlayer.teleport(targetPlayer.getLocation());
                            } else if (clickType == ClickType.RIGHT) {
//                                PlayerUtils.setPlayerCamera(ctfPlayer.getPlayer(), targetPlayer);
//                                ctfPlayer.teleport(targetPlayer.getLocation());
//                                ctfPlayer.sendPlayerViewTitle(targetPlayer);
//                                this.ctfPlayer.getBWPlayer(targetPlayer).setSelfWatchingPlayer(bwPlayer);
                            }
                            ctfPlayer.closeInventory();
                        }
                    } else if (itemStack.getType() == Material.STAINED_CLAY) {
                        for (Team team : this.ctf.getTeamManager().getTeams()) {
                            if (itemStack.getItemMeta().getDisplayName().equals(team.getDisplayName())) {
                                if (clickType == ClickType.LEFT) {
                                    ctfPlayer.teleport(team.getSpawnLocation());
                                } else if (clickType == ClickType.RIGHT) {
                                    ctfPlayer.teleport(team.getFlagCenter());
                                }
                                ctfPlayer.closeInventory();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}