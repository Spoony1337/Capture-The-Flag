package play.mickedplay.gameapi.utilities.builder;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class ItemGlower extends EnchantmentWrapper {
//    private static Enchantment glow;
//
    private ItemGlower(int id) {
        super(Integer.MAX_VALUE);
    }

//    private static Enchantment getGlow() {
//        if (glow != null) return glow;
//        Enchantment enchantment = Enchantment.getById(255);
//        if (enchantment instanceof ItemGlower) {
//            return glow = enchantment;
//        }
//        try {
//            Field field = Enchantment.class.getDeclaredField("acceptingNew");
//            field.setAccessible(true);
//            field.set(null, true);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        glow = new ItemGlower(255);
//        for (Enchantment e : Enchantment.values()) {
//            if (e.equals(glow)) return glow;
//        }
//        Enchantment.registerEnchantment(glow);
//        return glow;
//    }
//
//    public static void addGlow(ItemStack item) {
//        Enchantment enchantment = getGlow();
//        if (item.containsEnchantment(enchantment)) item.removeEnchantment(enchantment);
//        item.addEnchantment(enchantment, 1);
//    }
//
//    @Override
//    public boolean canEnchantItem(ItemStack itemStack) {
//        return true;
//    }
//
//    @Override
//    public boolean conflictsWith(Enchantment enchantment) {
//        return false;
//    }
//
//    @Override
//    public EnchantmentTarget getItemTarget() {
//        return null;
//    }
//
//    @Override
//    public int getMaxLevel() {
//        return 10;
//    }
//
//    @Override
//    public String getName() {
//        return "Glow";
//    }
//
//    @Override
//    public int getStartLevel() {
//        return 1;
//    }
}