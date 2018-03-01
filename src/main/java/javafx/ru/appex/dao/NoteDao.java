package javafx.ru.appex.dao;

import javafx.collections.ObservableList;
import javafx.ru.appex.model.Note;

public interface NoteDao {

    // добавить запись
    boolean add(Note note);

    // внести измененные значения (подтвердить измененные данные)
    boolean update(Note note);

    // удалить запись
    boolean delete(Note note);

    // Получить все записи
    ObservableList<Note> findAll();
}
