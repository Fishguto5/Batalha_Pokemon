package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {


    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Label label = new Label("Teste");
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Batalha de Pokemon");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
