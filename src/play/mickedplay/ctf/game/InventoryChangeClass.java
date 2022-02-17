package play.mickedplay.ctf.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.stages.Ingame;
import play.mickedplay.ctf.player.CTFPlayer;
import play.mickedplay.ctf.player.gameclass.GameClass;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 27.04.2016 at 16:08 UTC+1.
 * You are not allowed to remove this comment.
 */
public class InventoryChangeClass implements Listener {

    private CaptureTheFlag ctf;
    private Inventory inventory;

    public InventoryChangeClass(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.inventory = Bukkit.createInventory(null, 9, "Choose a class...");
        String[] description = new String[]{"§e\u25BA §7Normal Item", "§5\u25BA §7Enchanted Item", "§a\u25BA §7Status effect", "§c\u25BA §7Special attack"};
        this.inventory.setItem(0, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("MHF_Question").withName("§fRandom").withLore(description).build());
        ctf.getGameClassManager().getGameClasses().values().forEach(gameClass -> this.inventory.setItem(gameClass.getSlot(), gameClass.getChangeClassItem()));
        Bukkit.getPluginManager().registerEvents(this, ctf.getPlugin());
    }

    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            Inventory inventory = e.getClickedInventory();
            if (inventory.getName().equals(this.inventory.getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getType() != Material.AIR) {
                        CTFPlayer ctfPlayer = this.ctf.getCTFPlayer((Player) e.getWhoClicked());
                        GameClass gameClass = this.ctf.getGameClassManager().getGameClass(e.getCurrentItem());
                        if (gameClass == null) {
                            gameClass = this.ctf.getGameClassManager().getRandomGameClass();
                        }
                        if (this.ctf.getGameStage() instanceof Ingame) {
                            ctfPlayer.setupGameClass();
                        }
                        ctfPlayer.setGameClass(gameClass);
                        ctfPlayer.sendMessage("§eClass two §7" + gameClass.getName() + "§e Changed.");
                        ctfPlayer.closeInventory();
                    }
                }
            }
        }
    }
}