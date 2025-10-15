package org.dailygreen.dailygreen.view.components.postagens;

import java.util.ArrayList;

import org.dailygreen.dailygreen.controller.MainFeedController;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import static org.dailygreen.dailygreen.view.components.postagens.UpdatePostList.updatePostList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateComentariosBox {
    private static final PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    
    public static VBox createComentariosBox(Stage stage, Post post, User user) {
        VBox comentariosBox = new VBox(8);
        comentariosBox.getStyleClass().add("comentarios-box");
        Label comentariosTitulo = new Label("Comentários");
        comentariosTitulo.getStyleClass().add("section-subtitle");
        comentariosBox.getChildren().add(comentariosTitulo);
        VBox commentList = new VBox(5);
        commentList.getStyleClass().add("comment-list");
        ArrayList<Comment> comments = (ArrayList<Comment>) persistenceFacade.findCommentsByPost(post.getID());
        if (comments.isEmpty()) {
            Label noCommentsLabel = new Label("Nenhum comentário ainda.");
            noCommentsLabel.getStyleClass().add("no-comments-label");
            commentList.getChildren().add(noCommentsLabel);
        } else {
            for (Comment comment : comments) {
                Label comentarioLabel = new Label(comment.getAutorEmail() + ": " + comment.getConteudo());
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
            MainFeedController.acaoComentar(post, user.getAccountParticipante().getEmail(), campoComentario);
            updatePostList(stage);
        });
        novoComentarioBox.getChildren().addAll(campoComentario, btnComentar);
        comentariosBox.getChildren().addAll(commentList, novoComentarioBox);
        return comentariosBox;
    }
}
