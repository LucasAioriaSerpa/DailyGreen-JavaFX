package org.dailygreen.dailygreen.Users.Administrador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainAdmController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}