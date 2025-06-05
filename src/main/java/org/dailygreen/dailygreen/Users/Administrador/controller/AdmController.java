package org.dailygreen.dailygreen.Users.Administrador.controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.models.AdmValidacao;
import org.dailygreen.dailygreen.Users.Administrador.views.DenunciaView;
import org.dailygreen.dailygreen.Users.Administrador.views.LoginView;

public class AdmController {

    public static void login(String email, String password, Stage stage) {
        if (AdmValidacao.validarLogin(email, password)){
            showAlert("Login realizado com sucesso!", Alert.AlertType.INFORMATION);

            DenunciaView denunciaView = new DenunciaView(stage);
            Scene scene = new Scene(denunciaView.getDenunciaView(), 800, 500);
            scene.getStylesheets().add(AdmController.class.getResource("/CSS/classAdm.css").toExternalForm());
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
        boolean success = AdmValidacao.salvarNovoAdm(email,password1);
        if (success) {
            showAlert("Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);
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
