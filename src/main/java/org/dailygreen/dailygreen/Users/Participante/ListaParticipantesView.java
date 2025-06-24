package org.dailygreen.dailygreen.Users.Participante;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class ListaParticipantesView {
    private VBox layout;
    private Stage stage;

    public ListaParticipantesView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/participante.css")
        ).toExternalForm());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        criarComponentes();
    }

    private void criarComponentes() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.getStyleClass().add("card");


        Text titulo = new Text("Lista de Participantes");
        titulo.getStyleClass().add("title");


        VBox listaContainer = new VBox(10);
        listaContainer.setAlignment(Pos.CENTER);

        try {
            List<Participante> participantes = ArquivoParticipante.lerLista();

            if (participantes.isEmpty()) {
                listaContainer.getChildren().add(new Label("Nenhum participante cadastrado."));
            } else {

                HBox cabecalho = new HBox(20);
                cabecalho.setAlignment(Pos.CENTER);

                Label lblNome = new Label("Nome");
                lblNome.getStyleClass().add("cabecalho-label");

                Label lblEmail = new Label("Email");
                lblEmail.getStyleClass().add("cabecalho-label");

                cabecalho.getChildren().addAll(lblNome, lblEmail);
                listaContainer.getChildren().add(cabecalho);


                for (Participante p : participantes) {
                    HBox linhaParticipante = new HBox(20);
                    linhaParticipante.setAlignment(Pos.CENTER);

                    Label nomeLabel = new Label(p.getNome());
                    nomeLabel.getStyleClass().add("dado-label");

                    Label emailLabel = new Label(p.getEmail());
                    emailLabel.getStyleClass().add("dado-label");

                    linhaParticipante.getChildren().addAll(nomeLabel, emailLabel);
                    listaContainer.getChildren().add(linhaParticipante);
                }
            }
        } catch (Exception e) {
            listaContainer.getChildren().add(new Label("Erro ao carregar participantes."));
        }

        ScrollPane scrollPane = new ScrollPane(listaContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(300);


        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button-secondary");
        btnVoltar.setOnAction(e -> voltarParaLogin());

        card.getChildren().addAll(titulo, scrollPane, btnVoltar);
        layout.getChildren().add(card);
    }

    private void voltarParaLogin() {
        LoginViewParticipante loginView = new LoginViewParticipante(stage);
        stage.getScene().setRoot(loginView.getView());
    }

    public VBox getView() {
        return layout;
    }
}