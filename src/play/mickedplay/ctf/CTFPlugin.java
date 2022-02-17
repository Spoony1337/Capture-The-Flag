package play.mickedplay.ctf;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import play.mickedplay.ctf.cmd.CommandReloadExecutor;
import play.mickedplay.ctf.cmd.CommandTimerExecutor;
import play.mickedplay.ctf.event.block.BlockBreakListener;
import play.mickedplay.ctf.event.block.BlockMiscListener;
import play.mickedplay.ctf.event.block.BlockPlaceListener;
import play.mickedplay.ctf.event.entity.EntityDamageListener;
import play.mickedplay.ctf.event.entity.EntityMiscListener;
import play.mickedplay.ctf.event.entity.EntityShootBowListener;
import play.mickedplay.ctf.event.inventory.InventoryClickListener;
import play.mickedplay.ctf.event.player.*;
import play.mickedplay.ctf.event.world.WeatherChangeListener;

/**
 * Created by mickedplay on 24.04.2016 at 15:04 UTC+1.
 * You are not allowed to remove this comment.
 */
public class CTFPlugin extends JavaPlugin {

    public static CaptureTheFlag ctf;

    public void onEnable() {
        ctf = new CaptureTheFlag(this);

        // Event registration
        new AsyncPlayerChatListener(ctf);

        new BlockBreakListener(ctf);
        new BlockMiscListener(ctf);
        new BlockPlaceListener(ctf);

        new EntityDamageListener(ctf);
        new EntityMiscListener(ctf);
        new EntityShootBowListener(ctf);

        new InventoryClickListener(ctf);

        new PlayerDeathListener(ctf);
        new PlayerInteractAtEntityListener(ctf);
        new PlayerInteractListener(ctf);
        new PlayerJoinListener(ctf);
        new PlayerMiscListener(ctf);
        new PlayerMoveListener(ctf);
        new PlayerQuitListener(ctf);
        new PlayerRespawnListener(ctf);

        new WeatherChangeListener(ctf);

        this.registerCommands();
    }

    private void registerCommands() {
        new CommandTimerExecutor(this);
        new CommandReloadExecutor(this);
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer("§cServer has restarted."));
        ctf.getEffectManager().dispose();
        ctf.getActionManager().getMines().keySet().forEach(block -> block.setType(Material.AIR));
    }

    public CaptureTheFlag getCTF() {
        return ctf;
    }
}