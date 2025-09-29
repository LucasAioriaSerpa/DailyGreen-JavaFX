package org.dailygreen.dailygreen.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.util.controller.AdmController;
import org.dailygreen.dailygreen.view.administrador.CadastroView;

import java.util.Objects;

public class LoginView {
    private VBox layout;
    private Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/classAdm.css")
        ).toExternalForm());
        stage.setTitle("Login Administrador");
        showComponents();
    }

    private void showComponents() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("screen");

        Text title = new Text("LOGIN");
        title.getStyleClass().add("title");
        grid.add(title, 0, 0,2,1);

        Label emailLabel = new Label("Digite seu email:");
        emailLabel.getStyleClass().add("label-email");
        grid.add(emailLabel, 0, 1);

        TextField emailField = new TextField();
        emailField.getStyleClass().add("text-field");
        grid.add(emailField, 0, 2);

        Label passwordLabel = new Label("Digite sua senha:");
        passwordLabel.getStyleClass().add("label-password");
        grid.add(passwordLabel, 0, 3);

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field");
        grid.add(passwordField, 0, 4);

        Hyperlink linkToCadastro = new Hyperlink("Ainda não possui login? Cadastre-se aqui!");
        linkToCadastro.setOnAction(_ -> {
            CadastroView cadastroView = new CadastroView(stage);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(cadastroView.getView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        });
        linkToCadastro.getStyleClass().add("hyperlink");

        HBox linkToCadastroBox = new HBox(linkToCadastro);
        linkToCadastroBox.getStyleClass().add("box-link");
        grid.add(linkToCadastroBox, 0, 5);

        Button loginButton = new Button("ENTRAR");
        loginButton.setOnAction(_ -> {
            AdmController.login(emailField.getText(), passwordField.getText(), stage);
        });
        loginButton.getStyleClass().add("button");

        HBox buttonBox = new HBox(loginButton);
        buttonBox.getStyleClass().add("button-box");
        grid.add(buttonBox, 0, 6);

        layout.getChildren().add(grid);
    }

    public VBox getView(){
        return layout;
    }
}
