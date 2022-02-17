package play.mickedplay.ctf.game;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.utilities.builder.ItemBuilder;

import java.util.HashMap;

/**
 * Created by mickedplay on 19.06.2016 at 20:44 CEST.
 * You are not allowed to remove this comment.
 */

public class ActionManager {

    private CaptureTheFlag ctf;
    private HashMap<Block, Team> mines, cakes;

    private ItemStack changeClassItemStack, randomSelectItemStack;

    private Team winningTeam;

    public ActionManager(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.mines = new HashMap<>();
        this.cakes = new HashMap<>();
        this.changeClassItemStack = new ItemBuilder(Material.COMPASS).withName("§7Klasse wechseln").build();
        this.randomSelectItemStack = new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("MHF_Question").withName("§fZufällig").build();
    }

    public void addMine(Block block, Team team) {
        this.mines.put(block, team);
    }

    public HashMap<Block, Team> getMines() {
        return mines;
    }

    public Team getTeamByMine(Block block) {
        return this.mines.get(block);
    }

    public void addCake(Block block, Team team) {
        this.cakes.put(block, team);
    }

    public HashMap<Block, Team> getCakes() {
        return mines;
    }

    public Team getTeamByCake(Block block) {
        return this.cakes.get(block);
    }

    public ItemStack getChangeClassItemStack() {
        return changeClassItemStack;
    }

    public ItemStack getRandomSelectItemStack() {
        return randomSelectItemStack;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Team winningTeam) {
        this.winningTeam = winningTeam;
    }
}