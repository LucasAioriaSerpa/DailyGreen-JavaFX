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
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.Participante.PerfilViewParticipante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class PostagensView {
    private final Stage stage;
    private final VBox layout;
    private Participante accountParticipante;
    private Organizacao accountOrganizacao;
    private User user;
    public PostagensView(Stage stage) {
        user = DATuser.getUser();
        this.stage = stage;
        this.layout = new VBox();
        User user = DATuser.getUser();
        switch (user.getType()) {
            case "participante" -> accountParticipante = user.getAccountParticipante();
            case "organizador" -> accountOrganizacao = user.getAccountOrganizacao();
        }
        layout.setAlignment(Pos.CENTER); // Centraliza o conteúdo verticalmente
        layout.getStyleClass().add("postagens-view");
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
        titleField.setMaxWidth(400);
        Label descriptionLabel = new Label("Descrição:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setMaxWidth(400);
        descriptionArea.setPrefRowCount(4);
        Button submitButton = new Button("Postar");
        submitButton.setMaxWidth(200);
        submitButton.setOnAction(_ -> {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            if (title.isBlank() || description.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao postar");
                alert.setHeaderText("Preencha todos os campos");
                alert.setContentText("Todos os campos devem ser preenchidos.");
                alert.showAndWait();
            } else {
                PostagensControll.sendPost(stage, accountParticipante.getID(), title, description);
                stage.getScene().setRoot(new PostagensView(stage).getView());
            }
        });
        postForm.getChildren().addAll(
            titleLabel, titleField,
            descriptionLabel, descriptionArea,
            submitButton
        );
        return postForm;
    }

    private ListView<VBox> createPostList() {
        ListView<VBox> postList = new ListView<>();
        VBox.setVgrow(postList, Priority.ALWAYS);
        postList.setMaxWidth(400);
        for (Post post : DATpost.lerLista()) {
            VBox postCard = new VBox(5);
            postCard.getStyleClass().add("post-card");
            postCard.setPadding(new Insets(10));
            Label titleLabel = new Label(post.getTitulo());
            titleLabel.getStyleClass().add("post-title");
            Label descriptionLabel = new Label(post.getDescricao());
            descriptionLabel.getStyleClass().add("post-description");
            descriptionLabel.setWrapText(true);
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            Button btnEditar = new Button("Editar");
            btnEditar.setOnAction(e -> {
                // ? Formulário simples de edição
                TextInputDialog dialog = new TextInputDialog(post.getTitulo());
                Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
                dialogStage.getIcons().add(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/dailygreen_icon-32x32.png"))
                ));
                dialog.setTitle("Editar Postagem");
                dialog.setHeaderText("Editar título da postagem");
                dialog.setContentText("Novo título:");
                dialog.showAndWait().ifPresent(novoTitulo -> {
                    TextInputDialog descDialog = new TextInputDialog(post.getDescricao());
                    descDialog.setTitle("Editar Postagem");
                    descDialog.setHeaderText("Editar descrição da postagem");
                    descDialog.setContentText("Nova descrição:");
                    descDialog.showAndWait().ifPresent(novaDesc -> {
                        post.setTitulo(novoTitulo);
                        post.setDescricao(novaDesc);
                        DATpost.atualizarPost(post.getID(), post);
                        // ? Atualiza a lista
                        postList.getItems().clear();
                        postList.getItems().addAll(createPostList().getItems());
                    });
                });
            });
            Button btnDeletar = new Button("Deletar");
            btnDeletar.setOnAction(e -> {
                DATpost.removerPost(post.getID());
                postList.getItems().remove(postCard);
            });
            buttonBox.getChildren().addAll(btnEditar, btnDeletar);
            Separator separator = new Separator();
            postCard.getChildren().addAll(titleLabel, descriptionLabel, buttonBox, separator);
            postList.getItems().add(postCard);
        }
        return postList;
    }
}