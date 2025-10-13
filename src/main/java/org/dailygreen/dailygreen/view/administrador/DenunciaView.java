package org.dailygreen.dailygreen.view.administrador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.moderation.Report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        ObservableList<Report> reports = FXCollections.observableArrayList();


        // HEADER | FILTRO

        ComboBox<String> filtro = new ComboBox<>();
        filtro.getItems().addAll("ID", "Participante", "Titulo", "Motivo", "Data", "Status");
        filtro.setValue("ID");
        filtro.getStyleClass().add("combo-filtro");

        TextField campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o valor que deseja buscar...");
        campoPesquisa.getStyleClass().add("campo-pesquisa");

        Button btnBuscar = new Button("FILTRAR");
        btnBuscar.setOnAction(e -> {
            String tipo = filtro.getValue();
            String termo = campoPesquisa.getText();
            List<Report> filtradas = DenunciaDAO.filtrar(tipo, termo);
            reports.setAll(filtradas);
        });
        btnBuscar.getStyleClass().add("botao-filtrar");

        Button btnFormDenuncia = new Button("DENUNCIAR");
        btnFormDenuncia.setOnAction(e -> {
            DenunciaFormView denunciaFormView = new DenunciaFormView(stage);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(denunciaFormView.getDenunciaFormView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(getClass().getResource("/CSS/formDenuncia.css").toExternalForm());
            stage.setScene(scene);
        });
        btnFormDenuncia.getStyleClass().add("botao-form-denuncia");

        HBox filtros = new HBox(10, filtro, campoPesquisa, btnBuscar, btnFormDenuncia);
        filtros.getStyleClass().add("filtros-container");
        grid.add(filtros, 0, 0);



        // TABELA DE DENUNCIAS

        TableView<Report> tableView = new TableView<>();
        tableView.getStyleClass().add("table-view-denuncia");

        TableColumn<Report, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getId().toString()));

        TableColumn<Report, String> participante = new TableColumn<>("EMAIL DO PARTICIPANTE");
        participante.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getParticipante()));

        TableColumn<Report, String> titulo = new TableColumn<>("TITULO");
        titulo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitulo()));

        TableColumn<Report, String> motivo = new TableColumn<>("MOTIVO");
        motivo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getMotivo()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        TableColumn<Report, String> data = new TableColumn<>("DATA REGISTRO");
        data.setCellValueFactory(cell -> {
            LocalDate dataOriginal = cell.getValue().getData();
            String dataFormatada = dataOriginal.format(formatter);
            return new javafx.beans.property.SimpleStringProperty(dataFormatada);
        });

        TableColumn<Report, String> status = new TableColumn<>("STATUS");
        status.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus()));

        reports.addAll(DenunciaDAO.mostrar());
        tableView.setItems(reports);



        // BOTÕES

        TableColumn<Report, Void> buttons = new TableColumn<>("DECISÃO");
        buttons.setCellFactory(coluna -> new javafx.scene.control.TableCell<Report, Void>(){
            private final Button delete = new Button("EXCLUIR");
            private final Button suspend = new Button("SUSPENDER");
            private final Button ban = new Button("BANIR");
            private final HBox buutonBox = new HBox(5, delete, suspend, ban);

            {
                // BOTÃO EXCLUIR

                delete.setOnAction(event -> {
                    Report report = getTableView().getItems().get(getIndex());

                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmar Decisão");
                    alerta.setHeaderText("Deseja realmente excluir essa denúncia?");
                    ButtonType confirmar = new ButtonType("CONFIRMAR");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alerta.getButtonTypes().setAll(cancelar, confirmar);

                    // Se confirmar
                    Optional<ButtonType> escolha = alerta.showAndWait();
                    if (escolha.isPresent() && escolha.get() == confirmar){
                        DenunciaDAO.removerPorEmail(report.getParticipante());
                        reports.remove(report);
                    }
                });


                // BOTÃO SUSPENDER

                suspend.setOnAction(event -> {
                    Report report = getTableView().getItems().get(getIndex());

                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmar Decisão");
                    alerta.setHeaderText("Deseja realmente suspender esse usuário?");
                    ButtonType confirmar = new ButtonType("SUSPENDER");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alerta.getButtonTypes().setAll(cancelar, confirmar);

                    Optional<ButtonType> escolha = alerta.showAndWait();
                    if (escolha.isPresent() && escolha.get() == confirmar){
                        String email = report.getParticipante();
                        List<Report> reports = new ArrayList<>(tableView.getItems());
                        for (Report d : reports){
                            if (d.getParticipante().equalsIgnoreCase(email)){
                                d.setStatus("Suspenso");
                                d.setSuspenso(true);
                            }
                        }

                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DenunciaDAO.REPORT_FILE))){
                            oos.writeObject(reports);
                        } catch (IOException e){
                            e.printStackTrace();
                        }

                        tableView.refresh();
                    }
                });

                delete.setId("delete");
                suspend.setId("suspend");
                ban.setId("ban");


                // BOTÃO BANIR

                ban.setOnAction(event -> {
                    Report report = getTableView().getItems().get(getIndex());

                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmar Decisão");
                    alerta.setHeaderText("Deseja realmente banir esse usuário?");
                    ButtonType confirmar = new ButtonType("BANIR");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alerta.getButtonTypes().setAll(cancelar, confirmar);

                    Optional<ButtonType> escolha = alerta.showAndWait();
                    if (escolha.isPresent() && escolha.get() == confirmar){
                        String email = report.getParticipante();
                        List<Report> reports = new ArrayList<>(tableView.getItems());
                        for (Report d : reports){
                            if (d.getParticipante().equalsIgnoreCase(email)){
                                d.setStatus("Banido");
                                d.setSuspenso(true);
                            }
                        }

                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DenunciaDAO.REPORT_FILE))){
                            oos.writeObject(reports);
                        } catch (IOException e){
                            e.printStackTrace();
                        }

                        tableView.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Report report = getTableView().getItems().get(getIndex());
                    if (report.getSuspenso() || report.getBanido()){
                        setGraphic(null);
                    } else {
                        setGraphic(buutonBox);
                    }
                }
            }
        });


        // ADICIONANDO ITENS A TABELA E COLOCANDO ELA NO GRID

        tableView.getColumns().addAll(id, participante, titulo, motivo, data, status, buttons);
        grid.add(tableView, 0, 1);

        layout.getChildren().add(grid);
    }

    public VBox getDenunciaView() {
        return layout;
    }
}
