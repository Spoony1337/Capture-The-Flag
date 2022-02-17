package play.mickedplay.gameapi.utilities.player;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * Created by mickedplay on 17.08.2016 at 19:45 CEST.
 * You are not allowed to remove this comment.
 */
public class PlayerUtilities {

    public static void cleanPlayer(Player player) {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.setExp(0F);
        player.setLevel(0);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setHealth(20D);
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
    }

//    public static void setHeaderAndFooter(Player player, String header, String footer) {
//        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
//        IChatBaseComponent head = IChatBaseComponent.ChatSerializer.a("{'color': '" + "', 'text': '" + header + "'}");
//        IChatBaseComponent foot = IChatBaseComponent.ChatSerializer.a("{'color': '" + "', 'text': '" + footer + "'}");
//        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
//        try {
//            Field headerField = packet.getClass().getDeclaredField("a");
//            headerField.setAccessible(true);
//            headerField.set(packet, head);
//            headerField.setAccessible(!headerField.isAccessible());
//
//            Field footerField = packet.getClass().getDeclaredField("b");
//            footerField.setAccessible(true);
//            footerField.set(packet, foot);
//            footerField.setAccessible(!footerField.isAccessible());
//        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exception) {
//            exception.printStackTrace();
//        }
//        connection.sendPacket(packet);
//    }

    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 20, 180, 180);
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}"), fadeIn, stay, fadeOut));
        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}")));
    }

    public static void sendActionBar(Player player, String message) {
        //PacketPlayOutChat ppoc = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), ChatMessageType.GAME_INFO);
       // ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
    }

    public static void setHeaderAndFooter(Player player, String header, String footer) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent head = IChatBaseComponent.ChatSerializer.a("{'color': '" + "', 'text': '" + header + "'}");
        IChatBaseComponent foot = IChatBaseComponent.ChatSerializer.a("{'color': '" + "', 'text': '" + footer + "'}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, head);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, foot);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        connection.sendPacket(packet);
    }

    public static void setPlayerCamera(Player player, Player targetPlayer) {
        PacketPlayOutCamera camera = new PacketPlayOutCamera(((CraftPlayer) (targetPlayer == null ? player : targetPlayer)).getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(camera);
    }

    public static void resetPlayerCamera(Player player) {
        setPlayerCamera(player, null);
    }

    public static void respawn(Plugin plugin, Player player) {
        respawn(plugin, player, 0);
    }

    public static void respawn(Plugin plugin, Player player, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.spigot().respawn(), delay);
    }
}