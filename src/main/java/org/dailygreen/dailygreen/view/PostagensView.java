package org.dailygreen.dailygreen.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Postagens.Comentario;
import org.dailygreen.dailygreen.Postagens.EventoOrganizacao;
import org.dailygreen.dailygreen.Postagens.Post;
import org.dailygreen.dailygreen.Users.Organizacao;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.DAO.ComentarioDAO;
import org.dailygreen.dailygreen.util.DAO.RecaoDAO;
import org.dailygreen.dailygreen.util.DAT.DATpost;
import org.dailygreen.dailygreen.util.DAT.DATuser;
import org.dailygreen.dailygreen.util.DAT.EventoOrganizacaoDAT;
import org.dailygreen.dailygreen.util.DAT.ParticipanteDAT;
import org.dailygreen.dailygreen.util.controller.PostagensControll;
import org.jetbrains.annotations.NotNull;

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
        switch (user.getType()) {
            case "participante" -> accountParticipante = user.getAccountParticipante();
            case "organizador" -> accountOrganizacao = user.getAccountOrganizacao();
        }
        layout.getStyleClass().add("postagens-view");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/classPostagem.css")).toExternalForm()
        );
        stage.setTitle("DailyGreen - Feed");
        showComponents();
    }

    public VBox getView() {
        return layout;
    }

    public Stage getStage() {
        return stage;
    }

    private void showComponents() {
        HBox mainContainer = new HBox();
        mainContainer.getStyleClass().add("main-container");

        // ? Seção Esquerda (Navegação)
        VBox leftSection = createSection("left-section");
        Button btnPerfil = new Button("Meu Perfil");
        btnPerfil.getStyleClass().add("nav-button");
        btnPerfil.setOnAction(_ -> PostagensControll.goPerfil(stage, user.getAccountParticipante()));

        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("nav-button-active");
        leftSection.getChildren().addAll(btnPerfil, btnPostagens);
        leftSection.setPrefWidth(220);
        leftSection.setMinWidth(200);
        HBox.setHgrow(leftSection, Priority.NEVER);

        // ? Seção Central (Postagens)
        VBox centerSectionContent = new VBox(20);
        centerSectionContent.setPadding(new Insets(15));
        centerSectionContent.getChildren().addAll(
                createPostForm(),
                createPostList()
        );

        ScrollPane centerScrollPane = new ScrollPane(centerSectionContent);
        centerScrollPane.getStyleClass().add("center-scroll-pane");
        centerScrollPane.setFitToWidth(true);
        HBox.setHgrow(centerScrollPane, Priority.ALWAYS);

        // ? Seção Direita (Eventos)
        VBox rightSection = createSection("right-section");
        rightSection.setPrefWidth(300);
        rightSection.setMinWidth(260);
        HBox.setHgrow(rightSection, Priority.SOMETIMES);

        Label eventosLabel = new Label("Próximos Eventos");
        eventosLabel.getStyleClass().add("section-title");

        ListView<EventoOrganizacao> eventosListView = new ListView<>();
        eventosListView.getItems().addAll(EventoOrganizacaoDAT.lerLista());
        eventosListView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(EventoOrganizacao evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox eventoCard = new VBox(5);
                    Label nomeEvento = new Label(evento.getNome());
                    nomeEvento.getStyleClass().add("event-title");
                    Label dataEvento = new Label("Data: " + evento.getData());
                    dataEvento.getStyleClass().add("event-detail");
                    eventoCard.getChildren().addAll(nomeEvento, dataEvento);
                    setGraphic(eventoCard);
                }
            }
        });
        VBox.setVgrow(eventosListView, Priority.ALWAYS);
        rightSection.getChildren().addAll(eventosLabel, eventosListView);
        mainContainer.getChildren().addAll(leftSection, centerScrollPane, rightSection);
        layout.getChildren().add(mainContainer);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
    }

    private VBox createSection(String styleClass) {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_CENTER);
        section.setPadding(new Insets(20));
        section.getStyleClass().add(styleClass);
        return section;
    }

    private VBox createPostForm() {
        VBox postForm = new VBox(10);
        postForm.getStyleClass().add("post-form");

        Label formTitle = new Label("Criar nova postagem");
        formTitle.getStyleClass().add("form-title");

        TextField titleField = new TextField();
        titleField.setPromptText("Qual o título da sua ideia?");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descreva sua ideia ou evento aqui...");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(4);

        Button submitButton = new Button("Publicar");
        submitButton.setMaxWidth(Double.MAX_VALUE);
        submitButton.setOnAction(_ -> {
            PostagensControll.acaoPostar(accountParticipante, titleField, descriptionArea);
            updatePostList();
        });
        postForm.getChildren().addAll(formTitle, titleField, descriptionArea, submitButton);
        return postForm;
    }

    private ListView<VBox> createPostList() {
        ListView<VBox> postList = new ListView<>();
        postList.getStyleClass().add("post-list");
        VBox.setVgrow(postList, Priority.ALWAYS);
        ArrayList<Participante> participanteList = ParticipanteDAT.lerLista();
        for (Post post : DATpost.lerLista()) {
            VBox postCard = createPostCard(post, participanteList, postList);
            postList.getItems().add(postCard);
        }
        return postList;
    }

    private VBox createPostCard(Post post, ArrayList<Participante> participanteList, ListView<VBox> postList) {
        VBox postCard = new VBox(15);
        postCard.getStyleClass().add("post-card");

        // ? Autor
        String autorNome = participanteList.stream()
                .filter(p -> p.getID() == post.getId_autor())
                .map(Participante::getNome)
                .findFirst()
                .orElse("Autor Desconhecido");

        // ? Header do Post
        Label titleLabel = new Label(post.getTitulo());
        titleLabel.getStyleClass().add("post-title");
        Label authorLabel = new Label("por " + autorNome);
        authorLabel.getStyleClass().add("post-author");
        VBox postHeader = new VBox(titleLabel, authorLabel);

        // ? Descrição
        Label descriptionLabel = new Label(post.getDescricao());
        descriptionLabel.getStyleClass().add("post-description");
        descriptionLabel.setWrapText(true);

        // ? Reações e Comentários
        HBox reactionsBox = createReactionsBox(post);
        VBox comentariosBox = createComentariosBox(post);

        postCard.getChildren().addAll(postHeader, descriptionLabel, reactionsBox, comentariosBox);

        if (user.getAccountParticipante() != null && post.getId_autor() == user.getAccountParticipante().getID()) {
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            buttonBox.getStyleClass().add("action-buttons");
            Button btnEditar = new Button("Editar");
            btnEditar.setOnAction(_ -> {
                PostagensControll.acaoEditar(post);
                updatePostList();
            });
            Button btnDeletar = new Button("Deletar");
            btnDeletar.getStyleClass().add("button-danger");
            btnDeletar.setOnAction(_ -> {
                PostagensControll.acaoDeletarPost(post, postCard, postList, this);
                updatePostList();
            });
            buttonBox.getChildren().addAll(btnEditar, btnDeletar);
            postCard.getChildren().add(buttonBox);
        }
        return postCard;
    }

    private HBox createReactionsBox(Post post) {
        HBox reactionsBox = new HBox(5);
        reactionsBox.setAlignment(Pos.CENTER_LEFT);
        reactionsBox.getStyleClass().add("reactions-box");
        String[] tipos = {"gostei", "parabens", "apoio", "amei", "genial"};
        String tipoReacaoUsuario = RecaoDAO.buscarTipoReacaoUsuario(user.getAccountParticipante().getEmail(), post.getID());
        for (String tipo : tipos) {
            long count = RecaoDAO.contarPorTipo(post.getID(), tipo);
            Button btn = new Button(tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " (" + count + ")");
            btn.getStyleClass().add("reaction-button");
            btn.getStyleClass().add("button-" + tipo);
            if (tipo.equals(tipoReacaoUsuario)) {
                btn.getStyleClass().add("selected");
            }
            btn.setOnAction(_ -> {
                PostagensControll.acaoReagir(post, tipo, user.getAccountParticipante().getEmail());
                updatePostList();
            });
            reactionsBox.getChildren().add(btn);
        }
        return reactionsBox;
    }

    private VBox createComentariosBox(Post post) {
        VBox comentariosBox = new VBox(8);
        comentariosBox.getStyleClass().add("comentarios-box");
        Label comentariosTitulo = new Label("Comentários");
        comentariosTitulo.getStyleClass().add("section-subtitle");
        comentariosBox.getChildren().add(comentariosTitulo);
        VBox commentList = new VBox(5);
        commentList.getStyleClass().add("comment-list");
        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) ComentarioDAO.buscarPorPost(post.getID());
        if (comentarios.isEmpty()) {
            Label noCommentsLabel = new Label("Nenhum comentário ainda.");
            noCommentsLabel.getStyleClass().add("no-comments-label");
            commentList.getChildren().add(noCommentsLabel);
        } else {
            for (Comentario comentario : comentarios) {
                Label comentarioLabel = new Label(comentario.getAutorEmail() + ": " + comentario.getConteudo());
                comentarioLabel.getStyleClass().add("comment-label");
                comentarioLabel.setWrapText(true);
                commentList.getChildren().add(comentarioLabel);
            }
        }
        HBox novoComentarioBox = new HBox(5);
        novoComentarioBox.setAlignment(Pos.CENTER_LEFT);
        TextField campoComentario = new TextField();
        campoComentario.setPromptText("Escreva um comentário...");
        HBox.setHgrow(campoComentario, Priority.ALWAYS);
        Button btnComentar = new Button("Enviar");
        btnComentar.setOnAction(_ -> {
            PostagensControll.acaoComentar(post, user.getAccountParticipante().getEmail(), campoComentario);
            updatePostList();
        });
        novoComentarioBox.getChildren().addAll(campoComentario, btnComentar);
        comentariosBox.getChildren().addAll(commentList, novoComentarioBox);
        return comentariosBox;
    }

    private void updatePostList() {
        stage.getScene().setRoot(new PostagensView(stage).getView());
    }
}