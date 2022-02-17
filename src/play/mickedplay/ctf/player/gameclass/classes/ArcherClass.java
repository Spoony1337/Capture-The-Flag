package play.mickedplay.ctf.player.gameclass.classes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import play.mickedplay.ctf.player.gameclass.ClassType;
import play.mickedplay.ctf.player.gameclass.GameClass;
import play.mickedplay.ctf.player.gameclass.GameClassManager;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 26.04.2016 at 20:41 UTC+1.
 * You are not allowed to remove this comment.
 */
public class ArcherClass extends GameClass {

    public ArcherClass(GameClassManager gameClassManager) {
        super(gameClassManager, ClassType.ARCHER);
        this.addArmor(0, null);
        this.addArmor(1, new ItemBuilder(Material.LEATHER_CHESTPLATE).setUnbreakable());
        this.addArmor(2, new ItemBuilder(Material.LEATHER_LEGGINGS).setUnbreakable());
        this.addArmor(3, new ItemBuilder(Material.LEATHER_BOOTS).setUnbreakable());
        this.addItem(new ItemBuilder(Material.WOOD_AXE).setUnbreakable());
        this.addItem(new ItemBuilder(Material.BOW).withEnchantment(Enchantment.ARROW_KNOCKBACK, 1).setUnbreakable());
        this.addItem(new ItemBuilder(Material.ARROW).withAmount(3));
    }
}