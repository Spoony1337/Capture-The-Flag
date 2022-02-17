package play.mickedplay.ctf.event.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 04.07.2016 at 21:40 UTC+1.
 * You are not allowed to remove this comment.
 */
public class WeatherChangeListener extends GameEvent {

    private CaptureTheFlag ctf;

    public WeatherChangeListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
        this.ctf = ctf;
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
