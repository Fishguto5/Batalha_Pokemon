package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TelaInicialController {

    @FXML
    //Arrumar nome da função pois foi alterado o fluxo das telas
    private void iniciarBatalha(ActionEvent event) throws IOException {
        System.out.println("Botão Iniciar Batalha clicado! Carregando a cena de batalha...");

        URL fxmlUrl = getClass().getResource("/cadastro/TelaCadastro.fxml");
        if (fxmlUrl == null) {
            System.err.println("ERRO CRÍTICO: O arquivo 'Batalha.fxml' não foi encontrado.");
            return;
        }
        Parent batalhaRoot = FXMLLoader.load(fxmlUrl);
        Scene batalhaScene = new Scene(batalhaRoot);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        URL cssUrl = getClass().getResource("/ui/style.css");
        if (cssUrl != null) {
            batalhaScene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setScene(batalhaScene);
        stage.centerOnScreen();
        stage.show();
    }
}