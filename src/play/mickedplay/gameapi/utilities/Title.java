package play.mickedplay.gameapi.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class Title {

    private final int fadeIn, stay, fadeOut;
    private String title, subtitle;

    public Title(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public Title(String title, int fadeIn, int stay, int fadeOut) {
        this(title, "", fadeIn, stay, fadeOut);
    }

    public Title(String title, String subtitle) {
        this(title, subtitle, 10, 120, 10);
    }

//    public void sendTo(Player player) {
//        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
//        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{'text': '" + this.title + "'}"), fadeIn, stay, fadeOut));
//        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{'text': '" + this.subtitle + "'}")));
//    }

    public void sendTo(Player player) {
        try {
            Object object;
            Object chatSubtitle;
            Constructor subtitleConstructor;
            Object subtitlePacket;
            if (this.title != null) {
                object = NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                chatSubtitle = NMSUtilities.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = NMSUtilities.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtilities.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(object, chatSubtitle, fadeIn, stay, fadeOut);
                NMSUtilities.sendPacket(player, subtitlePacket);
                object = NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatSubtitle = NMSUtilities.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = NMSUtilities.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtilities.getNMSClass("IChatBaseComponent"));
                subtitlePacket = subtitleConstructor.newInstance(object, chatSubtitle);
                NMSUtilities.sendPacket(player, subtitlePacket);
            }

            if (this.subtitle != null) {
                object = NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                chatSubtitle = NMSUtilities.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = NMSUtilities.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtilities.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(object, chatSubtitle, fadeIn, stay, fadeOut);
                NMSUtilities.sendPacket(player, subtitlePacket);
                object = NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = NMSUtilities.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = NMSUtilities.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtilities.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtilities.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(object, chatSubtitle, fadeIn, stay, fadeOut);
                NMSUtilities.sendPacket(player, subtitlePacket);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sendToAll() {
        Bukkit.getOnlinePlayers().forEach(this::sendTo);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subtitle;
    }

    public void setSubTitle(String subTitle) {
        this.subtitle = subTitle;
    }
}