package javafx.ru.appex.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.ru.appex.model.Note;
import javafx.ru.appex.model.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class MainController2 {
    private ObservableList<Note> notes = FXCollections.observableArrayList();

    @FXML
    private TableView<Note> tableNotes;

    @FXML
    private TableColumn<Note, LocalDateTime> dateTime;

    @FXML
    private TableColumn<Note, String> text;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        dateTime.setCellValueFactory(new PropertyValueFactory<Note, LocalDateTime>("dateTime"));
        text.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));

        // заполняем таблицу данными
        tableNotes.setItems(notes);
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {
        notes.add(new Note(1, LocalDateTime.now(), "qwerty"));
        notes.add(new Note(2, LocalDateTime.now(), "dsfsdfw"));
        notes.add(new Note(3, LocalDateTime.now(), "Jeck@mail.com"));

    }

}
