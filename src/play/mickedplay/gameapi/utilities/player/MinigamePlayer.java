package play.mickedplay.gameapi.utilities.player;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import play.mickedplay.gameapi.game.Minigame;

import java.util.UUID;

/**
 * Created by mickedplay on 20.05.2016 at 08:07 UTC+1.
 */
public class MinigamePlayer {

    private Minigame minigame;
    private Player player;
    private String votedMap;

    public MinigamePlayer(Minigame minigame, Player player) {
        this.minigame = minigame;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }

    public void teleport(Location location) {
        this.player.teleport(location);
    }

    public void playSound(Sound sound) {
        playSound(sound, 1F, 1F);
    }

    public void setLevel(int level) {
        this.player.setLevel(level);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        this.player.playSound(this.player.getLocation(), sound, volume, pitch);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.player.getInventory().setItem(slot, itemStack);
    }

    public void addItem(ItemStack itemStack) {
        this.player.getInventory().addItem(itemStack);
    }

    public void setHeldItemSlot(int slot) {
        this.player.getInventory().setHeldItemSlot(slot);
    }

    public void setFireTicks(int fireTicks) {
        this.player.setFireTicks(fireTicks);
    }

    public void clearArmor() {
        this.player.getInventory().setArmorContents(null);
    }

    public void clearInventory() {
        this.player.getInventory().clear();
    }

    public void setGameMode(GameMode gameMode) {
        this.player.setGameMode(gameMode);
    }

    public void setAdventureMode() {
        this.player.setGameMode(GameMode.ADVENTURE);
    }

    public void setSpectatorMode() {
        this.player.setGameMode(GameMode.SPECTATOR);
    }

    public void setSurvivalMode() {
        this.player.setGameMode(GameMode.SURVIVAL);
    }

    public void setExp(int exp) {
        this.player.setExp(0);
    }

    public void setFoodLevel(int level) {
        this.player.setFoodLevel(level);
    }

    public void setSaturation(float saturation) {
        this.player.setSaturation(saturation);
    }

    public void removeAllPotionEffects() {
        this.player.getActivePotionEffects().forEach(effect -> this.player.removePotionEffect(effect.getType()));
    }

    public void closeInventory() {
        this.player.closeInventory();
    }

    public void updateInventory() {
        this.player.updateInventory();
    }

    public void setHelmet(ItemStack itemStack) {
        this.player.getInventory().setHelmet(itemStack);
    }

    public void setAllowFlight(boolean allowFlight) {
        this.player.setAllowFlight(allowFlight);
    }

    public void kickPlayer(String message) {
        this.player.kickPlayer(message);
    }

    public void setPlayerListName(String name) {
        this.player.setPlayerListName(name);
    }

    public void removeItemInMainHand() {
        this.player.getInventory().remove(this.player.getInventory().getItemInHand());
    }

    public void removeItemInOffHand() { //unused method
        this.player.getInventory().remove(this.player.getInventory().getItemInHand());
    }

    public void setVelocity(Vector vector) {
        this.player.setVelocity(vector);
    }

    public void sendActionBar(String message) {
        PlayerUtilities.sendActionBar(this.player, message);
    }

    public boolean hasItem(ItemStack itemStack) {
        return this.player.getInventory().contains(itemStack);
    }

    public String getName() {
        return player.getName();
    }

    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public double getX() {
        return player.getLocation().getX();
    }

    public double getY() {
        return player.getLocation().getY();
    }

    public double getZ() {
        return player.getLocation().getZ();
    }

    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    public ItemStack getItemInMainHand() {
        return player.getInventory().getItemInHand();
    }

    public ItemStack getItemInOffHand() {
        return player.getInventory().getItemInHand();
    }

    public double getHealth() {
        return player.getHealth();
    }

    public void setHealth(double health) {
        this.player.setHealth(health);
    }

    public double getMaxHealth() {
        return player.getMaxHealth();
    }

    public String getVotedMap() {
        return votedMap;
    }

    public void setVotedMap(String votedMap) {
        this.votedMap = votedMap;
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public World getWorld() {
        return player.getWorld();
    }

    public double distance(Location location) {
        return player.getLocation().distance(location);
    }
}