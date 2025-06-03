package org.dailygreen.dailygreen.Users.Participante;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ParticipanteController {
    @FXML private TextField txtNome;
    @FXML private Label lblStatus;

    private Participante participanteModel;  // Referência ao modelo

    @FXML
    private void initialize() {
        // Inicialização opcional
        participanteModel = new Participante();
    }

    @FXML
    private void salvarDados() {
        participanteModel.setNome(txtNome.getText());
        lblStatus.setText("Salvo: " + participanteModel.getNome());
    }
}