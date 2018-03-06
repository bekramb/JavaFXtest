package javafx.ru.appex.db;

import javafx.ru.appex.dao.DBNoteDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitDb {
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS 'note'";
    private static final String CREAT_TABLE = "CREATE TABLE 'note' ('id'   INTEGER NOT NULL"
            + "  PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, 'date' DATE  NOT NULL, 'text' TEXT NOT NULL)";

    public static void createDB() {
        try (Connection con = SQLiteConnection.getConnection(); Statement statement = con.createStatement()) {
            statement.execute(DROP_TABLE);
            statement.execute(CREAT_TABLE);
        } catch (SQLException ex) {
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeDB() {
        try (Connection con = SQLiteConnection.getConnection(); Statement statement = con.createStatement()) {
            statement.execute("INSERT INTO 'note' ('date', 'text') VALUES ('2014-11-10', 'тестовая заметка1'); ");
            statement.execute("INSERT INTO 'note' ('date', 'text') VALUES ('2014-11-11', 'тестовая заметка2'); ");

        } catch (SQLException ex) {
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

