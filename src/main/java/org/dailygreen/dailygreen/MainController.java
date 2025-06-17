package org.dailygreen.dailygreen;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.MainAdm;
import org.dailygreen.dailygreen.Users.Participante.EditarPerfilViewParticipante;
import org.dailygreen.dailygreen.Users.Participante.Participante;
import org.dailygreen.dailygreen.Users.Participante.ParticipanteMain;
import org.dailygreen.dailygreen.Users.Participante.PerfilViewParticipante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;

import java.io.IOException;

public class MainController {
    private static User user;
    public static void btnAdmin(Stage stage) {
        try {
            inicializarUsuario("administrador");
            iniciarInterfaceAdministrador(stage);
        } catch (IOException ex) {
            tratarErro(ex, "NONE");
        }
    }

    public static void btnUser(Stage stage) {
        try {
            inicializarUsuario("participante");
            user = DATuser.getUser();
            if (user.isLogged()) {
                inicializarPerfil(stage, user.getAccountParticipante());
                return;
            }
            iniciarInterfaceParticipante(stage);
        } catch (Exception e) {
            tratarErro(e, "NONE");
        }
    }

    private static void inicializarUsuario(String tipo) {
        User user = DATuser.getUser();
        user.setType(tipo);
        DATuser.setUser(user);
    }

    private static void tratarErro(Exception ex, String tipoUsuario) {
        User user = DATuser.getUser();
        user.setType(tipoUsuario);
        DATuser.setUser(user);
        ex.printStackTrace();
        throw new RuntimeException(ex);
    }

    private static void iniciarInterfaceAdministrador(Stage stage) throws IOException {
        MainAdm adm = new MainAdm();
        adm.start(stage);
    }

    private static void iniciarInterfaceParticipante(Stage stage) {
        ParticipanteMain userPmain = new ParticipanteMain();
        userPmain.start(stage);
    }

    private static void inicializarPerfil(Stage stage, Participante participante) {
        PerfilViewParticipante perfilView = new PerfilViewParticipante(stage, participante);
        stage.getScene().setRoot(perfilView.getView());
    }

}