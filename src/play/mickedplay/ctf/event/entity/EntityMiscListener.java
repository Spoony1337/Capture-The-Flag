package play.mickedplay.ctf.event.entity;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.gameapi.event.GameEvent;

/**
 * Created by mickedplay on 16.05.2016 at 23:35 UTC+1.
 * You are not allowed to remove this comment.
 */
public class EntityMiscListener extends GameEvent {

    public EntityMiscListener(CaptureTheFlag ctf) {
        super(ctf.getPlugin());
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void entitySpawn(EntitySpawnEvent e) {
        e.setCancelled(!(e.getEntity() instanceof Item));
    }

    @EventHandler
    public void spawnerSpawn(SpawnerSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            e.getEntity().remove();
        }
    }
}