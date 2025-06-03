package org.dailygreen.dailygreen.Users.Participante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblStatus;

    @FXML
    private void salvarDados() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
        } else {
            // Aqui você pode validar com o banco de dados
            lblStatus.setText("Login realizado com: " + email);
        }
    }

    @FXML
    private void abrirCadastro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_cadastro_screen.fxml"));
            Parent novaTela = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(novaTela);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao abrir a tela de cadastro.");
        }
    }
}