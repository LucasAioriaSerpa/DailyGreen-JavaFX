package org.dailygreen.dailygreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.dailygreen.dailygreen.MainController;
import org.dailygreen.dailygreen.Users.Administrador.MainAdm;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.Participante.ParticipanteMain;
import org.dailygreen.dailygreen.util.CriptografiaComArquivo;

import javax.crypto.SecretKey;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Button btnAdm = new Button("Administrador");
        btnAdm.setOnAction(e -> {
            try {
                MainAdm adm = new MainAdm();
                adm.start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });
        Button btnUser = new Button("Usuario");
        btnUser.setOnAction(e -> {
           try {
               ParticipanteMain user = new ParticipanteMain();
               user.start(stage);
           } catch (IOException ex) {
               ex.printStackTrace();
               throw new RuntimeException(ex);
           }
        });
        VBox root = new VBox(10, btnAdm, btnUser); root.setStyle("" +
                "-fx-padding: 10; " +
                "-fx-alignment: center;" +
                "");
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("MAIN-PAGE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        CriptografiaComArquivo cript = new CriptografiaComArquivo();
        try {
            SecretKey key;
            File arquivoKey = new File(cript.getARQUIVO_CHAVE());
            if (arquivoKey.exists()) {
                key = CriptografiaComArquivo.lerChaveDeArquivo(cript.getARQUIVO_CHAVE());
                System.out.println(" key read!");
            } else {
                key = CriptografiaComArquivo.gerarChave();
                CriptografiaComArquivo.salvarChaveEmArquivo(key, cript.getARQUIVO_CHAVE());
                System.out.println("key saved!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // ? launch app
        launch(args);
    }
}
