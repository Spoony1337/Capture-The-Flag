package play.mickedplay.ctf.player.gameclass;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mickedplay on 26.04.2016 at 20:41 UTC+1.
 * You are not allowed to remove this comment.
 */
public class GameClass {

    private String name;
    private HashMap<Integer, ItemStack> armorContents;
    private List<ItemStack> content;
    private HashMap<PotionEffectType, Integer> potionEffects;
    private int slot;
    private ItemStack changeClassItem;

    public GameClass(GameClassManager gameClassManager, ClassType classType) {
        this.armorContents = new HashMap<>();
        this.content = new ArrayList<>();
        this.potionEffects = new HashMap<>();
        this.name = classType.getName();
        this.slot = classType.getSlot();
        this.changeClassItem = classType.getChangeClassItem();
        gameClassManager.getGameClasses().put(this.changeClassItem, this);
        gameClassManager.getClassTypes().put(classType, this);
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, ItemStack> getArmorContents() {
        return armorContents;
    }

    public void addArmor(int slot, ItemBuilder itemBuilder) {
        this.armorContents.put(slot, itemBuilder == null ? null : itemBuilder.hideFlags(ItemFlag.values()).build());
    }

    public List<ItemStack> getContent() {
        return content;
    }

    public void addItem(ItemBuilder itemBuilder) {
        if (itemBuilder != null) {
            this.content.add(itemBuilder.hideFlags(ItemFlag.values()).build());
        }
    }

    public HashMap<PotionEffectType, Integer> getPotionEffects() {
        return potionEffects;
    }

    public void addPotionEffect(PotionEffectType potionEffectType, int amplifier) {
        this.potionEffects.put(potionEffectType, amplifier);
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getChangeClassItem() {
        return changeClassItem;
    }
}