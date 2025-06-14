package org.dailygreen.dailygreen.Postagens;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PostagensMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        PostagensView postagensView = new PostagensView(stage);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(postagensView.getView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
        scene.getStylesheets().add(getClass().getResource("/CSS/classPostagem.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Pagina de postagem");
        stage.show();
    }
    public static void main(String[] args) {launch(args);}
}
