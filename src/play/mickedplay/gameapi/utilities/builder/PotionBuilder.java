package play.mickedplay.gameapi.utilities.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mickedplay on 16.04.2016 at 00:32 UTC+1.
 */
public class PotionBuilder {

    private String name;
    private short damage;
    private int amount;
    private String[] lore;
    private Map<Enchantment, Integer> enchantment;
    private HashMap<PotionEffect, Boolean> effects;
    private boolean glow, unbreakable;
    private PotionEffectType mainEffect;
    private ItemFlag[] itemFlags;

    public PotionBuilder() {
        this.damage = 0;
        this.amount = 1;
        this.lore = new String[]{};
        this.itemFlags = new ItemFlag[]{};
        this.enchantment = new HashMap<>();
        this.effects = new HashMap<>();
    }

    public PotionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PotionBuilder withEffect(PotionEffect potionEffect, boolean visible) {
        this.effects.put(potionEffect, visible);
        return this;
    }

    public PotionBuilder withMainEffect(PotionEffectType mainEffect) {
        this.mainEffect = mainEffect;
        return this;
    }

    public PotionBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public PotionBuilder withLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public PotionBuilder withEnchantment(Enchantment enchantment, int level) {
        this.enchantment.put(enchantment, level);
        return this;
    }

    public PotionBuilder withDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public PotionBuilder withGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public PotionBuilder setUnbreakable() {
        this.unbreakable = true;
        return this;
    }

    public PotionBuilder hideItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

        for (Map.Entry<PotionEffect, Boolean> entry : this.effects.entrySet()) {
            potionMeta.addCustomEffect(entry.getKey(), entry.getValue());
        }

        if (mainEffect != null) potionMeta.setMainEffect(mainEffect);
        potionMeta.spigot().setUnbreakable(unbreakable);
        potionMeta.setDisplayName(name);
        potionMeta.setLore(Arrays.asList(lore));
        potionMeta.addItemFlags(itemFlags);
        itemStack.setAmount(amount);
        itemStack.setDurability(damage);
        itemStack.addUnsafeEnchantments(enchantment);
        itemStack.setItemMeta(potionMeta);
//        if (this.glow) ItemGlower.addGlow(itemStack);
        return itemStack;
    }
}