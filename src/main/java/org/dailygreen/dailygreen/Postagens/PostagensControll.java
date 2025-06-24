package org.dailygreen.dailygreen.Postagens;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Postagens.Comentario.Comentario;
import org.dailygreen.dailygreen.Postagens.Comentario.ComentarioDAO;
import org.dailygreen.dailygreen.Postagens.Post.Post;
import org.dailygreen.dailygreen.Postagens.Reacao.Reacao;
import org.dailygreen.dailygreen.Postagens.Reacao.RecaoDAO;
import org.dailygreen.dailygreen.Postagens.utils.DATpost;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.Participante.PerfilViewParticipante;

public class PostagensControll {
    public static void sendPost(Stage stage, long id_author , String title, String description) {
        if (title == null || title.isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        } else if (description == null || description.isEmpty()) {
            System.out.println("Description cannot be empty");
        }
        Post post = new Post(id_author, title, description);
        DATpost.adicionarPost(post);
    }

    public static void goPerfil(Stage stage, Participante accountParticipante) {
        stage.getScene().setRoot(new PerfilViewParticipante(stage, accountParticipante).getView());
    }

    // --- NOVOS MÉTODOS DE AÇÃO DOS BOTÕES ---

    public static void acaoComentar(Post post, String autorEmail, TextField campoComentario, Stage stage) {
        String texto = campoComentario.getText().trim();
        if (!texto.isEmpty()) {
            Comentario novoComentario = new Comentario(autorEmail, texto, post.getID());
            ComentarioDAO.salvarComentario(novoComentario);
            stage.getScene().setRoot(new PostagensView(stage).getView());
        }
    }

    public static void acaoReagir(Post post, String tipo, String email, Stage stage) {
        RecaoDAO.salvarOuRemoverReacao(new Reacao(email, post.getID(), tipo));
        stage.getScene().setRoot(new PostagensView(stage).getView());
    }

    public static void acaoEditar(Post post, ListView<VBox> postList, PostagensView view, Stage stage) {
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
                DATpost.atualizarPost(post.getID(), post);
                view.getStage().getScene().setRoot(new PostagensView(stage).getView());
            });
        });
    }

    public static void acaoDeletarPost(Post post, VBox postCard, javafx.scene.control.ListView<VBox> postList, PostagensView view) {
        DATpost.removerPost(post.getID());
        postList.getItems().remove(postCard);
        postList.getItems().clear();
        postList.getItems().addAll((VBox) view.getView().getChildren());
        view.getStage().getScene().setRoot(new PostagensView(view.getStage()).getView());
    }

    public static void acaoPostar(Stage stage, Participante accountParticipante, TextField titleField, TextArea descriptionArea) {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        if (title.isBlank() || description.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao postar");
            alert.setHeaderText("Preencha todos os campos");
            alert.setContentText("Todos os campos devem ser preenchidos.");
            alert.showAndWait();
        } else {
            sendPost(stage, accountParticipante.getID(), title, description);
            stage.getScene().setRoot(new PostagensView(stage).getView());
        }
    }
}
