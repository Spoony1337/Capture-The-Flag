package play.mickedplay.gameapi.utilities;


import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.ArrayList;
import java.util.List;

public class BlockModifier {

    public static void sendBlockBreak(EntityHuman entityHuman, Location loc, int level) {
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), level);
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(entityHuman, loc.getX(), loc.getY(), loc.getZ(), 30, ((CraftWorld) loc.getWorld()).getHandle().dimension, packet);
    }

    public static List<Block> getBlocksInSelection(Location minimum, Location maximum, List<Material> material) {
        List<Block> list = new ArrayList<>();
        int minX = Math.min(minimum.getBlockX(), maximum.getBlockX());
        int minY = Math.min(minimum.getBlockY(), maximum.getBlockY());
        int minZ = Math.min(minimum.getBlockZ(), maximum.getBlockZ());
        int maxX = Math.max(minimum.getBlockX(), maximum.getBlockX());
        int maxY = Math.max(minimum.getBlockY(), maximum.getBlockY());
        int maxZ = Math.max(minimum.getBlockZ(), maximum.getBlockZ());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = minimum.getWorld().getBlockAt(x, y, z);
                    if (material.contains(block.getType())) list.add(block);
                }
            }
        }
        return list;
    }

    public static List<Location> getLocationsInSelection(Location minimum, Location maximum) {
        List<Location> list = new ArrayList<>();
        int minx = Math.min(minimum.getBlockX(), maximum.getBlockX()), miny = Math.min(minimum.getBlockY(), maximum.getBlockY());
        int minz = Math.min(minimum.getBlockZ(), maximum.getBlockZ()), maxx = Math.max(minimum.getBlockX(), maximum.getBlockX());
        int maxy = Math.max(minimum.getBlockY(), maximum.getBlockY()), maxz = Math.max(minimum.getBlockZ(), maximum.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    if (minimum.getWorld().getBlockAt(x, y, z).getType() != Material.AIR) list.add(new Location(minimum.getWorld(), x, y, z));
                }
            }
        }
        return list;
    }

    public static List<Block> getBlocksInSelection(Location minimum, Location maximum) {
        List<Block> list = new ArrayList<>();
        int minx = Math.min(minimum.getBlockX(), maximum.getBlockX()), miny = Math.min(minimum.getBlockY(), maximum.getBlockY());
        int minz = Math.min(minimum.getBlockZ(), maximum.getBlockZ()), maxx = Math.max(minimum.getBlockX(), maximum.getBlockX());
        int maxy = Math.max(minimum.getBlockY(), maximum.getBlockY()), maxz = Math.max(minimum.getBlockZ(), maximum.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    Block block = minimum.getWorld().getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) list.add(block);
                }
            }
        }
        return list;
    }

}