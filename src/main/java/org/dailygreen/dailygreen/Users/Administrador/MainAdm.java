package org.dailygreen.dailygreen.Users.Administrador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.views.LoginView;
import java.io.IOException;

public class MainAdm extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        LoginView loginView = new LoginView(stage);
        Scene scene = new Scene(loginView.getView(),800,500);
        scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login Administrador");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
