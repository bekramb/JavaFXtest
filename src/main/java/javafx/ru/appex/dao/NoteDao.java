package javafx.ru.appex.dao;

import javafx.ru.appex.model.Note;

public interface NoteDao {

    // добавить запись
    void add(Note note);

    // внести измененные значения (подтвердить измененные данные)
    void update(Note note);

    // удалить запись
    void delete(Note note);
}
