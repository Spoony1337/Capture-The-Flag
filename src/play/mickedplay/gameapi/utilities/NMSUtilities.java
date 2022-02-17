package play.mickedplay.gameapi.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by mickedplay on 17.08.2016 at 19:48 CEST.
 * You are not allowed to remove this comment.
 */
public class NMSUtilities {

    public static void sendPacket(Player player, Object packet) {
        try {
            Object e = player.getClass().getMethod("getHandle", new Class[0]).invoke(player);
            Object playerConnection = e.getClass().getField("playerConnection").get(e);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNMSClass("Packet")}).invoke(playerConnection, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            return null;
        }
    }
}