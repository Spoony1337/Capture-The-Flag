package play.mickedplay.ctf.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.game.ItemShop;
import play.mickedplay.gameapi.utilities.Utilities;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 05.05.2016 at 13:31 UTC+1.
 * You are not allowed to remove this comment.
 */
public class ShopItem {

    private int costs;
    private ItemStack itemStack;

    public ShopItem(ItemShop itemShop, int costs, Material material, String name, List<String> description) {
        ArrayList<String> lore = new ArrayList<>(description);
        lore.add(0, "§aExperience: §e§l" + costs + " Level");
        lore.add(1, "§aDescription:");

        this.itemStack = new ItemBuilder(material).withName("§6" + name).withLore(Utilities.toArray(lore)).build();
        this.costs = costs;
        itemShop.getInventory().addItem(this.itemStack);
        itemShop.getShopItems().put(this.itemStack, this);
    }

    public int getCosts() {
        return costs;
    }
}