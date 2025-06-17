package org.dailygreen.dailygreen.Postagens;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Postagens.Post.Post;
import org.dailygreen.dailygreen.Postagens.utils.DATpost;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.Participante.PerfilViewParticipante;

import java.util.Objects;

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

}
