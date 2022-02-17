package play.mickedplay.ctf.shop;

/**
 * Created by mickedplay on 27.04.2016 at 14:40 UTC+1.
 * You are not allowed to remove this comment.
 */
public enum LevelType {
    PLAYER_KILL(1, "Player-Kill"),
    FLAG_STEAL(1, "Flag Stolen"),
    FLAG_CAPTURE(3, "Flag conquered"),
    MINE_EXPLOSION(1, "Mineexplosion"),
    RANDOM_ENEMY_KILL(6, "Random Enemy Kill"),
    RANDOM_ENEMY_KILL_CAPTURER(9, "Player Killed With Flag");

    private int level;
    private String reason;

    LevelType(int level, String reason) {
        this.level = level;
        this.reason = reason;
    }

    public int getLevel() {
        return level;
    }

    public String getReason() {
        return reason;
    }
}