package play.mickedplay.ctf.team.task;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.misc.Helper;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.utilities.game.Cardinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 25.04.2016 at 18:57 UTC+1.
 * You are not allowed to remove this comment.
 */
public class FlagEffectTask extends BukkitRunnable {

    private int delay = 200, period = 2, particleLocationCount = 54;
    private double circleRadius = 0.8;

    private Block flagBlock;
    private Location effectCenter;
    private Color effectColor;

    private List<Location> particleLocations;

    public FlagEffectTask(Team team, boolean isRefresh) {
        this.effectColor = team.getData().getColor();
        this.flagBlock = team.getFlagEffectLocation().getBlock();
        this.effectCenter = team.getFlagCenter();
        Bukkit.getScheduler().runTaskLater(team.getTeamManager().getCaptureTheFlag().getPlugin(), () -> this.flagBlock.setType(Material.PACKED_ICE), 0L);
        Bukkit.getScheduler().runTaskLater(team.getTeamManager().getCaptureTheFlag().getPlugin(), () -> {
            flagBlock.setType(Material.WOOL);
            flagBlock.setData(team.getFlagBlockColor());
            runTaskTimerAsynchronously(team.getTeamManager().getCaptureTheFlag().getPlugin(), 0, period);
        }, isRefresh ? this.delay : 100);
        this.generateParticleLocations();
    }

    @Override
    public void run() {
        this.particleLocations.forEach(location -> ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), location, this.effectColor, 50D, 0F, 0F, 0F, 1F, 20));
    }

    /**
     * Fügt der Partikel-Positions-Liste für jede Himmelsrichtung die entsprechend ermittelten Positionen hinzu
     */
    private void generateParticleLocations() {
        this.particleLocations = new ArrayList<>();
        for (Cardinal cardinal : Cardinal.values())
            this.particleLocations.addAll(this.fetchParticleLocations(cardinal));
    }

    /**
     * Gibt eine Liste mit Positionen zurück, die auf einem Kreisrand mit angegebenen Radius und Partikelaufkommen beinhaltet.
     */
    // @formatter:off
    private List<Location> fetchParticleLocations(Cardinal cardinal) {
        double x = this.effectCenter.getX(), y = this.effectCenter.getY(), z = this.effectCenter.getZ();
        switch (cardinal) {
            case EAST: x += this.circleRadius; break;
            case WEST: x -= this.circleRadius; break;
            case SOUTH: z += this.circleRadius; break;
            case NORTH: z -= this.circleRadius; break;
            case UP: y += this.circleRadius; break;
            case DOWN: y -= this.circleRadius;
        }
        return Helper.getCircle(cardinal, new Location(this.effectCenter.getWorld(), x, y, z), this.circleRadius * 2, this.particleLocationCount);
    }
    // @formatter:on
}