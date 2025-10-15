package org.dailygreen.dailygreen.view.components.postagens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.controller.MainFeedController;

import java.util.ArrayList;

import static org.dailygreen.dailygreen.view.components.postagens.CreateComentariosBox.createComentariosBox;
import static org.dailygreen.dailygreen.view.components.postagens.CreateReactionsBox.createReactionsBox;
import static org.dailygreen.dailygreen.view.components.postagens.UpdatePostList.updatePostList;

public class CreatePostCard {
    public static VBox createPostCard(Stage stage, VBox layout, Post post, ArrayList<Participant> participantList, ListView<VBox> postList, User user) {
        VBox postCard = new VBox(15);
        postCard.getStyleClass().add("post-card");

        // ? Autor
        String autorNome = participantList.stream()
                .filter(p -> p.getID() == post.getId_autor())
                .map(Participant::getNome)
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
        HBox reactionsBox = createReactionsBox(stage, post, user);
        VBox comentariosBox = createComentariosBox(stage, post, user);

        postCard.getChildren().addAll(postHeader, descriptionLabel, reactionsBox, comentariosBox);

        if (user.getAccountParticipante() != null && post.getId_autor() == user.getAccountParticipante().getID()) {
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            buttonBox.getStyleClass().add("action-buttons");
            Button btnEditar = new Button("Editar");
            btnEditar.setOnAction(_ -> {
                MainFeedController.acaoEditar(post);
                updatePostList(stage);
            });
            Button btnDeletar = new Button("Deletar");
            btnDeletar.getStyleClass().add("button-danger");
            btnDeletar.setOnAction(_ -> {
                MainFeedController.acaoDeletarPost(post, postCard, postList, layout);
                updatePostList(stage);
            });
            buttonBox.getChildren().addAll(btnEditar, btnDeletar);
            postCard.getChildren().add(buttonBox);
        }
        return postCard;
    }
}
