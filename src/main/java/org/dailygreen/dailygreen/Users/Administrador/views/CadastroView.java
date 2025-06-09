package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.dao.AdmDAO;

public class CadastroView {
    private VBox layout;
    private Stage stage;

    public CadastroView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        stage.setTitle("Cadastro Administrador");
        showComponents();
    }

    private void showComponents() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("screen");

        Text title = new Text("CADASTRO");
        title.getStyleClass().add("title");
        grid.add(title, 0, 0,2,1);

        Label emailLabel = new Label("Digite seu e-mail:");
        emailLabel.getStyleClass().add("label-email");
        grid.add(emailLabel, 0, 1);

        TextField emailField = new TextField();
        grid.add(emailField, 0, 2);

        Label passwordLabel1 = new Label("Digite uma senha:");
        passwordLabel1.getStyleClass().add("label-password");
        grid.add(passwordLabel1, 0, 3);

        PasswordField passwordField1 = new PasswordField();
        grid.add(passwordField1, 0, 4);

        Label passwordLabel2 = new Label("Digite a senha novamente:");
        passwordLabel2.getStyleClass().add("label-password");
        grid.add(passwordLabel2, 0, 5);

        PasswordField passwordField2 = new PasswordField();
        grid.add(passwordField2, 0, 6);

        Hyperlink linkToLogin = new Hyperlink("Já possui login? Clique aqui!");
        linkToLogin.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            Scene scene = new Scene(loginView.getView(), 800, 500);
            scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        });

        HBox linkToLoginBox = new HBox(linkToLogin);
        linkToLoginBox.getStyleClass().add("box-link");
        grid.add(linkToLoginBox, 0, 7);

        Button cadastrarButton = new Button("CADASTRAR");
        cadastrarButton.setOnAction(event -> {
            AdmDAO.cadastrar(emailField.getText(), passwordField1.getText(), passwordField2.getText(), stage);
        });
        HBox buttonBox = new HBox(cadastrarButton);
        buttonBox.getStyleClass().add("button-box");
        grid.add(buttonBox, 0, 8);

        layout.getChildren().add(grid);

    }

    public VBox getView(){
        return layout;
    }

}
