package org.dailygreen.dailygreen;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.MainAdm;
import org.dailygreen.dailygreen.Users.Participante.ParticipanteMain;

import java.io.IOException;

public class MainController {

    public static void btnAdm(Stage stage) {
        try {
            MainAdm adm = new MainAdm();
            adm.start(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void btnUser(Stage stage) {
        try {
            ParticipanteMain user = new ParticipanteMain();
            user.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}