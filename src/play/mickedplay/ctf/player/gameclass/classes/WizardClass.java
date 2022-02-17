package play.mickedplay.ctf.player.gameclass.classes;

import org.bukkit.Material;
import play.mickedplay.ctf.player.gameclass.ClassType;
import play.mickedplay.ctf.player.gameclass.GameClass;
import play.mickedplay.ctf.player.gameclass.GameClassManager;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

/**
 * Created by mickedplay on 26.04.2016 at 20:41 UTC+1.
 * You are not allowed to remove this comment.
 */
public class WizardClass extends GameClass {

    public WizardClass(GameClassManager gameClassManager) {
        super(gameClassManager, ClassType.WIZARD);
        this.addArmor(0, null);
        this.addArmor(1, new ItemBuilder(Material.LEATHER_CHESTPLATE).setUnbreakable());
        this.addArmor(2, new ItemBuilder(Material.LEATHER_LEGGINGS).setUnbreakable());
        this.addArmor(3, null);
        this.addItem(new ItemBuilder(Material.BLAZE_ROD).withName("§bMagical Wand").setUnbreakable());
    }
}