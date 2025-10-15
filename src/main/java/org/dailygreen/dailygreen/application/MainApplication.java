package org.dailygreen.dailygreen.application;

import java.io.File;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.dailygreen.dailygreen.controller.MainController;
import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import org.dailygreen.dailygreen.util.Cryptography;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = createMainScene(stage);
        stage.setTitle("DailyGreen - Main Page");
        stage.getIcons().add(loadImage("/dailygreen_icon-32x32.png"));
        stage.setScene(scene);
        stage.show();
    }

    private Scene createMainScene(Stage stage) {
        VBox root = new VBox(10, createLogo(), createButtonBox(stage));
        root.getStyleClass().add("root");
        root.setBackground(new Background(new BackgroundImage(
                loadImage("/IMAGES/BACKGROUNDS/florest-1.jpeg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screen.getWidth() / 2, screen.getHeight() / 2);
        scene.getStylesheets().add(getClass().getResource("/CSS/classMain.css").toExternalForm());
        return scene;
    }

    private ImageView createLogo() {
        ImageView logo = new ImageView(loadImage("/IMAGES/logo-dailygreen.png"));
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        logo.getStyleClass().add("logo");
        return logo;
    }

    private HBox createButtonBox(Stage stage) {
        Button btnAdm = createButton("Administrador", "btn-adm", _ -> MainController.btnAdmin(stage));
        Button btnUser = createButton("Usuário", "btn-user", _ -> MainController.btnUser(stage));
        HBox box = new HBox(10, btnAdm, btnUser);
        box.getStyleClass().add("btns");
        return box;
    }

    private Button createButton(String text, String styleClass, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("btn", styleClass);
        btn.setOnAction(action);
        return btn;
    }

    private Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
    }

    private static void initializeSecurityKey() {
        try {
            File keyFile = new File(Cryptography.getARQUIVO_CHAVE());
            if (!keyFile.exists()) {
                SecretKey key = Cryptography.gerarChave();
                Cryptography.salvarChaveEmArquivo(key, Cryptography.getARQUIVO_CHAVE());
                System.out.println("Nova chave criada e salva com sucesso!");
            } else { System.out.println("Chave existente carregada com sucesso!"); }
        } catch (Exception e) {
            System.err.println("Falha ao inicializar a chave de segurança: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeUser() {
        System.out.println("Inicializando arquivo de usuários...");
        try {
            PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
            if (persistenceFacade.initializePersistence()) {
                User user = new User(Role.USERNOTLOGGED);
                persistenceFacade.saveUser(user);
                System.out.println("Arquivo de usuários carregado com sucesso!");
            } else { 
                System.out.println("Arquivo de usuários criado com sucesso!"); 
            }
        } catch (Exception e) { 
            System.err.println("Erro ao inicializar usuário: " + e.getMessage()); 
        }
    }

    public static void main(String[] args) {
        initializeSecurityKey();
        initializeUser();
        launch(args);
    }
}
