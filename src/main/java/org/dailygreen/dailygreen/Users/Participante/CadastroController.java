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

public class CadastroController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblStatus;

    @FXML
    private void cadastrarParticipante() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
        } else {
            lblStatus.setText("Cadastro realizado com sucesso!");
            // Aqui você pode salvar os dados no banco
        }
    }

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"));
            Parent loginTela = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginTela);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao voltar para o login.");
        }
    }
}