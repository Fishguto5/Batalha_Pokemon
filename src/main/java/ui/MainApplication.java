package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApplication extends Application {
    public static String nome_jogador;
    public static int altura_tela = 1280;
    public static int largura_tela = 720;
    @Override
    public void start(Stage stage) {
        //Inicia a interface visual
        try {
            URL fxmlUrl = getClass().getResource("/ui/TelaInicial.fxml");
            Parent root = FXMLLoader.load(fxmlUrl);

            Scene scene = new Scene(root,MainApplication.altura_tela,MainApplication.largura_tela);

            URL cssUrl = getClass().getResource("/ui/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setTitle("Batalha Pokémon");
            stage.setScene(scene);

            stage.setMinWidth(800);
            stage.setMinHeight(600);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
