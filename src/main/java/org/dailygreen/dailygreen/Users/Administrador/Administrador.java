package org.dailygreen.dailygreen.Users.Administrador;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Main;

import java.io.IOException;

public class Administrador extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Administrador");

        VBox loginAdmPage = new VBox();
        loginAdmPage.setAlignment(Pos.CENTER);
        loginAdmPage.setPadding(new Insets(25));
        loginAdmPage.setSpacing(10);

        showLoginScene(loginAdmPage);

        Scene loginScene = new Scene(loginAdmPage, 800, 500);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showLoginScene(VBox login){
        login.getChildren().clear();

        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        Text sceneLoginTitle = new Text("LOGIN");
        sceneLoginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginGrid.add(sceneLoginTitle, 0, 0, 2, 1);

        Label emailLogin = new Label("Digite seu email:");
        loginGrid.add(emailLogin, 0, 1);

        TextField loginEmailField = new TextField();
        loginGrid.add(loginEmailField, 1, 1);

        Label loginPassword = new Label("Digite sua senha:");
        loginGrid.add(loginPassword, 0, 2);

        PasswordField passwordBox = new PasswordField();
        loginGrid.add(passwordBox, 1, 2);

        Button buttonEntrar = new Button("ENTRAR");
        HBox hbButtonEntrar = new HBox(10);
        hbButtonEntrar.setAlignment(Pos.BOTTOM_CENTER);
        hbButtonEntrar.getChildren().add(buttonEntrar);
        loginGrid.add(hbButtonEntrar, 1, 4);

        final Text actiontarget = new Text();
        loginGrid.add(actiontarget, 1, 6);

        buttonEntrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
            }
        });

        Hyperlink linkToCadastro = new Hyperlink("FAZER CADASTRO");
        linkToCadastro.setOnAction(e -> showLoginScene(login));
        login.getChildren().addAll(loginGrid, buttonEntrar);
    }

    public void showCadastroScene(VBox cadastro){
        cadastro.getChildren().clear();

        GridPane cadastroGrid = new GridPane();
        cadastroGrid.setAlignment(Pos.CENTER);
        cadastroGrid.setHgap(10);
        cadastroGrid.setVgap(10);

        Text sceneCadastroTitle = new Text("CADASTRO");
        sceneCadastroTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        cadastroGrid.add(sceneCadastroTitle, 0, 0, 2, 1);

        Label emailCadastro = new Label("Digite seu email de cadastro:");
        cadastroGrid.add(emailCadastro, 0, 1);

        TextField emailCadastroField = new TextField();
        cadastroGrid.add(emailCadastroField, 1, 1);

        Label firstPassword = new Label("Digite uma senha:");
        cadastroGrid.add(firstPassword, 0, 2);

        PasswordField firstPasswordField = new PasswordField();
        cadastroGrid.add(firstPasswordField, 1, 2);

        Label secondPassword = new Label("Digite a senha novamente:");
        cadastroGrid.add(secondPassword, 0, 3);

        PasswordField secondPasswordField = new PasswordField();
        cadastroGrid.add(secondPasswordField, 1, 3);

        Button buttonCadastrar = new Button("CADASTRAR");
        HBox hbButtonCadastrar = new HBox(10);
        hbButtonCadastrar.setAlignment(Pos.CENTER);
        hbButtonCadastrar.getChildren().add(buttonCadastrar);
        cadastroGrid.add(hbButtonCadastrar, 1, 5);

        final Text actiontarget = new Text();
        cadastroGrid.add(actiontarget, 1, 6);

        buttonCadastrar.setOnAction(e->{
            actiontarget.setFill(Color.GREEN);
            actiontarget.setText("Cadastro Finalizado!");
        });

        Hyperlink linkToLogin = new Hyperlink("LOGIN");
        linkToLogin.setOnAction(e -> showLoginScene(cadastro));
        cadastro.getChildren().addAll(cadastroGrid,linkToLogin);
    }

}
