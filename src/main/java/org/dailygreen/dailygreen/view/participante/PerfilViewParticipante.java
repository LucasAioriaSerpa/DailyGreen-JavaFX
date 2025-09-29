package org.dailygreen.dailygreen.view.participante;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.util.DAT.ParticipanteDAT;
import org.dailygreen.dailygreen.view.PostagensView;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.DAT.DATuser;

import java.util.ArrayList;
import java.util.Objects;

public class PerfilViewParticipante {
    private final BorderPane layout;
    private final Participante participante;

    public PerfilViewParticipante(Stage stage, Participante participante) {
        this.participante = participante;
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

        Label letraInicial = new Label(participante.getNome().substring(0, 1).toUpperCase());
        letraInicial.getStyleClass().add("profile-photo-text");
        fotoPerfil.getChildren().add(letraInicial);

        Text nome = new Text(participante.getNome());
        nome.getStyleClass().add("profile-name");

        Text email = new Text(participante.getEmail());
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
        EditarPerfilViewParticipante editarView = new EditarPerfilViewParticipante(stage, participante);
        stage.getScene().setRoot(editarView.getView());
    }

    private void deletarConta(Stage stage) {
        ArrayList<Participante> participantes = ParticipanteDAT.lerLista();
        participantes.removeIf(p -> p.getEmail().equals(participante.getEmail()));
        ParticipanteDAT.salvarLista(participantes);
        User user = DATuser.getUser();
        user.setLogged(false);
        user.setAccountParticipante(null);
        DATuser.setUser(user);
        voltarParaLogin(stage);
    }

    private void voltarParaLogin(Stage stage) {
        User user = DATuser.getUser();
        user.setLogged(false);
        user.setAccountParticipante(null);
        DATuser.setUser(user);
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