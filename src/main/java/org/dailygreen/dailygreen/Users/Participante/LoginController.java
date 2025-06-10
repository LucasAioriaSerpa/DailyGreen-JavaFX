package org.dailygreen.dailygreen.Users.Participante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
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
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        var participantes = ArquivoParticipante.lerLista();
        Participante participanteLogado = participantes.stream()
                .filter(p -> {
                    try {
                        return p.getEmail().equals(email) && p.getPassword().equals(senha);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .orElse(null);

        if (participanteLogado != null) {
            lblStatus.setText("Login realizado com sucesso!");
            lblStatus.setStyle("-fx-text-fill: green;");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_perfil_screen.fxml"));
                Parent root = loader.load();

                PerfilController controller = loader.getController();
                controller.setParticipante(participanteLogado);

                Stage stage = (Stage) txtEmail.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                lblStatus.setText("Erro ao abrir perfil.");
                lblStatus.setStyle("-fx-text-fill: red;");
            }

        } else {
            lblStatus.setText("Email ou senha inválidos!");
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void abrirCadastro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_cadastro_screen.fxml"));
            Parent novaTela = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(novaTela, (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao abrir a tela de cadastro.");
        }
    }
}