package org.dailygreen.dailygreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import org.dailygreen.dailygreen.Users.Administrador.MainAdm;

public class Main extends Application{
    private static MainAdm adm = new MainAdm();

    @Override
    public void start(Stage stage) throws IOException {
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    }
}
