package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        root.getStylesheets()
                .add("JMetroLightTheme.css");
        primaryStage.setTitle("Graph simulation");
        primaryStage.setScene(new Scene(root,
                1000,
                700));
        primaryStage.show();
    }
}
