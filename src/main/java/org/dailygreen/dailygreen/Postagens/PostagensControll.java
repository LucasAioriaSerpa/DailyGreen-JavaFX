package org.dailygreen.dailygreen.Postagens;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Postagens.Post.Post;
import org.dailygreen.dailygreen.Postagens.utils.DATpost;

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
}
