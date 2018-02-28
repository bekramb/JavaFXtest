package javafx.ru.appex.model;

import java.time.LocalDateTime;

public class Note {
    private int id;
    private LocalDateTime dateTime;
    private String text;

    public Note() {
    }

    public Note(int id, LocalDateTime dateTime, String text) {
        this.id = id;
        this.dateTime = dateTime;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
