package play.mickedplay.ctf.shop.tasks;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.misc.Helper;
import play.mickedplay.gameapi.utilities.game.Cardinal;

import java.util.List;

/**
 * Created by mickedplay on 23.06.2016 at 13:31.
 * You are not allowed to remove this comment.
 */
public class TeamCircleHealerEffect extends BukkitRunnable {

    private TeamCircleHealer teamCircleHealer;
    private List<Location> effectLocations;

    private int currentIndex, currentIndexInverted;

    public TeamCircleHealerEffect(TeamCircleHealer teamCircleHealer) {
        this.teamCircleHealer = teamCircleHealer;
        this.effectLocations = Helper.getCircle(Cardinal.DOWN, this.teamCircleHealer.getHealCenter(), GameSettings.CAKE_HEAL_RADIUS, GameSettings.CAKE_EFFECT_PARTICLE_COUNT);
        this.currentIndex = 0;
        this.currentIndexInverted = this.effectLocations.size() - 1;
        this.runTaskTimerAsynchronously(teamCircleHealer.getCTF().getPlugin(), 0L, 1L);
    }

    @Override
    public void run() {
        ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), this.effectLocations.get(this.currentIndex), Color.PURPLE, 50D, 0F, 0F, 0F, 1F, 20);
        ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), this.effectLocations.get(this.currentIndex).clone().add(0, 0.5, 0), Color.PURPLE, 50D, 0F, 0F, 0F, 1F, 20);
        ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), this.effectLocations.get(this.currentIndexInverted), Color.FUCHSIA, 50D, 0F, 0F, 0F, 1F, 20);
        ParticleEffect.REDSTONE.display(new ParticleEffect.ItemData(Material.WOOL, (byte) 0), this.effectLocations.get(this.currentIndexInverted).clone().add(0, 0.5, 0), Color.FUCHSIA, 50D, 0F, 0F, 0F, 1F, 20);
        this.currentIndex++;
        if (this.currentIndex >= this.effectLocations.size()) {
            this.currentIndex = 0;
        }
        this.currentIndexInverted--;
        if (this.currentIndexInverted < 0) {
            this.currentIndexInverted = this.effectLocations.size() - 1;
        }
    }
}