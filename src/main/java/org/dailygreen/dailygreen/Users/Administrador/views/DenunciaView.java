package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.dao.DenunciaDAO;
import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;


import java.util.Date;


public class DenunciaView {
    private VBox layout;
    private Stage stage;

    public DenunciaView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("denuncia-view");
        stage.setTitle("Lista de Denuncias");
        showComponents();
    }

    public void showComponents(){
        GridPane grid = new GridPane();
        grid.getStyleClass().add("denuncia-grid");

        Text mainTitle = new Text("LISTA DE DENUNCIAS");
        mainTitle.getStyleClass().add("denuncia-title");
        grid.add(mainTitle, 0, 0, 4, 1);

        Label tituloLabel = new Label("Motivo da Denúncia:");
        grid.add(tituloLabel,0,2);

        TextField tituloField = new TextField();
        grid.add(tituloField,1,2);

        Label motivoLabel = new Label("Descrição da Denúncia:");
        grid.add(motivoLabel,3,2);

        TextField motivoField = new TextField();
        grid.add(motivoField,4,2);

        ObservableList<Denuncia> denuncias = FXCollections.observableArrayList();

        Button registrarButton = new Button("REGISTRAR");
        registrarButton.setOnAction(e -> {
            String tituloValue = tituloField.getText();
            String motivoValue = motivoField.getText();

            if (!tituloValue.isEmpty() && !motivoValue.isEmpty()){
                Denuncia denuncia = new Denuncia(tituloValue, motivoValue);
                DenunciaDAO.registrar(denuncia);
                denuncias.add(denuncia);
                tituloField.clear();
                motivoField.clear();
            }
        });
        grid.add(registrarButton,6,2);

        // TABELA DE DENUNCIAS
        TableView<Denuncia> tableView = new TableView<>();

        TableColumn<Denuncia, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<Denuncia, Integer>("id"));

        TableColumn<Denuncia, String> titulo = new TableColumn<>("TITULO");
        titulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));

        TableColumn<Denuncia, String> motivo = new TableColumn<>("MOTIVO");
        motivo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMotivo()));

        denuncias.addAll(DenunciaDAO.mostrar());
        tableView.setItems(denuncias);

        TableColumn<Denuncia, Void> analise = new TableColumn<>("AÇÃO");
        analise.setCellFactory(coluna -> new javafx.scene.control.TableCell<Denuncia, Void>() {
            private final Hyperlink link = new Hyperlink("Analisar");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(link);
                }
            }
        });

        tableView.getColumns().addAll(titulo, motivo, analise);
        grid.add(tableView, 0, 5,5,10);
        layout.getChildren().add(grid);
    }

    public VBox getDenunciaView() {
        return layout;
    }
}
