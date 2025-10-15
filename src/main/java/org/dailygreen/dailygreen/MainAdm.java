package org.dailygreen.dailygreen;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.dailygreen.dailygreen.view.common.LoginView;

import java.io.IOException;

public class MainAdm extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        LoginView loginView = new LoginView(stage);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(loginView.getView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
        scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login Administrador");
        stage.show();
    }
    public static void main(String[] args) {launch(args);}
}
