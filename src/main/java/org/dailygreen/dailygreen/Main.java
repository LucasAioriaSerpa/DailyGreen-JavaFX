package org.dailygreen.dailygreen;

import java.io.File;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;
import org.dailygreen.dailygreen.util.Criptografia;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private static String userType = "NONE";
    private static boolean hasloged = false;

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResource("/dailygreen_icon-32x32.png")
        ).toExternalForm()));
        stage.setResizable(false);
        if (!userType.equals("NONE")) {
            switch (userType) {
                case "administrador" -> MainController.btnAdm(stage);
                case "participante" -> MainController.btnUser(stage);
                default -> showMainPage(stage);
            }
        } else {
            showMainPage(stage);
        }
    }

    private void showMainPage(Stage stage) {
        Button btnAdm = new Button("Administrador");
        btnAdm.getStyleClass().add("btn-adm");
        btnAdm.getStyleClass().add("btn");
        btnAdm.setOnAction(_-> {MainController.btnAdm(stage);});
        Button btnUser = new Button("Usuario");
        btnUser.getStyleClass().add("btn-user");
        btnUser.getStyleClass().add("btn");
        btnUser.setOnAction(_ -> {MainController.btnUser(stage);});
        Image image = new Image(Objects.requireNonNull(
                getClass().getResource("/IMAGES/BACKGROUNDS/florest-1.jpeg")).toExternalForm()
        );
        BackgroundImage bg = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        VBox root = new VBox();
        root.getStyleClass().add("root");
        root.setBackground(new Background(bg));
        HBox btnsBox = new HBox(10, btnAdm, btnUser);
        btnsBox.getStyleClass().add("btns");
        root.getChildren().add(btnsBox);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(
                root,
                (int)(screenBounds.getWidth()/2),
                (int)(screenBounds.getHeight()/2)
        );
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/classMain.css")
        ).toExternalForm());
        stage.setTitle("DailyGreen - Main-Page");
        stage.setScene(scene);
        stage.show();
    }

    private static void initializeSecurityKey() {
        try {
            File keyFile = new File(Criptografia.getARQUIVO_CHAVE());
            if (!keyFile.exists()) {
                SecretKey key = Criptografia.gerarChave();
                Criptografia.salvarChaveEmArquivo(key, Criptografia.getARQUIVO_CHAVE());
                System.out.println("Key created and saved successfully!");
            } else {
                System.out.println("Existing key loaded successfully!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize security key: " + e.getMessage(), e);
        }
    }

    private static void initializeUser() {
        if (!DATuser.check()) {
            User user = new User("NONE");
            DATuser.setUser(user);
            System.out.println("User created and saved successfully!");
        } else {
            System.out.println("User loaded successfully!");
            User user = DATuser.getUser();
            userType = user.getType();
        }
    }

    public static void main(String[] args) {
        initializeSecurityKey();
        initializeUser();
        launch(args);
    }
}