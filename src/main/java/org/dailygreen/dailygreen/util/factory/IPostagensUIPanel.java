package org.dailygreen.dailygreen.util.factory;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.User;

public interface IPostagensUIPanel {
    VBox createLeftSection(Stage stage, User user, VBox layout);
    ScrollPane createCenterSection(Stage stage, User user, VBox layout);
}
