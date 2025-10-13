package org.dailygreen.dailygreen.view.components.postagens;

import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.model.user.User;

import java.util.ArrayList;

import static org.dailygreen.dailygreen.view.components.postagens.CreatePostCard.createPostCard;

public class CreatePostList {
    public static ListView<VBox> createPostList(Stage stage, User user, VBox layout) {
        ListView<VBox> postList = new ListView<>();
        postList.getStyleClass().add("post-list");
        VBox.setVgrow(postList, Priority.ALWAYS);
        ArrayList<Participant> participantList = ParticipanteDAT.lerLista();
        for (Post post : DATpost.lerLista()) {
            VBox postCard = createPostCard(stage, layout, post, participantList, postList, user);
            postList.getItems().add(postCard);
        }
        return postList;
    }
}
