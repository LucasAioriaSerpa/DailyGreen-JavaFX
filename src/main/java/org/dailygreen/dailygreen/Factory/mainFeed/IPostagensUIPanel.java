package org.dailygreen.dailygreen.Factory.mainFeed;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.user.User;

public interface IPostagensUIPanel {
    VBox createLeftSection(Stage stage, User user, VBox layout);
    ScrollPane createCenterSection(Stage stage, User user, VBox layout);
}
