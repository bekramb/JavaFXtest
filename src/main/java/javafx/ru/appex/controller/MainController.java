package javafx.ru.appex.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.ru.appex.dao.NoteDaoImpl;
import javafx.ru.appex.model.Note;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

// TODO: 01.03.2018 Разобраться с нуль пойнтером при инициализации note
// TODO: 01.03.2018 Разобраться с размерами в таблице и с отображением даты
// TODO: 01.03.2018 Прикрутить работу с базой данных
// TODO: 01.03.2018 Реализовать работу с бд в потоке
public class MainController {

    private NoteDaoImpl noteDao = new NoteDaoImpl();

    private Stage mainStage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;


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

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    // инициализируем форму данными
    @FXML
    private void initialize() {

        columnId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, LocalDate>("localDate"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));
        initListeners();
        fillData();
        initLoader();
    }

    private void fillData() {
        noteDao.fillTestData();
        tableNotes.setItems(noteDao.getNoteList());
    }

    private void initListeners(){
        noteDao.getNoteList().addListener(new ListChangeListener<Note>() {
            @Override
            public void onChanged(Change<? extends Note> c) {
                updateCountLabel();
            }
        });

        tableNotes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    editController.setNote((Note)tableNotes.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });
    }

    private void initLoader() {
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

        switch (clickedButton.getId()) {
            case "btnAdd":
                editController.setNote(new Note());
                editController.setCurrentLocalDate();
                showDialog();
                noteDao.add(editController.getNote());
                break;

            case "btnEdit":
                editController.setNote((Note)tableNotes.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDelete":
                noteDao.delete((Note)tableNotes.getSelectionModel().getSelectedItem());
                break;

        }

    }


    private void showDialog() {
        if (editStage==null) {
            editStage = new Stage();
            editStage.setTitle("Редактирование записи");
            editStage.setMinHeight(150);
            editStage.setMinWidth(300);
            editStage.setResizable(false);
            editStage.setScene(new Scene(fxmlEdit));
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(mainStage);
        }
            editStage.showAndWait();

    }
}
