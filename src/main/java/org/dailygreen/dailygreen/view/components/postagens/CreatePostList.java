package org.dailygreen.dailygreen.view.components.postagens;

import java.util.ArrayList;

import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import static org.dailygreen.dailygreen.view.components.postagens.CreatePostCard.createPostCard;

import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatePostList {
    private static final PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    
    public static ListView<VBox> createPostList(Stage stage, User user, VBox layout) {
        ListView<VBox> postList = new ListView<>();
        postList.getStyleClass().add("post-list");
        VBox.setVgrow(postList, Priority.ALWAYS);
        ArrayList<Participant> participantList = (ArrayList<Participant>) persistenceFacade.findAllParticipants();
        for (Post post : persistenceFacade.findAllPosts()) {
            VBox postCard = createPostCard(stage, layout, post, participantList, postList, user);
            postList.getItems().add(postCard);
        }
        return postList;
    }
}
