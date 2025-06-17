package org.dailygreen.dailygreen.Postagens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import org.dailygreen.dailygreen.Postagens.Post.Post;
import org.dailygreen.dailygreen.Postagens.utils.DATpost;
import org.dailygreen.dailygreen.Users.Organizacao.Organizacao;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;

public class PostagensView {
    private final Stage stage;
    private final VBox layout;
    private Participante accountParticipante;
    private Organizacao accountOrganizacao;
    public PostagensView(Stage stage) {
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
        showComponents();
    }

    public VBox getView() {return layout;}

    public Stage getStage() {return stage;}

    private void showComponents() {
        HBox mainContainer = new HBox();
        // ? Section left
        VBox leftSection = createSection("left-section");
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
                createPostForm(),
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

    private VBox createPostForm() {
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
            } else {PostagensControll.sendPost(stage, accountParticipante.getID(), title, description);}
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

            Separator separator = new Separator();

            postCard.getChildren().addAll(titleLabel, descriptionLabel, separator);
            postList.getItems().add(postCard);
        }

        return postList;
    }
}