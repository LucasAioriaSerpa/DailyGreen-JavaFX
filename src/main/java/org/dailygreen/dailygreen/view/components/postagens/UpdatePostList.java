package org.dailygreen.dailygreen.view.components.postagens;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.view.feed.PostagensView;

public class UpdatePostList {
    public static void updatePostList(Stage stage) { stage.getScene().setRoot(new PostagensView(stage).getView()); }
}
