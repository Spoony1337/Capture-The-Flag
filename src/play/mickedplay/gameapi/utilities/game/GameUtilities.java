package play.mickedplay.gameapi.utilities.game;

/**
 * Created by mickedplay on 17.08.2016 at 19:48 CEST.
 * You are not allowed to remove this comment.
 */
public class GameUtilities {

    public static String formatTime(long time, TimeType type) {
        long first = 0;
        long second = 0;
        // @formatter:off
        switch (type) {
            case HOURS_MINUTES: first = time / 3600; second = time / 60; break;
            case MINUTES_SECONDS: first = time / 60; second = time % 60; break;
            case SECONDS_MILLISECONDS: first = time; second = first % time; break;
        }
        // @formatter:on
        return (first < 10 ? "" : "") + first + ":" + (second < 10 ? "0" : "") + second;
    }
}