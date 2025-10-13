package org.dailygreen.dailygreen.util.controller;

import javafx.stage.Stage;
import org.dailygreen.dailygreen.Main;
import org.dailygreen.dailygreen.ParticipanteMain;
import org.dailygreen.dailygreen.MainAdm;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.view.participante.PerfilViewParticipante;

import java.io.IOException;

public class MainController {
    private static User user;

    public static void mostrarTelaPrincipal(Stage stage) {
        // Reabra a tela principal aqui
        new Main().start(stage);
    }

    public static void btnAdmin(Stage stage) {
        try {
            inicializarUsuario(Role.ADMINISTRADOR);
            iniciarInterfaceAdministrador(stage);
        } catch (IOException ex) {
            tratarErro(ex, null);
        }
    }

    public static void btnUser(Stage stage) {
        try {
            inicializarUsuario(Role.PARTICIPANTE);
            user = DATuser.getUser();
            if (user.isLogged()) {
                inicializarPerfil(stage, user.getAccountParticipante());
                return;
            }
            iniciarInterfaceParticipante(stage);
        } catch (Exception e) { tratarErro(e, null); }
    }
//    public static void btnOrganizador(Stage stage, String emailOrganizador) {
//        try {
//            inicializarUsuario("organizador");
//
//            TelaOrganizador tela = new TelaOrganizador(stage, emailOrganizador);
//            Scene scene = new Scene(tela.getView(), 600, 400);
//
//            stage.setTitle("Login - Organizador");
//            stage.setScene(scene);
//            stage.show();
//
//        } catch (Exception e) {  tratarErro(e, "organizador"); }
//    }
//    // Para abrir a tela de login do organizador (sem precisar do email)
//    public static void btnOrganizadorLogin(Stage stage) {
//        try {
//            inicializarUsuario("organizador");
//            TelaOrganizador tela = new TelaOrganizador(stage); // construtor sem email
//            Scene scene = new Scene(tela.getView(), 600, 400);
//
//            stage.setTitle("Login - Organizador");
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e) { tratarErro(e, "organizador"); }
//    }
//
//    public static void btnOrganizadorCRUD(Stage stage, String emailOrganizador) {
//        try { new TelaCRUDOrganizadores(stage, emailOrganizador); }
//        catch (Exception e) { tratarErro(e, "organizador"); }
//    }
//
//    public static void btnGerenciarOrganizadores(Stage stage, String email) {
//        try {
//            Profile profile = DATuser.getUser();
//
//            new TelaCRUDOrganizadores(stage, email);
//        } catch (Exception e) {
//            tratarErro(e, "organizador");
//        }
//    }

    private static void inicializarUsuario(Role tipo) {
        User user = DATuser.getUser();
        user.setRole(tipo);
        DATuser.setUser(user);
    }

    private static void tratarErro(Exception ex, Role tipoUsuario) {
        User user = DATuser.getUser();
        user.setRole(tipoUsuario);
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

    private static void inicializarPerfil(Stage stage, Participant participant) {
        PerfilViewParticipante perfilView = new PerfilViewParticipante(stage, participant);
        stage.getScene().setRoot(perfilView.getView());
    }

    }





