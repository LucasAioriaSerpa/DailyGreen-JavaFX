package org.dailygreen.dailygreen;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.util.DAT.ParticipanteDAT;
import org.dailygreen.dailygreen.view.participante.LoginViewParticipante;

import java.util.Objects;

public class ParticipanteMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginViewParticipante loginView = new LoginViewParticipante(primaryStage);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(
                    loginView.getView(),
                    (int)(screenBounds.getWidth() / 2),
                    (int)(screenBounds.getHeight() / 2)
            );
            String cssPath = Objects.requireNonNull(
                    getClass().getResource("/CSS/participante.css"),
                    "Arquivo CSS não encontrado"
            ).toExternalForm();
            scene.getStylesheets().add(cssPath);
            primaryStage.setTitle("DailyGreen - Login Participante");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a interface: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inicializarDadosTeste() {
        try {
            // Exemplo para testes. Comente ou apague depois.
            //ParticipanteDAT.salvarLista(new ArrayList<>());

            // Participante p1 = new Participante("Abimael", "11111111111", "123");
            // Participante p2 = new Participante("Zé Grillo", "22222222222", "123");
            // ParticipanteDAT.adicionarParticipante(p1);
            // ParticipanteDAT.adicionarParticipante(p2);

            System.out.println("Participantes cadastrados:");
            for (Participante p : ParticipanteDAT.lerLista()) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados de teste: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) {inicializarDadosTeste();}
        launch(args);
    }
}