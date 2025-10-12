package org.dailygreen.dailygreen.view.components.postagens;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.User;

import static org.dailygreen.dailygreen.util.controller.PostagensControll.acaoPostar;
import static org.dailygreen.dailygreen.view.components.postagens.UpdatePostList.updatePostList;

public class CreatePostForm {
    public static VBox createPostForm(Stage stage, User user) {
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
            acaoPostar(user.getAccountParticipante(), titleField, descriptionArea);
            updatePostList(stage);
        });
        postForm.getChildren().addAll(formTitle, titleField, descriptionArea, submitButton);
        return postForm;
    }

}
