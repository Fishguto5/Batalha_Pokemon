package exception;

import javafx.scene.control.Alert;

public class NomeInvalido extends Exception {
    public NomeInvalido(String message) {
        super(message);
        //mostra na tela um aviso que o nome não pode ser nulo
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nome Inválido");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, digite seu nome!");
        alert.showAndWait();
    }
}
