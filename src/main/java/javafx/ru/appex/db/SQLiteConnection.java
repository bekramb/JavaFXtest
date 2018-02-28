package javafx.ru.appex.db;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConnection {

    public static final String JDBC_SQLITE_DB_BIRTHDAYS_DB = "jdbc:sqlite:db\\birthdays.db";
    public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    public static Connection getConnection() {
        try {
            Class.forName(ORG_SQLITE_JDBC).newInstance();
            String url = JDBC_SQLITE_DB_BIRTHDAYS_DB;
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            LoggerFactory.getLogger(SQLiteConnection.class.getName()).debug("Faild to get connection to database");
        }
        return null;
    }
}
