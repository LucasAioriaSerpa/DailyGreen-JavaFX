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
    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblStatus;

    @FXML
    private void cadastrarParticipante() {
        try {

            if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()) {
                lblStatus.setText("Por favor, preencha todos os campos!");
                return;
            }

            Participante novoParticipante = new Participante(
                    txtNome.getText(),
                    txtEmail.getText(),  // Estou assumindo que você usa email como CPF
                    txtSenha.getText()
            );

            ArquivoParticipante.adicionarParticipante(novoParticipante);

            lblStatus.setText("Cadastro realizado com sucesso!");
            lblStatus.setStyle("-fx-text-fill: green;");



        } catch (Exception e) {
            lblStatus.setText("Erro ao cadastrar: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }

        ArquivoParticipante.lerLista().forEach(System.out::println);
    }

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"));
            Parent novaTela = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(novaTela);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao abrir a tela de Login.");
        }
    }
}