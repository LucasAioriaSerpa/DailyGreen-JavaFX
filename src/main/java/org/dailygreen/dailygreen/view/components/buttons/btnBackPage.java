package org.dailygreen.dailygreen.view.components.buttons;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import static org.dailygreen.dailygreen.util.controller.MainController.mostrarTelaPrincipal;

public class btnBackPage {
    public static Button btnBackMenu(Stage stage) {
        Button btnBackMenu = new Button("Voltar");
        btnBackMenu.getStyleClass().add("btn-back-menu");
        btnBackMenu.setOnMouseClicked(_ -> mostrarTelaPrincipal(stage));
        return btnBackMenu;
    }
}
