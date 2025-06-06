package org.dailygreen.dailygreen.Users.Participante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.util.datManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CadastroController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblStatus;

    @FXML
    private void cadastrarParticipante() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            lblStatus.setText("Email inválido!");
            return;
        }

        if (senha.length() < 6) {
            lblStatus.setText("Senha deve ter 6+ caracteres!");
            return;
        }

        try {
            ArrayList<Participante> participantes = datManager.loadArray("participante.dat");
            if (participantes == null) participantes = new ArrayList<>();

            for (Participante p : participantes) {
                if (p.getEmail().equalsIgnoreCase(email)) {
                    lblStatus.setText("Email já cadastrado!");
                    return;
                }
            }

            participantes.add(new Participante(nome, email, senha));
            datManager.saveArray(participantes, "participante.dat");

            lblStatus.setText("Cadastro realizado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            lblStatus.setText("Erro ao cadastrar.");
            e.printStackTrace();
        }
    }

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        try {
            Parent loginTela = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml")
            ));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginTela));
        } catch (IOException e) {
            lblStatus.setText("Erro ao carregar tela de login.");
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtSenha.clear();
    }
}