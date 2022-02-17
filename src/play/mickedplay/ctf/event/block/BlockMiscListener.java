package play.mickedplay.ctf.event.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 25.04.2016 at 14:20 CEST.
 * * You are not allowed to remove this comment.
 */
public class BlockMiscListener extends GameEvent {

    public BlockMiscListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
    }

    @EventHandler
    public void leavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }
}