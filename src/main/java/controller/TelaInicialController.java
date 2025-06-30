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
    private void iniciarBatalha(ActionEvent event) throws IOException {
        //Pega a rota da tela de Cadastro
        Parent rotaCadastro = FXMLLoader.load(getClass().getResource("/cadastro/TelaCadastro.fxml"));
        //Cria a Cena da tela de Cadastro
        Scene sceneCadastro = new Scene(rotaCadastro);
        sceneCadastro.getStylesheets().add(getClass().getResource("/ui/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(sceneCadastro);
    }
}