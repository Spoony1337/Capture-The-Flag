package play.mickedplay.ctf.player;

import play.mickedplay.ctf.sql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mickedplay on 04.05.2016 at 17:04 UTC+1.
 * You are not allowed to remove this comment.
 */
public class GameStats {

    private CTFPlayer ctfPlayer;
    private long gameLogin, timePlayed, lastPlayed;
    private int highscore, kills, deaths, flagsStolen, flagsCaptured, gainedExp, boughtTeamItems;

    public GameStats(CTFPlayer ctfPlayer) {
        this.ctfPlayer = ctfPlayer;
        if (!ctfPlayer.getCaptureTheFlag().getMySQL().playerExists(ctfPlayer)) {
            MySQL mySQL = ctfPlayer.getCaptureTheFlag().getMySQL();
            mySQL.update("INSERT INTO capturetheflag(uuid,highscore,kills,deaths,flags_stolen,flags_captured,gained_exp,bought_teamItems,first_played,last_played,time_played) VALUES ('" + ctfPlayer.getUniqueId().toString() + "',0,0,0,0,0,0,0," + System.currentTimeMillis() + ",0,0)");
        }
        this.fetchGameStatistics();
    }

    private void fetchGameStatistics() {
        try {
            ResultSet resultSet = this.ctfPlayer.getCaptureTheFlag().getMySQL().query("SELECT * FROM capturetheflag WHERE uuid='" + this.ctfPlayer.getUniqueId().toString() + "'");
            while (resultSet.next()) {
                this.highscore = resultSet.getInt("highscore");
                this.kills = resultSet.getInt("kills");
                this.deaths = resultSet.getInt("deaths");
                this.flagsStolen = resultSet.getInt("flags_stolen");
                this.flagsCaptured = resultSet.getInt("flags_captured");
                this.gainedExp = resultSet.getInt("gained_exp");
                this.boughtTeamItems = resultSet.getInt("bought_teamItems");
                this.lastPlayed = resultSet.getLong("last_played");
                this.timePlayed = resultSet.getLong("time_played");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void saveGameStatistics() {
        long timestamp = System.currentTimeMillis();
        this.timePlayed += (timestamp - this.gameLogin);

        MySQL mySQL = this.ctfPlayer.getCaptureTheFlag().getMySQL();
        mySQL.update("UPDATE capturetheflag SET highscore=" + this.highscore + ",kills=" + this.kills + ",deaths=" + this.deaths + ",flags_stolen=" + this.flagsStolen + ",flags_captured=" + this.flagsCaptured + ",gained_exp=" + this.gainedExp + ",bought_teamItems=" + this.boughtTeamItems + ",last_played=" + timestamp + ",time_played=" + this.timePlayed + " WHERE uuid='" + this.ctfPlayer.getUniqueId().toString() + "'");
    }

    public void setGameLogin() {
        this.gameLogin = System.currentTimeMillis();
    }

    public int getHighscore() {
        return highscore;
    }

    public void addKill() {
        this.kills++;
        this.highscore += 5;
    }

    public int getKills() {
        return kills;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addStolenFlag() {
        this.flagsStolen++;
        this.highscore += 7;
    }

    public int getStolenFlags() {
        return flagsStolen;
    }

    public void addCapturedFlag() {
        this.flagsCaptured++;
        this.highscore += 10;
    }

    public int getCapturedFlags() {
        return flagsCaptured;
    }

    public void addGainedExp(int gainedExp) {
        this.gainedExp += gainedExp;
        this.highscore += gainedExp;
    }

    public int getGainedExp() {
        return gainedExp;
    }

    public void addBoughtTeamItem() {
        this.boughtTeamItems++;
        this.highscore += 3;
    }

    public int getBoughtTeamItems() {
        return boughtTeamItems;
    }
}