package org.dailygreen.dailygreen.util.factory;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.User;

public interface IPostagensUIPanel {
    VBox createLeftSection(Stage stage, User user);
}
