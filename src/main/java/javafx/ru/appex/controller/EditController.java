package javafx.ru.appex.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.ru.appex.model.Note;
import javafx.ru.appex.utils.DialogManager;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class EditController {

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker localDate;

    @FXML
    private TextArea textNote;

    private Note note;

    private boolean saveClicked = false;// для определения нажатой кнопки

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        if (note == null) {
            return;
        }
        saveClicked = false;
        this.note = note;
        localDate.setValue(note.getLocalDate());
        textNote.setText(note.getText());
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        note.setLocalDate(localDate.getValue());
        note.setText(textNote.getText());
        saveClicked = true;
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (localDate.getValue() == null || textNote.getText().trim().length() == 0 || textNote.getText().length() > 100) {
            String title = "";
            String msg = "";
            if (textNote.getText().length() > 100) {
                title = "Слишком много символов";
                msg = "Количество символов в заметке не должно превышать 100";
            } else {
                title = "Пустые поля";
                msg = "Заполните поля!";
            }
            DialogManager.showInfoDialog(title, msg);
            return false;
        }
        return true;
    }

    public void setCurrentLocalDate() {
        if (localDate == null) {
            localDate = new DatePicker();
        }
        this.localDate.setValue(LocalDate.now());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }
}
