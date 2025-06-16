package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.dao.DenunciaDAO;
import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        ObservableList<Denuncia> denuncias = FXCollections.observableArrayList();


        // HEADER | FILTRO

        ComboBox<String> filtro = new ComboBox<>();
        filtro.getItems().addAll("ID", "Participante", "Titulo", "Motivo", "Data", "Status");
        filtro.setValue("ID");

        TextField campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o valor que deseja buscar...");

        Button btnBuscar = new Button("FILTRAR");
        btnBuscar.setOnAction(e -> {
            String tipo = filtro.getValue();
            String termo = campoPesquisa.getText();
            List<Denuncia> filtradas = DenunciaDAO.filtrar(tipo, termo);
            denuncias.setAll(filtradas);
        });

        Button btnFormDenuncia = new Button("FORMULÁRIO");
        btnFormDenuncia.setOnAction(e -> {
            DenunciaFormView denunciaFormView = new DenunciaFormView(stage);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(denunciaFormView.getDenunciaFormView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        });

        HBox filtros = new HBox(10, filtro, campoPesquisa, btnBuscar, btnFormDenuncia);
        grid.add(filtros, 0, 0);



        // HEADER | INSERT DENÚNCIAS

        /*Text mainTitle = new Text("LISTA DE DENUNCIAS");
        mainTitle.getStyleClass().add("denuncia-title");
        grid.add(mainTitle, 0, 0,10,1);
        GridPane.setHalignment(mainTitle, HPos.CENTER);
        mainTitle.getStyleClass().add("denuncia-title");

        Label tituloLabel = new Label("Motivo da Denúncia:");
        tituloLabel.getStyleClass().add("label-titulo");

        TextField tituloField = new TextField();
        tituloField.getStyleClass().add("field-titulo");

        Label motivoLabel = new Label("Descrição da Denúncia:");
        motivoLabel.getStyleClass().add("label-motivo");

        TextField motivoField = new TextField();
        motivoField.getStyleClass().add("field-motivo");
        */
        /*Button registrarButton = new Button("REGISTRAR");
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
        registrarButton.getStyleClass().add("button-registrar");*/



        // TABELA DE DENUNCIAS

        TableView<Denuncia> tableView = new TableView<>();
        tableView.getStyleClass().add("table");

        TableColumn<Denuncia, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getId().toString()));

        TableColumn<Denuncia, String> participante = new TableColumn<>("PARTICIPANTE");
        participante.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getParticipante()));

        TableColumn<Denuncia, String> titulo = new TableColumn<>("TITULO");
        titulo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitulo()));

        TableColumn<Denuncia, String> motivo = new TableColumn<>("MOTIVO");
        motivo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getMotivo()));

        TableColumn<Denuncia, String> data = new TableColumn<>("DATA REGISTRO");
        data.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getData().toString()));

        TableColumn<Denuncia, String> status = new TableColumn<>("STATUS");
        status.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus()));

        denuncias.addAll(DenunciaDAO.mostrar());
        tableView.setItems(denuncias);



        // BOTÕES

        TableColumn<Denuncia, Void> buttons = new TableColumn<>("DECISÃO");
        buttons.setCellFactory(coluna -> new javafx.scene.control.TableCell<Denuncia, Void>(){
            private final Button delete = new Button("EXCLUIR");
            private final Button suspend = new Button("SUSPENDER");
            private final Button ban = new Button("BANIR");
            private final HBox buutonBox = new HBox(5, delete, suspend, ban);

            {
                // BOTÃO EXCLUIR

                delete.setOnAction(event -> {
                    Denuncia denuncia = getTableView().getItems().get(getIndex());

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

                    }
                });


                // BOTÃO SUSPENDER

                suspend.setOnAction(event -> {
                    Denuncia denuncia = getTableView().getItems().get(getIndex());

                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmar Decisão");
                    alerta.setHeaderText("Deseja realmente suspender esse usuário?");
                    ButtonType confirmar = new ButtonType("SUSPENDER");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alerta.getButtonTypes().setAll(cancelar, confirmar);

                    Optional<ButtonType> escolha = alerta.showAndWait();
                    if (escolha.isPresent() && escolha.get() == confirmar){
                        denuncia.setStatus("Suspenso");
                        denuncia.setSuspenso(true);

                        List<Denuncia> denuncias = new ArrayList<>(tableView.getItems());
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DenunciaDAO.REPORT_FILE))){
                            oos.writeObject(denuncias);
                        } catch (IOException e){
                            e.printStackTrace();
                        }

                        tableView.refresh();
                    }
                });


                // BOTÃO BANIR

                ban.setOnAction(event -> {
                    Denuncia denuncia = getTableView().getItems().get(getIndex());

                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmar Decisão");
                    alerta.setHeaderText("Deseja realmente banir esse usuário?");
                    ButtonType confirmar = new ButtonType("BANIR");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alerta.getButtonTypes().setAll(cancelar, confirmar);

                    Optional<ButtonType> escolha = alerta.showAndWait();
                    if (escolha.isPresent() && escolha.get() == confirmar){
                        denuncia.setStatus("Banido");
                        denuncia.setSuspenso(true);

                        List<Denuncia> denuncias = new ArrayList<>(tableView.getItems());
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DenunciaDAO.REPORT_FILE))){
                            oos.writeObject(denuncias);
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
                    Denuncia denuncia = getTableView().getItems().get(getIndex());
                    if (denuncia.getSuspenso() || denuncia.getBanido()){
                        setGraphic(null);
                    } else {
                        setGraphic(buutonBox);
                    }
                }
            }
        });


        // ADICIONANDO ITENS A TABELA E COLOCANDO ELA NO GRID

        tableView.getColumns().addAll(id, participante, titulo, motivo, data, status, buttons);
        grid.add(tableView, 0, 10,10,10);

        layout.getChildren().add(grid);
    }

    public VBox getDenunciaView() {
        return layout;
    }
}
