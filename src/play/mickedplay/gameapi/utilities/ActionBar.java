package play.mickedplay.gameapi.utilities;

import net.md_5.bungee.api.ChatMessageType;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    private String message;

    public ActionBar(String message) {
        this.message = message;
    }

    public void sendTo(Player player) {
        if (player == null) return;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        //((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO));
    }

    public void sendToAll() {
        Bukkit.getOnlinePlayers().forEach(this::sendTo);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}