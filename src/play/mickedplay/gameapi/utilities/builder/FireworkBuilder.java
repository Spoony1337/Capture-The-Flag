package play.mickedplay.gameapi.utilities.builder;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder {

    private final Location location;
    private int power;
    private boolean flicker, trail;
    private FireworkEffect.Type type;
    private Color[] color, fade;

    public FireworkBuilder(Location location) {
        this.location = location;
    }

    public FireworkBuilder withPower(int power) {
        this.power = power;
        return this;
    }

    public FireworkBuilder withFlicker() {
        this.flicker = true;
        return this;
    }

    public FireworkBuilder withTrail() {
        this.trail = true;
        return this;
    }

    public FireworkBuilder withType(FireworkEffect.Type type) {
        this.type = type;
        return this;
    }

    public FireworkBuilder withColor(Color... color) {
        this.color = color;
        return this;
    }

    public FireworkBuilder withFade(Color... fade) {
        this.fade = fade;
        return this;
    }

    public Firework build() {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        if (!(power < 0 || power > 127)) meta.setPower(power);
        if (flicker) builder.withFlicker();
        if (trail) builder.withTrail();
        if (type != null) builder.with(type);
        if (color != null) builder.withColor(color);
        if (fade != null) builder.withFade(fade);
        meta.addEffects(builder.build());
        firework.setFireworkMeta(meta);
        return firework;
    }
}