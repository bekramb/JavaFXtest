package javafx.ru.appex.db;

import javafx.ru.appex.dao.DBNoteDaoImpl;
import javafx.ru.appex.utils.DialogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnection {

    public static final String JDBC_SQLITE_NOTE_DB = "jdbc:sqlite:memory";

    public static Connection getConnection() {
        try {
            String url = JDBC_SQLITE_NOTE_DB;
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            DialogManager.showErrorDialog("Ошибка соединения", "Не удалось подключиться к бд");
        }
        return null;
    }
}
