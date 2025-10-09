package org.dailygreen.dailygreen.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.dailygreen.dailygreen.Postagens.Post;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.controller.PostagensControll;

import java.util.ArrayList;

public class CreatePostCard {
    public static VBox createPostCard(Post post, ArrayList<Participante> participanteList, ListView<VBox> postList, User user) {
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
}
