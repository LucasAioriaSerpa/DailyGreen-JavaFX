package org.dailygreen.dailygreen.Users.Participante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PerfilController {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblEmail;

    private Participante participante;

    public void setParticipante(Participante participante) {
        this.participante = participante;
        lblNome.setText(participante.getNome());
        lblEmail.setText(participante.getEmail());
    }

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"));
            Parent novaTela = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_editar_perfil_screen.fxml"));
            Parent root = loader.load();

            EditarPerfilController editarController = loader.getController();
            editarController.setParticipante(participante);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarConta(ActionEvent event) {
        ArrayList<Participante> participantes = ArquivoParticipante.lerLista();


        participantes.removeIf(p -> p.getEmail().equals(participante.getEmail()));

        ArquivoParticipante.salvarLista(participantes);


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}