package org.dailygreen.dailygreen;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.MainAdm;
import org.dailygreen.dailygreen.Users.Participante.ParticipanteMain;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;

import java.io.IOException;

public class MainController {

    public static void btnAdm(Stage stage) {
        try {
            User user = DATuser.getUser();
            user.setType("administrador");
            DATuser.setUser(user);
            MainAdm adm = new MainAdm();
            adm.start(stage);
        } catch (IOException ex) {
            User user = DATuser.getUser();
            user.setType("NONE");
            DATuser.setUser(user);
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void btnUser(Stage stage) {
        try {
            User user = DATuser.getUser();
            user.setType("participante");
            DATuser.setUser(user);
            ParticipanteMain userPmain = new ParticipanteMain();
            userPmain.start(stage);
        } catch (Exception e) {
            User user = DATuser.getUser();
            user.setType("NONE");
            DATuser.setUser(user);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}