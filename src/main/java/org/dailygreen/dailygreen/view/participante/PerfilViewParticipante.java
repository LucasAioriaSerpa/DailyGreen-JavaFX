package org.dailygreen.dailygreen.view.participante;

import java.util.Objects;

import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import org.dailygreen.dailygreen.view.feed.PostagensView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PerfilViewParticipante {
    private final BorderPane layout;
    private final Participant participant;
    private final PersistenceFacade persistenceFacade;

    public PerfilViewParticipante(Stage stage, Participant participant) {
        this.participant = participant;
        this.persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
        this.layout = new BorderPane();
        layout.getStyleClass().add("main-screen");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/participante.css")).toExternalForm()
        );
        criarComponentes(stage);
    }

    private void criarComponentes(Stage stage) {
        // BANNER
        HBox banner = new HBox(20);
        banner.getStyleClass().add("profile-banner");
        banner.setPadding(new Insets(20));
        banner.setAlignment(Pos.CENTER_LEFT);

        StackPane fotoPerfil = new StackPane();
        fotoPerfil.getStyleClass().add("profile-photo");
        fotoPerfil.setPrefSize(80, 80);

        Label letraInicial = new Label(participant.getNome().substring(0, 1).toUpperCase());
        letraInicial.getStyleClass().add("profile-photo-text");
        fotoPerfil.getChildren().add(letraInicial);

        Text nome = new Text(participant.getNome());
        nome.getStyleClass().add("profile-name");

        Text email = new Text(participant.getEmail());
        email.getStyleClass().add("profile-email");

        VBox infoBox = new VBox(5, nome, email);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        banner.getChildren().addAll(fotoPerfil, infoBox);
        layout.setTop(banner);

        // POSTAGENS
        VBox postagensArea = new VBox(15);
        postagensArea.setPadding(new Insets(20));
        postagensArea.setAlignment(Pos.TOP_CENTER);

        Label tituloPostagens = new Label("Suas Postagens");
        tituloPostagens.getStyleClass().add("section-title");

        layout.setCenter(postagensArea);

        // BOTÕES
        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(20));

        Button btnEditar = new Button("Editar Perfil");
        btnEditar.getStyleClass().add("button-primary");
        btnEditar.setOnAction(e -> editarPerfil(stage));

        Button btnDeletar = new Button("Deletar Conta");
        btnDeletar.getStyleClass().add("button-danger");
        btnDeletar.setOnAction(e -> deletarConta(stage));

        Button btnVoltar = new Button("Logout");
        btnVoltar.getStyleClass().add("button-secondary");
        btnVoltar.setOnAction(e -> voltarParaLogin(stage));

        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("button-primary");
        btnPostagens.setOnAction(_ -> irParaPostagens(stage));

        botoes.getChildren().addAll(btnEditar, btnDeletar, btnVoltar, btnPostagens);
        layout.setBottom(botoes);
    }

    private void editarPerfil(Stage stage) {
        EditarPerfilViewParticipante editarView = new EditarPerfilViewParticipante(stage, participant);
        stage.getScene().setRoot(editarView.getView());
    }

    private void deletarConta(Stage stage) {
        persistenceFacade.deleteParticipantByEmail(participant.getEmail());
        User user = persistenceFacade.findUserByEmail(participant.getEmail()).orElse(null);
        if (user != null) {
            user.setAccountParticipante(null);
            user.setLogged(false);
            user.setRole(null);
            persistenceFacade.updateUser(user);
        }
        voltarParaLogin(stage);
    }

    private void voltarParaLogin(Stage stage) {
        User user = persistenceFacade.findUserByEmail(participant.getEmail()).orElse(null);
        if (user != null) {
            user.setAccountParticipante(null);
            user.setLogged(false);
            user.setRole(null);
            persistenceFacade.updateUser(user);
        }
        LoginViewParticipante loginView = new LoginViewParticipante(stage);
        stage.getScene().setRoot(loginView.getView());
    }

    private void irParaPostagens(Stage stage) {
        PostagensView postagensView = new PostagensView(stage);
        stage.getScene().setRoot(postagensView.getView());
    }

    public BorderPane getView() {
        return layout;
    }
}