package org.dailygreen.dailygreen.controller;

import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import org.dailygreen.dailygreen.view.participante.PerfilViewParticipante;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFeedController {
    private static final PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    
    public static void sendPost(long id_author , String title, String description) {
        if (title == null || title.isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        } else if (description == null || description.isEmpty()) {
            System.out.println("Description cannot be empty");
        }
        Post post = new Post(id_author, title, description);
        persistenceFacade.savePost(post);
    }

    public static void goPerfil(Stage stage, Participant accountParticipant) {
        stage.getScene().setRoot(new PerfilViewParticipante(stage, accountParticipant).getView());
    }

    // --- NOVOS MÉTODOS DE AÇÃO DOS BOTÕES ---

    public static void acaoComentar(Post post, String autorEmail, TextField campoComentario) {
        String texto = campoComentario.getText().trim();
        if (!texto.isEmpty()) {
            Comment novoComment = new Comment(autorEmail, texto, post.getID());
            persistenceFacade.saveComment(novoComment);
        }
    }

    public static void acaoReagir(Post post, String tipo, String email) { 
        persistenceFacade.saveOrToggleReaction(new Reaction(email, post.getID(), tipo)); 
    }

    public static void acaoEditar(Post post) {
        TextInputDialog dialog = new TextInputDialog(post.getTitulo());
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
                persistenceFacade.updatePost(post);
            });
        });
    }

    public static void acaoDeletarPost(Post post, VBox postCard, ListView<VBox> postList, VBox view) {
        persistenceFacade.deletePostById(post.getID());
        for (Reaction reaction : persistenceFacade.findAllReactions()) { 
            if (reaction.getIdPost() == post.getID()) { 
                persistenceFacade.deleteReaction(reaction.getAutorEmail(), reaction.getIdPost()); 
            } 
        }
        postList.getItems().remove(postCard);
        postList.getItems().clear();
        postList.getItems().addAll((VBox) view.getChildren());
    }

    public static void acaoPostar(Participant accountParticipant, TextField titleField, TextArea descriptionArea) {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        if (title.isBlank() || description.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao postar");
            alert.setHeaderText("Preencha todos os campos");
            alert.setContentText("Todos os campos devem ser preenchidos.");
            alert.showAndWait();
        } else {
            sendPost(accountParticipant.getID(), title, description);
        }
    }
}
