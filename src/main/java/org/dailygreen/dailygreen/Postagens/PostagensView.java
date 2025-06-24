package org.dailygreen.dailygreen.Postagens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import org.dailygreen.dailygreen.Postagens.Post.Post;
import org.dailygreen.dailygreen.Postagens.utils.DATpost;
import org.dailygreen.dailygreen.Users.Organizacao.Organizacao;
import org.dailygreen.dailygreen.Users.Participante.ArquivoParticipante;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;
import org.jetbrains.annotations.NotNull;
import org.dailygreen.dailygreen.Postagens.Reacao.RecaoDAO;
import org.dailygreen.dailygreen.Postagens.Comentario.Comentario;
import org.dailygreen.dailygreen.Postagens.Comentario.ComentarioDAO;
import org.dailygreen.dailygreen.Users.Organizacao.persistencia.EventoOrganizacaoDAT;
import org.dailygreen.dailygreen.Users.Organizacao.util.EventoOrganizacao;

import java.util.ArrayList;
import java.util.Objects;

public class PostagensView {
    private final Stage stage;
    private final VBox layout;
    private Participante accountParticipante;
    private Organizacao accountOrganizacao;
    private final User user;
    public PostagensView(Stage stage) {
        user = DATuser.getUser();
        this.stage = stage;
        this.layout = new VBox();
        User user = DATuser.getUser();
        switch (user.getType()) {
            case "participante" -> accountParticipante = user.getAccountParticipante();
            case "organizador" -> accountOrganizacao = user.getAccountOrganizacao();
        }
        layout.getStyleClass().add("postagens-view");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/classPostagem.css")).toExternalForm()
        );
        stage.setTitle("Postagens");
        stage.setResizable(false);
        showComponents(stage);
    }

    public VBox getView() {return layout;}

    public Stage getStage() {return stage;}

    private void showComponents(Stage stage) {
        HBox mainContainer = new HBox();
        // ? Section left
        VBox leftSection = createSection("left-section");
        Button btnPerfil = new Button("Perfil");
        btnPerfil.getStyleClass().add("button-primary");
        btnPerfil.setOnAction(_ -> {PostagensControll.goPerfil(stage, user.getAccountParticipante());});
        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("button-secondary");
        leftSection.getChildren().addAll(btnPerfil, btnPostagens);
        HBox.setHgrow(leftSection, Priority.ALWAYS);
        leftSection.setPrefWidth(120);
        // ? Section center
        VBox centerSection = createSection("center-section");
        HBox.setHgrow(centerSection, Priority.ALWAYS);
        centerSection.setPrefWidth(360);
        ScrollPane scrollPane = new ScrollPane(centerSection);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        centerSection.getChildren().addAll(
                createPostForm(stage),
                createPostList()
        );
        // ? Section right
        VBox rightSection = createSection("right-section");
        HBox.setHgrow(rightSection, Priority.ALWAYS);
        rightSection.setPrefWidth(120);
        Label eventosLabel = new Label("Eventos");
        eventosLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        ListView<String> eventosListView = new ListView<>();
        eventosListView.setPrefWidth(110);
        eventosListView.setMaxWidth(110);
        eventosListView.setMinWidth(110);
        eventosListView.setPrefHeight(300);
        for (EventoOrganizacao evento : EventoOrganizacaoDAT.lerLista()) {
            eventosListView.getItems().add(evento.toString());
        }
        rightSection.getChildren().addAll(eventosLabel, eventosListView);

        // ? config main container
        mainContainer.getChildren().addAll(leftSection, centerSection, rightSection);
        layout.getChildren().add(mainContainer);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
    }

    private VBox createSection(String styleClass) {
        VBox section = new VBox(10);
        section.setAlignment(Pos.TOP_CENTER);
        section.setPadding(new Insets(15));
        section.getStyleClass().add(styleClass);
        return section;
    }

    private VBox createPostForm(Stage stage) {
        VBox postForm = new VBox(15);
        postForm.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        titleField.setPromptText("Digite o título da postagem");
        titleField.setMaxWidth(400);
        Label descriptionLabel = new Label("Descrição:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Digite a descrição da postagem");
        descriptionArea.setWrapText(true);
        descriptionArea.setMaxWidth(400);
        descriptionArea.setPrefRowCount(4);
        Button submitButton = getButton(stage, titleField, descriptionArea);
        postForm.getChildren().addAll(
            titleLabel, titleField,
            descriptionLabel, descriptionArea,
            submitButton
        );
        return postForm;
    }

    private @NotNull Button getButton(Stage stage, TextField titleField, TextArea descriptionArea) {
        Button submitButton = new Button("Postar");
        submitButton.setMaxWidth(200);
        submitButton.setOnAction(_ -> {
            PostagensControll.acaoPostar(stage, accountParticipante, titleField, descriptionArea);
        });
        return submitButton;
    }

    /**
     * Cria a lista de postagens exibidas na interface.
     * Cada item é um VBox criado por createPostCard.
     */
    private ListView<VBox> createPostList() {
        ListView<VBox> postList = new ListView<>();
        VBox.setVgrow(postList, Priority.ALWAYS);
        postList.setMaxWidth(400);
        ArrayList<Participante> participanteList = ArquivoParticipante.lerLista();
        for (Post post : DATpost.lerLista()) {
            VBox postCard = createPostCard(post, participanteList, postList);
            postList.getItems().add(postCard);
        }
        return postList;
    }

    /**
     * Cria o card visual de uma postagem, incluindo título, descrição, reações, comentários e botões de edição/deleção.
     */
    private VBox createPostCard(Post post, ArrayList<Participante> participanteList, ListView<VBox> postList) {
        VBox postCard = new VBox(5);
        postCard.getStyleClass().add("post-card");
        postCard.setPadding(new Insets(10));

        String autorNome = participanteList.stream()
                .filter(p -> p.getID() == post.getId_autor())
                .map(Participante::getNome)
                .findFirst()
                .orElse("Desconhecido");

        Label titleLabel = new Label(post.getTitulo() + " (Autor: " + autorNome + ")");
        titleLabel.getStyleClass().add("post-title");

        Label descriptionLabel = new Label(post.getDescricao());
        descriptionLabel.getStyleClass().add("post-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(360);

        HBox reactionsBox = createReactionsBox(post);

        VBox comentariosBox = createComentariosBox(post);

        if (post.getId_autor() == accountParticipante.getID()) {
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            Button btnEditar = getBtnEditar(post, postList);
            Button btnDeletar = new Button("Deletar");
            btnDeletar.setOnAction(e -> {
                PostagensControll.acaoDeletarPost(post, postCard, postList, this);
            });
            buttonBox.getChildren().addAll(btnEditar, btnDeletar);
            Separator separator = new Separator();
            postCard.getChildren().addAll(titleLabel, descriptionLabel, reactionsBox, comentariosBox, buttonBox, separator);
        } else {
            Separator separator = new Separator();
            postCard.getChildren().addAll(titleLabel, descriptionLabel, reactionsBox, comentariosBox, separator);
        }
        return postCard;
    }

    /**
     * Cria o box de reações para uma postagem.
     */
    private HBox createReactionsBox(Post post) {
        HBox reactionsBox = new HBox(8);
        reactionsBox.setAlignment(Pos.CENTER_LEFT);
        reactionsBox.getStyleClass().add("reactions-box");
        String[] tipos = {"gostei", "parabens", "apoio", "amei", "genial"};
        String email = user.getEmailOrganizador();
        String tipoReacaoUsuario = RecaoDAO.buscarTipoReacaoUsuario(email, post.getID());
        for (String tipo : tipos) {
            Button btn = new Button(tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
            btn.getStyleClass().add("button-" + tipo);
            btn.setStyle("-fx-font-size: 11px; -fx-padding: 3 8;");
            long count = RecaoDAO.contarPorTipo(post.getID(), tipo);
            Label lblCount = new Label(String.valueOf(count));
            if (tipo.equals(tipoReacaoUsuario)) {
                btn.setStyle(
                        btn.getStyle() + ";" +
                                " -fx-border-color: #222;" +
                                " -fx-border-width: 2;" +
                                " -fx-font-weight: bold;"
                );
            }
            btn.setOnAction(e -> {
                PostagensControll.acaoReagir(post, tipo, email, stage);
            });
            VBox reactionCol = new VBox(btn, lblCount);
            reactionCol.setAlignment(Pos.CENTER);
            reactionCol.getStyleClass().add("reaction-col");
            reactionsBox.getChildren().add(reactionCol);
        }
        return reactionsBox;
    }

    /**
     * Cria o box de comentários para uma postagem, incluindo lista e campo de novo comentário.
     */
    private VBox createComentariosBox(Post post) {
        VBox comentariosBox = new VBox(4);
        comentariosBox.setPadding(new Insets(8, 0, 0, 0));
        Label comentariosTitulo = new Label("Comentários:");
        comentariosTitulo.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        comentariosBox.getChildren().add(comentariosTitulo);
        for (Comentario comentario : ComentarioDAO.buscarPorPost(post.getID())) {
            Label comentarioLabel = new Label(
                comentario.getAutorEmail() + ": " + comentario.getConteudo()
            );
            comentarioLabel.setStyle("" +
                    "-fx-font-size: 11px;" +
                    " -fx-background-color: #f4f4f4;" +
                    " -fx-padding: 2 6 2 6;" +
                    " -fx-background-radius: 4;"
            );
            comentariosBox.getChildren().add(comentarioLabel);
        }
        HBox novoComentarioBox = new HBox(4);
        novoComentarioBox.setAlignment(Pos.CENTER_LEFT);
        TextField campoComentario = new TextField();
        campoComentario.setPromptText("Adicionar comentário...");
        campoComentario.setPrefWidth(220);
        Button btnComentar = new Button("Comentar");
        btnComentar.setOnAction(e -> {
            PostagensControll.acaoComentar(post, user.getEmailOrganizador(), campoComentario, stage);
        });
        novoComentarioBox.getChildren().addAll(campoComentario, btnComentar);
        comentariosBox.getChildren().add(novoComentarioBox);
        return comentariosBox;
    }

    private @NotNull Button getBtnEditar(Post post, ListView<VBox> postList) {
        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(_ -> {
            PostagensControll.acaoEditar(post, postList, this, stage);
        });
        return btnEditar;
    }

    private void updatePostList() {stage.getScene().setRoot(new PostagensView(stage).getView());}

}