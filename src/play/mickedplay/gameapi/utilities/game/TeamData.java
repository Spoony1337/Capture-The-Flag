package play.mickedplay.gameapi.utilities.game;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Created by mickedplay on 16.05.2016 at 13:23 CEST.
 * You are not allowed to remove this comment.
 */

public enum TeamData {
    // @formatter:off
//    WHITE("White", ChatColor.WHITE, 0, 15, new int[]{255, 255, 255}),
//    ORANGE("Orange", ChatColor.GOLD, 1, 14, new int[]{216, 127, 51}),
    LIGHT_BLUE("Blau", ChatColor.BLUE, 3, 12, new int[]{51, 76, 178}),
//    YELLOW("Yellow", ChatColor.YELLOW, 4, 11, new int[]{229, 229, 51}),
//    GREEN("Green", ChatColor.GREEN, 5, 10, new int[]{127, 204, 25}),
//    LIGHT_PURPLE("Light Purple", ChatColor.LIGHT_PURPLE, 6, 9, new int[]{242, 127, 165}),
//    DARK_GRAY("Dark Gray", ChatColor.DARK_GRAY, 7, 8, new int[]{76, 76, 76}),
//    GRAY("Gray", ChatColor.GRAY, 8, 7, new int[]{153, 154, 154}),
//    AQUA("Aqua", ChatColor.AQUA, 9, 6, new int[]{76, 127, 153}),
//    DARK_PURPLE("Dark Purple", ChatColor.DARK_PURPLE, 10, 5, new int[]{127, 63, 178}),
//    DARK_BLUE("Dunkelblau", ChatColor.DARK_BLUE, 11, 4, new int[]{51, 76, 178}),
//    DARK_GREEN("Dark Green", ChatColor.DARK_GREEN, 13, 2, new int[]{102, 127, 51}),
    RED("Rot", ChatColor.RED, 14, 1, new int[]{153, 51, 51});
//    BLACK("Black", ChatColor.BLACK, 15, 0, new int[]{25, 25, 25});
    // @formatter:on

    @Deprecated
    private String codeName;
    private String name;
    private ChatColor chatColor;
    private short blockMetaId, bannerMetaId;
    private int[] rgb;
    private Color color;

    TeamData(String name, ChatColor chatColor, int blockSubId, int bannerSubId, int[] rgb) {
        this.codeName = this.name();
        this.name = name;
        this.chatColor = chatColor;
        this.blockMetaId = (short) blockSubId;
        this.bannerMetaId = (short) bannerSubId;
        this.rgb = rgb;
        this.color = Color.fromRGB(rgb[0], rgb[1], rgb[2]);
    }

    @Deprecated
    public String getCodeName() {
        return this.codeName;
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public short getBlockMetaId() {
        return this.blockMetaId;
    }

    public short getBannerMetaId() {
        return bannerMetaId;
    }

    public int[] getRGB() {
        return this.rgb;
    }

    public Color getColor() {
        return this.color;
    }
}