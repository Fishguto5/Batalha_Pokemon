package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {

        try {
            System.out.println("Iniciando o carregamento do FXML...");

            URL fxmlUrl = getClass().getResource("/batalha/Batalha.fxml");

            System.out.println("URL do FXML encontrada: " + fxmlUrl);

            if (fxmlUrl == null) {
                System.err.println("ERRO CRÍTICO: O arquivo 'Batalha.fxml' não foi encontrado no classpath. Verifique se ele está em 'src/main/resources/batalha/'.");
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);
            System.out.println("FXML carregado com sucesso.");

            stage.setTitle("Batalha Pokémon");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            System.out.println("Deu certo!");

        } catch (Exception e) {
            System.err.println("<<<<< ERRO NA APLIACAO >>>>>");

            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
