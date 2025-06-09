package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SuspensoView {
    private VBox layout;
    private Stage stage;

    public SuspensoView(Stage stage){
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("suspenso-view");
        stage.setTitle("Lista de Suspensos");
        showComponents();
    }

    public void showComponents(){
        GridPane grid = new GridPane();
        grid.getStyleClass().add("suspenso-grid");
    }
}
