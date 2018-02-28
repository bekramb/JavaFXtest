package javafx.ru.appex.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Note {
    private IntegerProperty id;
    private SimpleObjectProperty<LocalDate> localDate;
    private StringProperty text;

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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id.get() +
                ", localDate=" + localDate.get() +
                ", text='" + text.get() + '\'' +
                '}';
    }
}
