package play.mickedplay.gameapi.utilities.builder;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private Material material;
    private String name, skullOwner;
    private short damage;
    private int amount;
    private String[] lore;
    private Color color;
    private MaterialData data;
    private Map<Enchantment, Integer> enchantment;
    private ItemFlag[] itemFlags;
    private boolean glow, unbreakable;

    public ItemBuilder(Material material) {
        this.material = material;
        this.name = null;
        this.damage = 0;
        this.amount = 1;
        this.lore = new String[]{};
        this.itemFlags = new ItemFlag[]{};

        this.enchantment = new HashMap<>();
        this.data = null;
        this.color = null;
        this.unbreakable = false;
    }

    public ItemBuilder(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.damage = itemStack.getDurability();
        this.amount = itemStack.getAmount();
        this.data = itemStack.getData();
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            this.name = itemMeta.getDisplayName();
            List<String> itemMetaLore = itemMeta.getLore();
            this.lore = itemMeta.hasLore() ? itemMetaLore.toArray(new String[itemMetaLore.size()]) : new String[]{};
            this.enchantment = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
                this.enchantment.put(entry.getKey(), entry.getValue());
            }
            this.itemFlags = itemMeta.getItemFlags().toArray(new ItemFlag[0]);
            this.unbreakable = itemMeta.spigot().isUnbreakable();
            if (itemMeta instanceof LeatherArmorMeta) this.color = ((LeatherArmorMeta) itemMeta).getColor();
            else if (itemMeta instanceof SkullMeta) this.skullOwner = ((SkullMeta) itemMeta).getOwner();
        }
    }

    public ItemBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
        this.enchantment.put(enchantment, level);
        return this;
    }

    public ItemBuilder withData(MaterialData data) {
        this.data = data;
        return this;
    }

    public ItemBuilder withDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder withGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public ItemBuilder hideFlags(ItemFlag... flags) {
        this.itemFlags = flags;
        return this;
    }

    public ItemBuilder setUnbreakable() {
        this.unbreakable = true;
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwner) {
        this.skullOwner = skullOwner;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        boolean isLeatherArmor = false;
        boolean isSkull = false;

        // @formatter:off
        switch (itemStack.getType()) {
            case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: isLeatherArmor = true; break;
            case SKULL_ITEM: isSkull = true;
        }
        // @formatter:on

        if (isLeatherArmor) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            if (name != null) leatherArmorMeta.setDisplayName(name);
            if (lore != null) leatherArmorMeta.setLore(Arrays.asList(lore));
            if (itemFlags != null) leatherArmorMeta.addItemFlags(itemFlags);
            if (this.color != null) leatherArmorMeta.setColor(this.color);
            leatherArmorMeta.spigot().setUnbreakable(this.unbreakable);
            itemStack.setItemMeta(leatherArmorMeta);
        } else if (isSkull) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            this.damage = 3;
            if (name != null) skullMeta.setDisplayName(name);
            if (lore != null) skullMeta.setLore(Arrays.asList(lore));
            if (itemFlags != null) skullMeta.addItemFlags(itemFlags);
            if (this.skullOwner != null) skullMeta.setOwner(this.skullOwner);
            skullMeta.spigot().setUnbreakable(this.unbreakable);
            itemStack.setItemMeta(skullMeta);
        } else {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (name != null) itemMeta.setDisplayName(name);
            if (lore != null) itemMeta.setLore(Arrays.asList(lore));
            if (itemFlags != null) itemMeta.addItemFlags(itemFlags);
            itemMeta.spigot().setUnbreakable(this.unbreakable);
            itemStack.setItemMeta(itemMeta);
        }

        if (data != null) itemStack.setData(data);
        if (enchantment != null) itemStack.addUnsafeEnchantments(enchantment);
        itemStack.setAmount(amount);
        itemStack.setDurability(damage);
//        if (this.glow) ItemGlower.addGlow(itemStack);
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public void give(Player player) {
        player.getInventory().addItem(this.build());
    }

    public void give(Player player, int slot) {
        player.getInventory().setItem(slot, build());
    }

    public void setHelmet(Player player) {
        player.getInventory().setHelmet(this.build());
    }

    public void setChestplate(Player player) {
        player.getInventory().setChestplate(this.build());
    }

    public void setLeggings(Player player) {
        player.getInventory().setLeggings(this.build());
    }

    public void setBoots(Player player) {
        player.getInventory().setBoots(this.build());
    }

    // @formatter:off
    public void setArmor(Player player, int slot) {
        switch (slot) {
            case 0: this.setHelmet(player); break;
            case 1: this.setChestplate(player); break;
            case 2: this.setLeggings(player); break;
            case 3: this.setBoots(player);
        }
    }
    // @formatter:on


    public Material getMaterial() {
        return material;
    }
}