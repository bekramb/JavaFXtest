package javafx.ru.appex.controller;

import javafx.collections.ListChangeListener;
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
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainController {

    private NoteDaoImpl noteDao = new NoteDaoImpl();

    @FXML
    private TableView<Note> tableNotes;

    @FXML
    private TableColumn<Note, Integer> columnId;

    @FXML
    private TableColumn<Note, LocalDate> columnDate;

    @FXML
    private TableColumn<Note, String> columnText;

    @FXML
    private Label labelCount;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditController editController;
    private Stage editStage;

    // инициализируем форму данными
    @FXML
    private void initialize() {


        // устанавливаем тип и значение которое должно хранится в колонке
        columnId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, LocalDate>("localDate"));
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

        try {

            fxmlLoader.setLocation(getClass().getResource("/views/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateCountLabel() {
        labelCount.setText("Количество записей: " + noteDao.getNoteList().size());
    }

    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        // если нажата не кнопка - выходим из метода
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        Note selectedNote =  tableNotes.getSelectionModel().getSelectedItem();

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        editController.setNote(selectedNote);
        editController.setCurrentLocalDate();

        switch (clickedButton.getId()) {
            case "btnAdd":

                break;

            case "btnEdit":
                showDialog(parentWindow);
                break;


            case "btnDelete":
               // NoteDaoImpl.delete((Note)tableNotes.getSelectionModel().getSelectedItem());
                break;

        }

    }


    private void showDialog(Window parentWindow) {
        if (editStage==null) {
            editStage = new Stage();
            editStage.setTitle("Редактирование записи");
            editStage.setMinHeight(150);
            editStage.setMinWidth(300);
            editStage.setResizable(false);
            editStage.setScene(new Scene(fxmlEdit));
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(parentWindow);
        }
            editStage.show();

    }
}
