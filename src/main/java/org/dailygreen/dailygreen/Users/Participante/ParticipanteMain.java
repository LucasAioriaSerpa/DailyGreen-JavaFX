package org.dailygreen.dailygreen.Users.Participante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ParticipanteMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource("/org/dailygreen/dailygreen/participante_login_screen.fxml"),
                            "Arquivo FXML não encontrado"
                    )
            );

            primaryStage.setTitle("DailyGreen - Participante");
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));

            String cssPath = Objects.requireNonNull(
                    getClass().getResource("/CSS/participante.css"),
                    "Arquivo CSS não encontrado"
            ).toExternalForm();
            scene.getStylesheets().add(cssPath);

            primaryStage.setScene(scene); // Define a cena no palco
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a interface: " + e.getMessage());
            throw e;
        }
    }

    private static void inicializarDadosTeste() {
        try {
            // Limpa dados teste RETIRAR DEPOIS
            //ArquivoParticipante.salvarLista(new ArrayList<>());

//            Participante p1 = new Participante("Abimael", "11111111111", "123");
//            Participante p2 = new Participante("Zé Grillo", "22222222222", "123");
//
//            ArquivoParticipante.adicionarParticipante(p1);
//            ArquivoParticipante.adicionarParticipante(p2);

            ArrayList<Participante> participantes = ArquivoParticipante.lerLista();
            System.out.println("Participantes cadastrados:");

            for(Participante p : participantes) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados de teste: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) {
            inicializarDadosTeste();
        }launch(args);}
}