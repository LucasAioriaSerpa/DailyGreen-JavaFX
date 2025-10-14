package org.dailygreen.dailygreen.view.participante;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.repository.impl.ParticipantJsonRepository;
import org.dailygreen.dailygreen.repository.impl.UserJsonRepository;

import java.util.ArrayList;
import java.util.Objects;

public class EditarPerfilViewParticipante {
    private VBox layout;
    private TextField txtNome;
    private TextField txtEmail;
    private Label lblStatus;
    private Participant participantOriginal;

    public EditarPerfilViewParticipante(Stage stage, Participant participant) {
        this.participantOriginal = participant;
        this.layout = new VBox(20);
        layout.getStyleClass().add("main-screen");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/participante.css")).toExternalForm()
        );
        criarComponentes(stage);
    }

    private void criarComponentes(Stage stage) {
        // Título
        Text titulo = new Text("Editar Perfil");
        titulo.getStyleClass().add("title");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNome = new Label("Nome:");
        txtNome = new TextField(participantOriginal.getNome());
        txtNome.setPromptText("Novo nome");
        Label lblEmail = new Label("Email:");
        txtEmail = new TextField(participantOriginal.getEmail());
        txtEmail.setPromptText("Novo email");
        grid.addRow(0, lblNome, txtNome);
        grid.addRow(1, lblEmail, txtEmail);

        lblStatus = new Label();
        lblStatus.getStyleClass().add("status-label");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("button-primary");
        btnSalvar.setOnAction(e -> salvarEdicao(stage));
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("button-secondary");
        btnCancelar.setOnAction(e -> voltarParaPerfil(stage));
        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        // principal
        layout.getChildren().addAll(
                titulo,
                grid,
                lblStatus,
                botoes
        );
        layout.setAlignment(Pos.CENTER);
    }

    private void salvarEdicao(Stage stage) {
        String novoNome = txtNome.getText();
        String novoEmail = txtEmail.getText();
        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }
        try {
            // Atualiza na lista de participantes
            ArrayList<Participant> lista = (ArrayList<Participant>) new ParticipantJsonRepository().findAll();
            lista.stream()
                    .filter(p -> p.getEmail().equals(participantOriginal.getEmail()))
                    .findFirst()
                    .ifPresent(p -> {
                        p.setNome(novoNome);
                        p.setEmail(novoEmail);
                    });
            new ParticipantJsonRepository().saveAll(lista);
            // Atualiza o participante original
            participantOriginal.setNome(novoNome);
            participantOriginal.setEmail(novoEmail);
            User user = new UserJsonRepository().findAll().getFirst();
            user.setAccountParticipante(participantOriginal);
            new UserJsonRepository().update(user);
            // Volta para a tela de perfil
            voltarParaPerfil(stage);
        } catch (Exception e) {
            lblStatus.setText("Erro ao salvar edições!");
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void voltarParaPerfil(Stage stage) {
        PerfilViewParticipante perfilView = new PerfilViewParticipante(stage, participantOriginal);
        stage.getScene().setRoot(perfilView.getView());
    }

    public VBox getView() {
        return layout;
    }
}