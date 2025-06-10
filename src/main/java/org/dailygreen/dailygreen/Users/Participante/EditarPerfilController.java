package org.dailygreen.dailygreen.Users.Participante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditarPerfilController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private Label lblStatus;

    private Participante participanteOriginal;

    public void setParticipante(Participante participante) {
        this.participanteOriginal = participante;
        txtNome.setText(participante.getNome());
        txtEmail.setText(participante.getEmail());
    }

    @FXML
    private void salvarEdicao(ActionEvent event) {
        String novoNome = txtNome.getText();
        String novoEmail = txtEmail.getText();

        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
            return;
        }

        ArrayList<Participante> lista = ArquivoParticipante.lerLista();

        for (Participante p : lista) {
            if (p.getEmail().equals(participanteOriginal.getEmail())) {
                p.setNome(novoNome);
                p.setEmail(novoEmail);
                break;
            }
        }

        ArquivoParticipante.salvarLista(lista);

        participanteOriginal.setNome(novoNome);
        participanteOriginal.setEmail(novoEmail);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_perfil_screen.fxml"));
            Parent root = loader.load();

            PerfilController perfilController = loader.getController();
            perfilController.setParticipante(participanteOriginal);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao voltar para o perfil.");
        }
    }

    @FXML
    private void voltarParaPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_perfil_screen.fxml"));
            Parent root = loader.load();

            PerfilController perfilController = loader.getController();
            perfilController.setParticipante(participanteOriginal);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao voltar para o perfil.");
        }
    }
}