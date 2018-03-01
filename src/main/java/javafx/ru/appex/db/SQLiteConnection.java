package javafx.ru.appex.db;

import javafx.ru.appex.dao.DBNoteDaoImpl;
import javafx.ru.appex.utils.DialogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnection {

    public static final String JDBC_SQLITE_DB_BIRTHDAYS_DB = "jdbc:sqlite:db/notes.db";
    public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    public static Connection getConnection() {
        try {
            Class.forName(ORG_SQLITE_JDBC).newInstance();
            String url = JDBC_SQLITE_DB_BIRTHDAYS_DB;
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            DialogManager.showErrorDialog("Ошибка соединения", "Не удалось подключиться к бд");
        }
        return null;
    }
}
