package org.dailygreen.dailygreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.view.organizador.TelaOrganizador;

public class MainOrganizador extends Application {

    @Override
    public void start(Stage stage) {
        TelaOrganizador tela = new TelaOrganizador(stage, ""); // email vazio para iniciar

        Scene scene = new Scene(tela.getView(), 600, 400);

        stage.setTitle("Login - Organizador");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
