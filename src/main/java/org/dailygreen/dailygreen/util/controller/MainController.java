package org.dailygreen.dailygreen.util.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Main;
import org.dailygreen.dailygreen.ParticipanteMain;
import org.dailygreen.dailygreen.MainAdm;
import org.dailygreen.dailygreen.view.organizador.TelaCRUDOrganizadores;
import org.dailygreen.dailygreen.view.organizador.TelaOrganizador;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.view.participante.PerfilViewParticipante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.DAT.DATuser;

import java.io.IOException;

public class MainController {
    private static User user;

    public static void mostrarTelaPrincipal(Stage stage) {
        // Reabra a tela principal aqui
        new Main().start(stage);
    }

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
    public static void btnOrganizador(Stage stage, String emailOrganizador) {
        try {
            inicializarUsuario("organizador");

            TelaOrganizador tela = new TelaOrganizador(stage, emailOrganizador);
            Scene scene = new Scene(tela.getView(), 600, 400);

            stage.setTitle("Login - Organizador");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            tratarErro(e, "organizador");
        }
    }
    // Para abrir a tela de login do organizador (sem precisar do email)
    public static void btnOrganizadorLogin(Stage stage) {
        try {
            inicializarUsuario("organizador");
            TelaOrganizador tela = new TelaOrganizador(stage); // construtor sem email
            Scene scene = new Scene(tela.getView(), 600, 400);

            stage.setTitle("Login - Organizador");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            tratarErro(e, "organizador");
        }
    }

    // Para abrir a tela de CRUD do organizador (com email)
    public static void btnOrganizadorCRUD(Stage stage, String emailOrganizador) {
        try {
            new TelaCRUDOrganizadores(stage, emailOrganizador);
        } catch (Exception e) {
            tratarErro(e, "organizador");
        }
    }

    public static void btnGerenciarOrganizadores(Stage stage, String email) {
        try {
            User user = DATuser.getUser();

            new TelaCRUDOrganizadores(stage, email);
        } catch (Exception e) {
            tratarErro(e, "organizador");
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





