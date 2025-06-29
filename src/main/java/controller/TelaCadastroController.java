package controller;

import exception.NomeInvalido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.MainApplication;


import java.io.IOException;
import java.net.URL;

public class TelaCadastroController {

    @FXML
    private TextField nomeInput;

    @FXML
    private void confirmarNome(ActionEvent event) throws IOException, NomeInvalido {
        String nome = nomeInput.getText();
        //Aplicar Exception
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalido("O nome do treinador não pode ser vazio");
        }

        MainApplication.nome_jogador = nome;
        System.out.println("Nome do treinador definido como: " + MainApplication.nome_jogador);

        Parent rotaBatalha = FXMLLoader.load(getClass().getResource("/batalha/Batalha.fxml"));
        Scene sceneBatalha = new Scene(rotaBatalha);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(sceneBatalha);
    }
}