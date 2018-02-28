package javafx.ru.appex.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.ru.appex.model.Note;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.LocalDate;

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


    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if(textNote.getLength()<=100) {
            note.setLocalDate(localDate.getValue());
            note.setText(textNote.getText());
            actionClose(actionEvent);
        }else{
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setTitle("Program Information");
            alert.setHeaderText("Number of char more than 100");
            alert.setContentText("You cannot enter more than 100 chars.");
            alert.show();

        }
    }



    public void setNote(Note note) {
        this.note = note;

        localDate.setValue(note.getLocalDate());
        textNote.setText(note.getText());
    }

    public void setCurrentLocalDate() {
        this.localDate.setValue(LocalDate.now());
    }
}
