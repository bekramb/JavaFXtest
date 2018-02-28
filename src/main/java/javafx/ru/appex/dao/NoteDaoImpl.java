package javafx.ru.appex.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.ru.appex.model.Note;

import java.time.LocalDate;

public class NoteDaoImpl implements NoteDao {
    private ObservableList<Note> noteList = FXCollections.observableArrayList();

    @Override
    public void add(Note note) {
        noteList.add(note);
    }

    @Override
    // для коллекции не используется, но пригодится для случая, когда данные хранятся в БД и пр.
    public void update(Note note) {
        // т.к. коллекция и является хранилищем - то ничего обновлять не нужно
        // если бы данные хранились в БД или файле - в этом методе нужно было бы обновить соотв. запись
    }

    @Override
    public void delete(Note note) {
        noteList.remove(note);
    }

    public ObservableList<Note> getNoteList() {
        return noteList;
    }

    public void print(){
        int number = 0;
        System.out.println();
        for(Note n : noteList){
            number++;
            System.out.println(number+") data = "+n.getLocalDate()+"; text = "+n.getText());
        }
    }

    public void fillTestData(){
        // notes.add(new Note(1, (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), "qwerty"));
        noteList.add(new Note(2, LocalDate.MIN, "dsfsdfw"));
        noteList.add(new Note(3, LocalDate.MAX, "Jeck@mail.com"));
    }


}
