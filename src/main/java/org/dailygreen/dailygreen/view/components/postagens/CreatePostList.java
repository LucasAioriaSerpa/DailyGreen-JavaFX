package org.dailygreen.dailygreen.view.components.postagens;

import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Postagens.Post;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.DAT.DATpost;
import org.dailygreen.dailygreen.util.DAT.ParticipanteDAT;

import java.util.ArrayList;

import static org.dailygreen.dailygreen.view.components.postagens.CreatePostCard.createPostCard;

public class CreatePostList {
    public static ListView<VBox> createPostList(Stage stage, User user, VBox layout) {
        ListView<VBox> postList = new ListView<>();
        postList.getStyleClass().add("post-list");
        VBox.setVgrow(postList, Priority.ALWAYS);
        ArrayList<Participante> participanteList = ParticipanteDAT.lerLista();
        for (Post post : DATpost.lerLista()) {
            VBox postCard = createPostCard(stage, layout, post, participanteList, postList, user);
            postList.getItems().add(postCard);
        }
        return postList;
    }
}
