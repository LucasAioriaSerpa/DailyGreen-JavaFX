package org.dailygreen.dailygreen.controller;

import java.io.IOException;
import java.util.List;

import org.dailygreen.dailygreen.MainAdm;
import org.dailygreen.dailygreen.ParticipanteMain;
import org.dailygreen.dailygreen.application.MainApplication;
import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import org.dailygreen.dailygreen.view.participante.PerfilViewParticipante;

import javafx.stage.Stage;

public class MainController {
    private static User user;
    private static final PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();

    public static void mostrarTelaPrincipal(Stage stage) { new MainApplication().start(stage); }

    public static void btnAdmin(Stage stage) {
        try {
            inicializarUsuario(Role.ADMINISTRADOR);
            iniciarInterfaceAdministrador(stage);
        } catch (IOException ex) { tratarErro(ex, Role.ADMINISTRADOR); }
    }

    public static void btnUser(Stage stage) {
        try {
            inicializarUsuario(Role.PARTICIPANTE);
            user = obterOuCriarPrimeiroUsuario();
            if (user.isLogged()) {
                inicializarPerfil(stage, user.getAccountParticipante());
                return;
            }
            iniciarInterfaceParticipante(stage);
        } catch (Exception e) { tratarErro(e, Role.PARTICIPANTE); }
    }

    private static void inicializarUsuario(Role tipo) {
        User usuario = obterOuCriarPrimeiroUsuario();
        usuario.setRole(tipo);
        persistenceFacade.updateUser(usuario);
        System.out.println("[INFO] Usuário inicializado como: " + tipo);
    }

    private static User obterOuCriarPrimeiroUsuario() {
        List<User> users = persistenceFacade.findAllUsers();
        if (users.isEmpty()) {
            System.out.println("[INFO] Nenhum usuário encontrado — criando novo usuário padrão...");
            User novo = new User(null);
            persistenceFacade.saveUser(novo);
            return novo;
        }
        return users.getFirst();
    }

    private static void tratarErro(Exception ex, Role tipoUsuario) {
        System.err.println("[ERRO] Falha em operação de MainController: " + ex.getMessage());
        ex.printStackTrace();
        try {
            User usuario = obterOuCriarPrimeiroUsuario();
            usuario.setRole(tipoUsuario);
            persistenceFacade.updateUser(usuario);
        } catch (Exception e) { System.err.println("[FALHA] Erro adicional ao tentar recuperar estado do usuário: " + e.getMessage()); }
        throw new RuntimeException(ex);
    }

    private static void iniciarInterfaceAdministrador(Stage stage) throws IOException { new MainAdm().start(stage); }

    private static void iniciarInterfaceParticipante(Stage stage) { new ParticipanteMain().start(stage); }

    private static void inicializarPerfil(Stage stage, Participant participant) {
        PerfilViewParticipante perfilView = new PerfilViewParticipante(stage, participant);
        stage.getScene().setRoot(perfilView.getView());
    }
}
