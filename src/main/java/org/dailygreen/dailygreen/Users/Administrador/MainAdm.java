package org.dailygreen.dailygreen.Users.Administrador;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

public class MainAdm extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        Administrador adm = new Administrador();
        adm.start(stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
