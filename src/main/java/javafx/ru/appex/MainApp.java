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

        Parent root = FXMLLoader.load(getClass().getResource("/views/main2.fxml"));
       // primaryStage.setTitle("Users List");
        primaryStage.setTitle("Notes List");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}