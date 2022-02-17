package play.mickedplay.ctf.player;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.game.GameSettings;
import play.mickedplay.ctf.game.stages.DecisionPhase;
import play.mickedplay.ctf.player.achievements.AchievementManager;
import play.mickedplay.ctf.player.gameclass.GameClass;
import play.mickedplay.ctf.player.gameclass.classes.ArcherClass;
import play.mickedplay.ctf.player.gameclass.task.ArcherClassTask;
import play.mickedplay.ctf.team.Team;
import play.mickedplay.gameapi.game.stages.Lobby;
import play.mickedplay.gameapi.utilities.ActionBar;
import play.mickedplay.gameapi.utilities.player.MinigamePlayer;

import java.util.*;

/**
 * Created by mickedplay on 25.04.2016 at 14:34 CEST.
 * You are not allowed to remove this comment.
 */
public class CTFPlayer extends MinigamePlayer {

    private CaptureTheFlag ctf;
    private AchievementManager achievementManager;
    private GameStats gameStats;

    private Player player;
    private Team team, teamOfStolenFlag;
    private boolean isSpectator, hasEnemyFlag, dead;
    private FlagCatchTask flagCatchTask;
    private GameClass gameClass;
    private List<ItemStack> claimedShopItems;
    private ActionBar actionBarSpawnData;

    private ArcherClassTask archerClassTask;
    private int archerArrows;

    public CTFPlayer(CaptureTheFlag ctf, Player player) {
        super(ctf, player);
        this.ctf = ctf;
        this.player = player;
        this.achievementManager = new AchievementManager(this);
        this.isSpectator = false;
        this.hasEnemyFlag = false;
        this.claimedShopItems = new ArrayList<>();
        this.actionBarSpawnData = new ActionBar("");
        this.archerArrows = GameSettings.DEFAULT_ARCHER_ARROWS;
        this.gameStats = new GameStats(this);
        this.dead = false;

        if (this.ctf.getGameStage() instanceof Lobby) {
            player.teleport(ctf.getLobbyManager().getSpawn());
        } else if (this.ctf.getGameStage() instanceof DecisionPhase) {
            player.teleport(ctf.getLobbyManager().getDecisionSpawn());
        } else {
            player.teleport(ctf.getGameMap().getCenterLocation());
        }

        this.setPlayerListName("§7" + getName());
        this.clean(false);
    }

    /* ********************************************************************************************************************************************************************* */
    /* ************************************************************************** HELPER_METHODS *************************************************************************** */
    /* ********************************************************************************************************************************************************************* */

    public void addPotionEffect(int ticks, PotionEffectType potionEffectType) {
        this.addPotionEffect(ticks, potionEffectType, 9);
    }

    public void addPotionEffect(PotionEffectType potionEffectType, int duration) {
        this.addPotionEffect(Integer.MAX_VALUE, potionEffectType, duration);
    }

    public void addPotionEffect(int ticks, PotionEffectType potionEffectType, int amplifier) {
        this.player.addPotionEffect(new PotionEffect(potionEffectType, ticks, amplifier));
    }

    /* ********************************************************************************************************************************************************************* */
    /* ************************************************************************** GETTER & SETTER ************************************************************************** */
    /* ********************************************************************************************************************************************************************* */
    public CaptureTheFlag getCaptureTheFlag() {
        return this.ctf;
    }

    public AchievementManager getAchievementManager() {
        return achievementManager;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.removePlayer(this);
        }
        team.addPlayer(this);
        this.team = team;
    }

    public void resetTeam() {
        if (this.team != null) {
            this.team.removePlayer(this);
            this.team = null;
        }
    }

    public boolean hasEnemyFlag() {
        return this.hasEnemyFlag;
    }

    public Team getTeamOfStolenFlag() {
        return this.teamOfStolenFlag;
    }

    public void setTeamOfStolenFlag(Team teamOfStolenFlag) {
        this.teamOfStolenFlag = teamOfStolenFlag;
    }

    public FlagCatchTask getFlagCatchTask() {
        return flagCatchTask;
    }

    public void setFlagCatchTask(Team enemyTeam) {
        this.flagCatchTask = new FlagCatchTask(this, enemyTeam);
    }

    public int getArcherArrows() {
        return archerArrows;
    }

    public void setArcherArrows(int archerArrows) {
        this.archerArrows = archerArrows;
    }

    public ArcherClassTask getArcherClassTask() {
        return archerClassTask;
    }

    public void setArcherClassTask(ArcherClassTask archerClassTask) {
        this.archerClassTask = archerClassTask;
    }

    public void hasEnemyFlag(boolean hasEnemyFlag) {
        this.hasEnemyFlag = hasEnemyFlag;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    private void sendActionBar() {
        this.actionBarSpawnData.setMessage(team.getDisplayName() + " §r§l\u2759 §7" + gameClass.getName());
        this.actionBarSpawnData.sendTo(this.player);
    }

    public GameClass getGameClass() {
        return gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public GameStats getGameStats() {
        return this.gameStats;
    }

    public boolean isNearSpawn() {
        return this.player.getLocation().distance(this.team.getSpawnLocation()) <= GameSettings.TEAM_SPAWN_RADIUS;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getDisplayName() {
        return (this.team != null ? this.team.getChatColor() : ChatColor.GRAY) + getName();
    }

    /* @formatter:on */

    /* ********************************************************************************************************************************************************************* */
    /* ******************************************************************************* TASKS ******************************************************************************* */
    /* ********************************************************************************************************************************************************************* */
    public void cancelArcherClassTask() {
        if (this.archerClassTask != null) {
            this.archerClassTask.cancel();
            this.archerClassTask = null;
        }
    }

    public void cancelFlagCatchTask() {
        if (this.flagCatchTask != null) {
            this.flagCatchTask.cancel();
        }
    }

    public void prepareForGame() {
        this.teleport(team.getSpawnLocation());
        if (this.gameClass == null) this.gameClass = this.ctf.getGameClassManager().getRandomGameClass();
        this.setupGameClass();
        this.getInventory().setHeldItemSlot(0);
        this.playSound(Sound.ENDERDRAGON_GROWL);
    }

    public void setupGameClass() {
        this.clean(true);
        Bukkit.getScheduler().runTaskLater(this.ctf.getPlugin(), () -> {
            this.player.setGameMode(GameMode.SURVIVAL);
            // Armor items
            HashMap<Integer, ItemStack> classArmorContents = gameClass.getArmorContents();
//            this.player.getInventory().setHelmet(classArmorContents.get(0) != null ? this.modifyArmorColor(classArmorContents.get(0)) : null);
//            this.player.getInventory().setChestplate(classArmorContents.get(1) != null ? this.modifyArmorColor(classArmorContents.get(1)) : null);
//            this.player.getInventory().setLeggings(classArmorContents.get(2) != null ? this.modifyArmorColor(classArmorContents.get(2)) : null);
//            this.player.getInventory().setBoots(classArmorContents.get(3) != null ? this.modifyArmorColor(classArmorContents.get(3)) : null);
            this.getInventory().setHelmet(this.modifyArmorColor(classArmorContents.get(0)));
            this.getInventory().setChestplate(this.modifyArmorColor(classArmorContents.get(1)));
            this.getInventory().setLeggings(this.modifyArmorColor(classArmorContents.get(2)));
            this.getInventory().setBoots(this.modifyArmorColor(classArmorContents.get(3)));


            // Inventory items
            gameClass.getContent().forEach(itemStack -> getInventory().addItem(itemStack));

            // Claimed shop Items
            this.claimedShopItems.forEach(item -> this.getInventory().addItem(item));

            this.getInventory().setItem(7, this.ctf.getItemShop().getShopOpenInventoryItem());
            this.getInventory().setItem(8, this.ctf.getActionManager().getChangeClassItemStack());

            // PotionEffects
            for (Map.Entry<PotionEffectType, Integer> entry : gameClass.getPotionEffects().entrySet()) {
                addPotionEffect(Integer.MAX_VALUE, entry.getKey(), entry.getValue());
            }

            // Tasks
            this.cancelArcherClassTask();
            if (gameClass instanceof ArcherClass) {
                this.archerArrows = GameSettings.DEFAULT_ARCHER_ARROWS;
                this.archerClassTask = new ArcherClassTask(this);
            }

            // Miscellaneous
            setLevel(this.team.getExperience());
            this.player.setCompassTarget(this.team.getFlagCenter());
        }, 0L);
        this.sendActionBar();
    }

    public void addClaimedShopItem(ItemStack itemStack) {
        this.claimedShopItems.add(itemStack);
    }

    public void removeClaimedShopItem(ItemStack itemStack) {
        this.claimedShopItems.remove(itemStack);
    }

    private ItemStack modifyArmorColor(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        List<Material> armorMaterial = new ArrayList<>(Arrays.asList(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS));
        if (armorMaterial.contains(itemStack.getType())) {
            if (itemStack.hasItemMeta()) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
                leatherArmorMeta.setColor(this.team.getArmorColor());
                itemStack.setItemMeta(leatherArmorMeta);
            }
        }
        return itemStack;
    }

    public void checkForEnemyFlag() {
        if (this.hasEnemyFlag) {
            Bukkit.broadcastMessage("§eThe Flag of " + this.teamOfStolenFlag.getDisplayName() + " §ehas respawned!");
            this.teamOfStolenFlag.getPlayers().forEach(player -> player.playSound(Sound.NOTE_BASS_DRUM));
            this.teamOfStolenFlag.refreshFlagEffectTask(this);
            this.teamOfStolenFlag = null;
            this.flagCatchTask.cancel();
        }
    }

    public void clean(boolean classChange) {
        clearArmor();
        clearInventory();
        setExp(0);
        setLevel(0);
        setFoodLevel(20);
        setSaturation(11.5F);
        setFireTicks(0);
        setAdventureMode();
        removeAllPotionEffects();
        this.player.setAllowFlight(false);
        if (!classChange) setHealth(getMaxHealth());
    }

    public void setAsSpectator() {
        this.isSpectator = true;
        this.setHeldItemSlot(4);
        if (!(this.ctf.getGameStage() instanceof DecisionPhase)) {
            this.ctf.getSpectatorInventoryHandler().initializeSpectatorItems().setInventory(this);
        }
        Bukkit.getScheduler().runTaskLater(this.getMinigame().getPlugin(), this::setSpectatorMode, 5L);
    }

    public void sendRoundStats() {
        sendMessage("§8\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB");
        sendMessage("§8\u00BB §6Game statistics:");
        sendMessage("§8\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB");
        sendMessage("§8\u00BB §2Kills: §6" + this.gameStats.getKills());
        sendMessage("§8\u00BB §2Deaths: §6" + this.gameStats.getDeaths());
        sendMessage("§8\u00BB §2Flags stolen: §6" + this.gameStats.getStolenFlags());
        sendMessage("§8\u00BB §2Flags captured: §6" + this.gameStats.getCapturedFlags());
        sendMessage("§8\u00BB §2Gained exp: §6" + this.gameStats.getGainedExp());
        sendMessage("§8\u00BB §2Bought team items: §6" + this.gameStats.getBoughtTeamItems());
        sendMessage("§8\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB\u00BB");
    }
}