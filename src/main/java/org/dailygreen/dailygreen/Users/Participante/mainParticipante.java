package org.dailygreen.dailygreen.Users.Participante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainParticipante extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega a tela específica do participante
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/dailygreen/dailygreen/participante_screen.fxml")
        );
        Parent root = loader.load();

        // Configura a janela
        primaryStage.setTitle("Área do Participante");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Inicia a aplicação JavaFX
    }
}