package javafx.ru.appex.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.ru.appex.dao.DBNoteDaoImpl;
import javafx.ru.appex.dao.NoteDao;
import javafx.ru.appex.model.Note;
import javafx.ru.appex.utils.DialogManager;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class MainController  {

    private NoteDao noteDao = new DBNoteDaoImpl();

    private Stage mainStage;

    private ExecutorService service;

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

        //For multithreading: Create executor that uses daemon threads:
        service = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            }
        });

        columnId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, LocalDate>("localDate"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));
        initListeners();
        try {
            fillTable();
        } catch (SQLException e) {
            e.printStackTrace();
            DialogManager.showErrorDialog("Ошибка обновления данных из бд"," Не удалось обновить данные");
        } catch (ClassNotFoundException e) {
            DialogManager.showErrorDialog("Ошибка обновления данных из бд"," Не удалось обновить данные");
        }
        initLoader();
    }

    private void fillTable() throws SQLException, ClassNotFoundException {
        Task<ObservableList<Note>> task = new Task<ObservableList<Note>>(){
            @Override
            public ObservableList<Note> call() throws Exception{
                ObservableList<Note> result = noteDao.findAll();
                return result;
            }
        };

        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                task.getException().printStackTrace();
            }
        });
        task.setOnSucceeded(e-> tableNotes.setItems((ObservableList<Note>) task.getValue()));
        service.submit(task);
    }

    private void initListeners(){
        ((DBNoteDaoImpl)noteDao).getNoteList().addListener(new ListChangeListener<Note>() {
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
        labelCount.setText("Количество записей: " + ((DBNoteDaoImpl)noteDao).getNoteList().size());
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
                Task<Boolean> taskAdd = new Task<Boolean>(){
                    @Override
                    public Boolean call() throws Exception{
                       boolean b = noteDao.add(editController.getNote());
                        return b;
                    }
                };
               // noteDao.add(editController.getNote());
                service.submit(taskAdd);
                break;

            case "btnEdit":
                editController.setNote((Note)tableNotes.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDelete":
                Task<Boolean> taskRemove = new Task<Boolean>(){
                    @Override
                    public Boolean call() throws Exception{
                        boolean b =  noteDao.delete((Note)tableNotes.getSelectionModel().getSelectedItem());;
                        return b;
                    }
                };
                service.submit(taskRemove);
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
