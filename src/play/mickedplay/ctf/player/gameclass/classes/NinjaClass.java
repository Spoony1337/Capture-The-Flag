package play.mickedplay.ctf.player.gameclass.classes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;
import play.mickedplay.ctf.player.gameclass.ClassType;
import play.mickedplay.ctf.player.gameclass.GameClass;
import play.mickedplay.ctf.player.gameclass.GameClassManager;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 26.04.2016 at 20:41 CEST.
 * You are not allowed to remove this comment.
 */
public class NinjaClass extends GameClass {

    public NinjaClass(GameClassManager gameClassManager) {
        super(gameClassManager, ClassType.NINJA);
        this.addArmor(0, null);
        this.addArmor(1, null);
        this.addArmor(2, new ItemBuilder(Material.LEATHER_LEGGINGS).setUnbreakable());
        this.addArmor(3, new ItemBuilder(Material.LEATHER_BOOTS).withEnchantment(Enchantment.PROTECTION_FALL, 2).setUnbreakable());
        this.addItem(new ItemBuilder(Material.GOLD_SWORD).setUnbreakable());
        this.addPotionEffect(PotionEffectType.SPEED, 0);
    }
}