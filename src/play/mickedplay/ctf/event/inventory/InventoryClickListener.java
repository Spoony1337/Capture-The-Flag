package play.mickedplay.ctf.event.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 27.04.2016 at 15:55 UTC+1.
 * You are not allowed to remove this comment.
 */
public class InventoryClickListener extends GameEvent {

    private CaptureTheFlag ctf;

    public InventoryClickListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getClickedInventory().getName().equalsIgnoreCase("container.inventory")) {
                    e.setCancelled(true);
                }
            }
        }
    }
}