package org.dailygreen.dailygreen;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.crypto.SecretKey;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.dailygreen.dailygreen.Users.Administrador.MainAdm;
import org.dailygreen.dailygreen.Users.Participante.ParticipanteMain;
import org.dailygreen.dailygreen.util.Criptografia;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.application.Application;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Button btnAdm = new Button("Administrador");
        btnAdm.getStyleClass().add("btn-adm");
        btnAdm.getStyleClass().add("btn");
        btnAdm.setOnAction(_ -> {
            try {
                MainAdm adm = new MainAdm();
                adm.start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });
        Button btnUser = new Button("Usuario");
        btnUser.getStyleClass().add("btn-user");
        btnUser.getStyleClass().add("btn");
        btnUser.setOnAction(_ -> {
            ParticipanteMain user = new ParticipanteMain();
            user.start(stage);
        });
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/IMAGES/BACKGROUNDS/florest-1.jpeg")).toExternalForm());
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        VBox root = new VBox();
        root.getStyleClass().add("root");
        root.setBackground(new Background(bg));
        HBox btnsBox = new HBox(10, btnAdm, btnUser);
        btnsBox.getStyleClass().add("btns");
        root.getChildren().add(btnsBox);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/CSS/classMain.css")).toExternalForm());
        stage.setTitle("DailyGreen - Main-Page");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResource("/dailygreen_icon-32x32.png")).toExternalForm()));
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            SecretKey key;
            File arquivoKey = new File(Criptografia.getARQUIVO_CHAVE());
            if (arquivoKey.exists()) {
                key = Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE());
                System.out.println(" key read!");
            } else {
                key = Criptografia.gerarChave();
                Criptografia.salvarChaveEmArquivo(key, Criptografia.getARQUIVO_CHAVE());
                System.out.println("key saved!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // ? launch app
        launch(args);
    }
}
