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
public class CommandTimerExecutor implements CommandExecutor {

    private CaptureTheFlag ctf;

    public CommandTimerExecutor(CTFPlugin ctfPlugin) {
        this.ctf = ctfPlugin.getCTF();
        ctfPlugin.getCommand("timer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("timer")) {
            if (this.ctf.getGameTimer().getTime() > 5) {
                int defaultTime = 5;
                if (args.length == 0) {
                    this.ctf.getGameTimer().setTime(defaultTime);
                } else {
                    try {
                        this.ctf.getGameTimer().setTime(Integer.parseInt(args[0]));
                    } catch (NumberFormatException exception) {
                        this.ctf.getGameTimer().setTime(defaultTime);
                    }
                }
            }
        }
        return false;
    }
}