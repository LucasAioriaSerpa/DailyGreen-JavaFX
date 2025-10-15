package org.dailygreen.dailygreen.controller;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.repository.impl.AdminJsonRepository;
import org.dailygreen.dailygreen.view.admin.DenunciaView;
import org.dailygreen.dailygreen.view.common.LoginView;

import java.util.Objects;

public class AdmController {
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static void login(String email, String password, Stage stage) {
        if (new AdminJsonRepository().validateLogin(email, password)){
            showAlert("Login realizado com sucesso!", Alert.AlertType.INFORMATION);

            // APÓS O LOGIN, LEVA PARA A PÁGINA DE DENÚNCIA
            DenunciaView denunciaView = new DenunciaView(stage);
            Scene scene = new Scene(denunciaView.getDenunciaView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(Objects.requireNonNull(AdmController.class.getResource("/CSS/classAdm.css")).toExternalForm());
            stage.setScene(scene);
        } else {
            showAlert("Email ou senha inválidos!", Alert.AlertType.ERROR);
        }
    }

    public static void cadastrar(String email, String password1, String password2, Stage stage) throws Exception {
        if (!password1.equals(password2)) {
            showAlert("As senhas estão divergentes!", Alert.AlertType.ERROR);
            return;
        }
        boolean success = new AdminJsonRepository().save(new Administrator(email, password1));
        if (success) {
            showAlert("Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);

            // APÓS O CADASTRO, LEVA PARA A PÁGINA DE LOGIN
            LoginView loginView = new LoginView(stage);
            Scene scene = new Scene(loginView.getView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(AdmController.class.getResource("/CSS/classAdm.css").toExternalForm());
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
