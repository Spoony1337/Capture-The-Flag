package play.mickedplay.ctf.player.gameclass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.player.gameclass.classes.*;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by mickedplay on 26.04.2016 at 22:04 UTC+1.
 * You are not allowed to remove this comment.
 */
public class GameClassManager {

    private HashMap<ItemStack, GameClass> gameClasses;
    private HashMap<ClassType, GameClass> classTypes;
    private ItemStack changeClassItem;

    public GameClassManager() {
        this.gameClasses = new HashMap<>();
        this.classTypes = new HashMap<>();
        this.changeClassItem = new ItemBuilder(Material.COMPASS).withName("§7Wähle eine Klasse...").build();

        new NinjaClass(this);
        new WarriorClass(this);
        new ArcherClass(this);
        new PitcherClass(this);
        new WizardClass(this);
    }

    public HashMap<ItemStack, GameClass> getGameClasses() {
        return gameClasses;
    }

    public HashMap<ClassType, GameClass> getClassTypes() {
        return classTypes;
    }

    public GameClass getGameClass(ItemStack itemStack) {
        return this.gameClasses.get(itemStack);
    }

    public GameClass getRandomGameClass() {
        List<GameClass> gameClassList = new ArrayList<>(gameClasses.values());
        return gameClassList.get(new Random().nextInt(gameClassList.size()));
    }

    public ItemStack getChangeClassItem() {
        return changeClassItem;
    }
}