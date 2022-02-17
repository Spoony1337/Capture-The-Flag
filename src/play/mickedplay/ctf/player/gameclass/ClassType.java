package play.mickedplay.ctf.player.gameclass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 09.08.2016 at 08:30 UTC+1.
 * You are not allowed to remove this comment.
 */
public enum ClassType {
    // @formatter:off
    ARCHER("Archer", 2, Material.BOW, new String[]{"§e\u25BA §7Leather Chestplate", "§e\u25BA §7Leather Leggings", "§e\u25BA §7Leather Boots", "§e\u25BA §7Wooden Axe", "§5\u25BA §7Bow (Knockback I)", "§c\u25BA §73 Arrows (rechargeable up to 16)"}),
    NINJA("Ninja", 3, Material.POTION, new String[]{"§e\u25BA §7Leather Leggings", "§5\u25BA §7Leather Boots (Feather Falling II)", "§e\u25BA §7Gold Sword", "§a\u25BA §7Speed I", "§c\u25BA §7Invisibility (only active when sneaking)"}),
    PITCHER("Pitcher", 5, Material.STICK, new String[]{"§e\u25BA §7Chainmail Chestplate", "§e\u25BA §7Chainmail Leggings", "§5\u25BA §7Leather Boots (Feather Falling II)", "§5\u25BA §7Baseball Bat (Knockback II)", "§a\u25BA §7Speed I", "§c\u25BA §710% chance to damage " + GameSettings.PITCHER_SPECIAL_DAMAGE + " hearts"}),
    WARRIOR("Warrior", 6, Material.STONE_SWORD, new String[]{"§e\u25BA §7Chainmail Chestplate", "§e\u25BA §7Leather Leggings", "§e\u25BA §7Stone Sword", "§c\u25BA §725% chance to do double damage"}),
    WIZARD("Wizard", 4, Material.BLAZE_ROD, new String[]{"§e\u25BA §7Leather Chestplate", "§e\u25BA §7Leather Leggings", "§c\u25BA §7Magical Wand (Right Click to use)"});
    // @formatter:on

    private String name;
    private int slot;
    private Material material;
    private short durability;
    private String[] description;

    ClassType(String name, int slot, Material material, String[] description) {
        this(name, slot, material, (short) 0, description);
    }

    ClassType(String name, int slot, Material material, short durability, String[] description) {
        this.name = name;
        this.slot = slot;
        this.material = material;
        this.durability = durability;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getChangeClassItem() {
        return new ItemBuilder(this.material).withName("§7" + this.name).withDamage(this.durability).withLore(this.description).hideFlags(ItemFlag.values()).build();
    }
}