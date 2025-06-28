package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    public static String nome_jogador;
    @Override
    public void start(Stage stage) {

        try {
            System.out.println("Iniciando o carregamento do FXML...");

            URL fxmlUrl = getClass().getResource("/ui/TelaInicial.fxml");

            System.out.println("URL do FXML encontrada: " + fxmlUrl);

            if (fxmlUrl == null) {
                System.err.println("O arquivo de Tela Inicial não foi encontrado.");
                return;
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            // Título à janela do jogo
            stage.setTitle("Batalha Pokémon");
            //Setta o tamanho
            Scene scene = new Scene(root, 1000, 800);
            URL cssUrl = getClass().getResource("/ui/style.css");
            if(cssUrl != null) {
                System.out.println("Arquivo CSS encontrado, aplicando estilos...");
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Arquivo CSS não encontrado");
            }
            stage.setScene(scene);
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
