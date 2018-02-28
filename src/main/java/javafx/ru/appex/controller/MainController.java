package javafx.ru.appex.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.ru.appex.dao.NoteDaoImpl;
import javafx.ru.appex.model.Note;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainController {

    private NoteDaoImpl noteDao = new NoteDaoImpl();

    @FXML
    private TableView<Note> tableNotes;

    @FXML
    private TableColumn<Note, Integer> columnId;

    @FXML
    private TableColumn<Note, LocalDateTime> columnDateTime;

    @FXML
    private TableColumn<Note, String> columnText;

    @FXML
    private Label labelCount;

    // инициализируем форму данными
    @FXML
    private void initialize() {


        // устанавливаем тип и значение которое должно хранится в колонке
        columnId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        columnDateTime.setCellValueFactory(new PropertyValueFactory<Note, LocalDateTime>("dateTime"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));

        noteDao.getNoteList().addListener(new ListChangeListener<Note>() {
            @Override
            public void onChanged(Change<? extends Note> c) {
                updateCountLabel();
            }
        });

        // подготавливаем данные для таблицы
        // вы можете получать их с базы данных
        noteDao.fillTestData();

        // заполняем таблицу данными
        tableNotes.setItems(noteDao.getNoteList());
    }


    private void updateCountLabel() {
        labelCount.setText("Количество записей: " + noteDao.getNoteList().size());
    }

    public void showDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        // если нажата не кнопка - выходим из метода
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        Note selectedNote = (Note)tableNotes.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "btnAdd":
                System.out.println("add "+selectedNote);
                break;

            case "btnEdit":
                System.out.println("edit " + selectedNote);
                break;


            case "btnDelete":
                System.out.println("delete " + selectedNote);
                break;
        }

        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/edit.fxml"));
            stage.setTitle("Редактирование записи");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
