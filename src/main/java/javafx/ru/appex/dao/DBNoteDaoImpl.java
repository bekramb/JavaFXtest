package javafx.ru.appex.dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.ru.appex.db.SQLiteConnection;
import javafx.ru.appex.model.Note;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBNoteDaoImpl implements NoteDao {

    private ObservableList<Note> noteList = FXCollections.observableArrayList();

    @Override
    public boolean add(Note note) {
        try (Connection con = SQLiteConnection.getConnection(); PreparedStatement statement = con.prepareStatement("insert into note(date, text) values (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, note.getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            statement.setString(2,note.getText());

            int result = statement.executeUpdate();
            if (result>0) {
                int id = statement.getGeneratedKeys().getInt(1);// получить сгенерированный id вставленной записи
                note.setId(id);
                noteList.add(note);
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean delete(Note note) {

        try (Connection con = SQLiteConnection.getConnection();  Statement statement = con.createStatement();) {
            int result = statement.executeUpdate("delete from note where id="+ note.getId());

            if (result>0) {
                noteList.remove(note);
                return true;
            }

        }catch (SQLException ex){
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<Note> findAll() {

        try (Connection con = SQLiteConnection.getConnection(); Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery("select * from note");) {
            while (rs.next()) {
                Note note  = new Note();
                note.setId(rs.getInt("id"));
                note.setLocalDate(LocalDate.parse(rs.getString("date")));
                note.setText(rs.getString("text"));
                noteList.add(note);
            }
        }catch (SQLException ex){
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return noteList;
    }

    @Override
    public boolean update(Note note) {
        try (Connection con = SQLiteConnection.getConnection(); PreparedStatement statement = con.prepareStatement("update note set date=?, text=? where id=?")) {
            statement.setString(1,note.getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            statement.setString(2,note.getText());
            statement.setInt(3,note.getId());

            int result = statement.executeUpdate();
            if (result>0) {
                // обновление в коллекции происходит автоматически, после нажатия ОК в окне редактирования
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(DBNoteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public ObservableList<Note> getNoteList() {
        return noteList;
    }
}
