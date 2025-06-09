package org.dailygreen.dailygreen.Users.Administrador.dao;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.controller.AdmController;
import org.dailygreen.dailygreen.Users.Administrador.views.DenunciaView;
import org.dailygreen.dailygreen.Users.Administrador.views.LoginView;

public class AdmDAO {

    public static void login(String email, String password, Stage stage) {
        if (AdmController.validarLogin(email, password)){
            showAlert("Login realizado com sucesso!", Alert.AlertType.INFORMATION);

            // APÓS O LOGIN, LEVA PARA A PÁGINA DE DENÚNCIA
            DenunciaView denunciaView = new DenunciaView(stage);
            Scene scene = new Scene(denunciaView.getDenunciaView(), 800, 500);
            scene.getStylesheets().add(AdmDAO.class.getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        } else {
            showAlert("Email ou senha inválidos!", Alert.AlertType.ERROR);
        }
    }

    public static void cadastrar(String email, String password1, String password2, Stage stage) {
        if (!password1.equals(password2)) {
            showAlert("As senhas estão divergentes!", Alert.AlertType.ERROR);
            return;
        }
        boolean success = AdmController.salvarNovoAdm(email,password1);
        if (success) {
            showAlert("Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);

            // APÓS O CADASTRO, LEVA PARA A PÁGINA DE LOGIN
            LoginView loginView = new LoginView(stage);
            Scene scene = new Scene(loginView.getView(), 800, 500);
            scene.getStylesheets().add(AdmDAO.class.getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        } else {
            showAlert("Erro ao cadastrar!", Alert.AlertType.ERROR);
        }
    }

    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
