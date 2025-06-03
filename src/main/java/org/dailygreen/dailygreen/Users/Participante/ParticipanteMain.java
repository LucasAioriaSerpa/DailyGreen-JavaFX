package org.dailygreen.dailygreen.Users.Participante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ParticipanteMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"))
        );

        primaryStage.setTitle("DailyGreen - Participante");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}