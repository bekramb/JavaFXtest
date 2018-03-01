package javafx.ru.appex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.ru.appex.controller.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);
        primaryStage.setTitle("Заметки");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(350);
        primaryStage.setScene(new Scene(fxmlMain, 300, 275));
        primaryStage.show();
    }
}
