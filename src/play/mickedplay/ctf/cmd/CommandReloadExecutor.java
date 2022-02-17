package play.mickedplay.ctf.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import play.mickedplay.ctf.CTFPlugin;
import play.mickedplay.ctf.CaptureTheFlag;

/**
 * Created by mickedplay on 19.06.2016 at 15:35 UTC+1.
 * You are not allowed to remove this comment.
 */
public class CommandReloadExecutor implements CommandExecutor {

    private CaptureTheFlag ctf;

    public CommandReloadExecutor(CTFPlugin ctfPlugin) {
        this.ctf = ctfPlugin.getCTF();
        ctfPlugin.getCommand("r").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("r")) {
            sender.sendMessage("Der Server wird neu geladen blablabla...");
            Bukkit.getScheduler().runTaskLater(this.ctf.getPlugin(), () -> Bukkit.reload(), 60L);
        }
        return false;
    }
}