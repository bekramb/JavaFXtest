package javafx.ru.appex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.ru.appex.controller.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

//        log.info("Starting Hello JavaFX and Maven demonstration application");
//
//        String fxmlFile = "/views/main.views";
//        log.debug("Loading FXML for main view from: {}", fxmlFile);
//        FXMLLoader loader = new FXMLLoader();
//        Parent rootNode =  loader.load(getClass().getResource(fxmlFile));
//
//        log.debug("Showing JFX scene");
//        Scene scene = new Scene(rootNode, 400, 200);
//        scene.getStylesheets().add("/styles/styles.css");
//
//        stage.setTitle("Hello JavaFX and Maven");
//        stage.setScene(scene);

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

//        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
//       // primaryStage.setTitle("Users List");
//        primaryStage.setTitle("List notes");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }
}
