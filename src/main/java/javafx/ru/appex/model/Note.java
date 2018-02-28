package javafx.ru.appex.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Note {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleObjectProperty<LocalDate> localDate = new SimpleObjectProperty<>(LocalDate.now());
    private SimpleStringProperty text = new SimpleStringProperty("");

    public Note() {
    }

    public Note(int id, LocalDate localDate, String text) {
        this.id = new SimpleIntegerProperty(id);
        this.localDate = new SimpleObjectProperty<>(localDate);
        this.text = new SimpleStringProperty(text);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getLocalDate() {
        return localDate.get();
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate.set(localDate);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public SimpleIntegerProperty idProperty(){
        return id;
    }

    public SimpleObjectProperty<LocalDate> localDateProperty(){
        return localDate;
    }

    public SimpleStringProperty textProperty(){
        return text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id.get() +
                ", localDate=" + localDate.get() +
                ", text='" + text.get() + '\'' +
                '}';
    }
}
