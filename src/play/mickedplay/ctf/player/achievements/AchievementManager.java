package play.mickedplay.ctf.player.achievements;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import play.mickedplay.ctf.player.CTFPlayer;

/**
 * Created by mickedplay on 02.07.2016 at 21:37 CEST.
 * You are not allowed to remove this comment.
 */
public class AchievementManager {

    private CTFPlayer ctfPlayer;
    private Inventory inventory;

    public AchievementManager(CTFPlayer ctfPlayer) {
        this.ctfPlayer = ctfPlayer;
        this.setupAchievementInventory();
    }

    private void setupAchievementInventory() {
        this.inventory = Bukkit.createInventory(null, 27, "Errungenschaften");
    }

    public Inventory getInventory() {
        return inventory;
    }
}