package play.mickedplay.ctf.sql;

import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.player.CTFPlayer;

import java.sql.*;

/**
 * Created by mickedplay on 10.05.2016 at 21:44 UTC+1.
 */
public class MySQL {

    private CaptureTheFlag ctf;
    private Connection connection;
    private boolean connectionAvailable;

    public MySQL(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.openConnection();
        this.createRequiredTables();
    }

    private void openConnection() {
        try {
            int port = 3306;
            String hostname = "127.0.0.1";
            String database = "capturetheflag";
            String username = "root";
            String password = "minecraft";
//            String hostname = "ms538.nitrado.net";
//            String database = "ni1290413_1_DB";
//            String username = "ni1290413_1_DB";
//            String password = "tw96GlbE";
            this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            this.connectionAvailable = true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(String query) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.executeUpdate(query);
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            this.openConnection();
        }
    }

    public ResultSet query(String query) {
        try {
            return this.connection.prepareStatement(query).executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            this.openConnection();
        }
        return null;
    }

    private void createRequiredTables() {
        if (this.connectionAvailable) {
            this.update("CREATE TABLE IF NOT EXISTS capturetheflag(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY, uuid VARCHAR(36), highscore BIGINT(13), kills INT(12), deaths INT(12), flags_stolen INT(10), flags_captured INT(10), gained_exp INT(12), bought_teamItems INT(10), first_played BIGINT(13), last_played BIGINT(13), time_played BIGINT(24))");
        }
    }

    public boolean playerExists(CTFPlayer ctfPlayer) {
        try {
            ResultSet resultSet = this.ctf.getMySQL().query("SELECT id FROM capturetheflag WHERE uuid='" + ctfPlayer.getUniqueId().toString() + "'");
            return resultSet.next() && resultSet.getString("id") != null;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }
}