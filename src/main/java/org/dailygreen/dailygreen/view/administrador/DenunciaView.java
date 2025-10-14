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
import org.dailygreen.dailygreen.repository.impl.ReportRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DenunciaView {
    private final VBox layout;
    private final Stage stage;
    private final ReportRepository reportRepository;
    private final ObservableList<Report> reports;

    public DenunciaView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        this.reportRepository = new ReportRepository();
        this.reports = FXCollections.observableArrayList();
        layout.getStyleClass().add("denuncia-view");
        stage.setTitle("Lista de Denúncias");
        showComponents();
    }

    private void showComponents() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("denuncia-grid");

        ComboBox<String> filtro = new ComboBox<>();
        filtro.getItems().addAll("ID", "Participante", "Titulo", "Motivo", "Data", "Status");
        filtro.setValue("ID");
        filtro.getStyleClass().add("combo-filtro");

        TextField campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o valor que deseja buscar...");
        campoPesquisa.getStyleClass().add("campo-pesquisa");

        Button btnBuscar = new Button("FILTRAR");
        btnBuscar.getStyleClass().add("botao-filtrar");
        btnBuscar.setOnAction(e -> {
            String tipo = filtro.getValue();
            String termo = campoPesquisa.getText();
            List<Report> filtradas = reportRepository.findByFilter(tipo, termo);
            reports.setAll(filtradas);
        });

        Button btnFormDenuncia = new Button("DENUNCIAR");
        btnFormDenuncia.getStyleClass().add("botao-form-denuncia");
        btnFormDenuncia.setOnAction(e -> abrirFormularioDenuncia());

        HBox filtros = new HBox(10, filtro, campoPesquisa, btnBuscar, btnFormDenuncia);
        filtros.getStyleClass().add("filtros-container");
        grid.add(filtros, 0, 0);

        TableView<Report> tableView = criarTabelaDenuncias();
        reports.setAll(reportRepository.findAll());
        tableView.setItems(reports);

        grid.add(tableView, 0, 1);
        layout.getChildren().add(grid);
    }

    private TableView<Report> criarTabelaDenuncias() {
        TableView<Report> tableView = new TableView<>();
        tableView.getStyleClass().add("table-view-denuncia");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        TableColumn<Report, String> id = criarColuna("ID", r -> r.getId().toString());
        TableColumn<Report, String> participante = criarColuna("EMAIL DO PARTICIPANTE", Report::getParticipante);
        TableColumn<Report, String> titulo = criarColuna("TÍTULO", Report::getTitulo);
        TableColumn<Report, String> motivo = criarColuna("MOTIVO", Report::getMotivo);
        TableColumn<Report, String> data = criarColuna("DATA REGISTRO",
                r -> r.getData().format(formatter));
        TableColumn<Report, String> status = criarColuna("STATUS", Report::getStatus);

        TableColumn<Report, Void> botoes = criarColunaDeAcoes(tableView);

        tableView.getColumns().addAll(id, participante, titulo, motivo, data, status, botoes);
        return tableView;
    }

    private TableColumn<Report, Void> criarColunaDeAcoes(TableView<Report> tableView) {
        TableColumn<Report, Void> col = new TableColumn<>("DECISÃO");

        col.setCellFactory(_ -> new TableCell<>() {
            private final Button delete = new Button("EXCLUIR");
            private final Button suspend = new Button("SUSPENDER");
            private final Button ban = new Button("BANIR");
            private final HBox box = new HBox(5, delete, suspend, ban);

            {
                delete.setId("delete");
                suspend.setId("suspend");
                ban.setId("ban");

                delete.setOnAction(e -> confirmarAcao("Excluir denúncia", "Deseja realmente excluir esta denúncia?", () -> {
                    Report report = getTableView().getItems().get(getIndex());
                    reportRepository.delete(report);
                    tableView.getItems().remove(report);
                }));

                suspend.setOnAction(e -> confirmarAcao("Suspender usuário", "Deseja realmente suspender este usuário?", () -> {
                    Report report = getTableView().getItems().get(getIndex());
                    atualizarStatus(report, "Suspenso", true, false);
                    tableView.refresh();
                }));

                ban.setOnAction(e -> confirmarAcao("Banir usuário", "Deseja realmente banir este usuário?", () -> {
                    Report report = getTableView().getItems().get(getIndex());
                    atualizarStatus(report, "Banido", false, true);
                    tableView.refresh();
                }));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= tableView.getItems().size()) {
                    setGraphic(null);
                } else {
                    Report report = tableView.getItems().get(getIndex());
                    if (report.getSuspenso() || report.getBanido()) {
                        setGraphic(null);
                    } else {
                        setGraphic(box);
                    }
                }
            }
        });

        return col;
    }

    private void confirmarAcao(String titulo, String mensagem, Runnable onConfirmar) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensagem);
        ButtonType confirmar = new ButtonType("CONFIRMAR");
        ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(cancelar, confirmar);

        Optional<ButtonType> escolha = alerta.showAndWait();
        if (escolha.isPresent() && escolha.get() == confirmar) {
            onConfirmar.run();
        }
    }

    private void atualizarStatus(Report report, String status, boolean suspenso, boolean banido) {
        report.setStatus(status);
        report.setSuspenso(suspenso);
        report.setBanido(banido);
        reportRepository.update(report);
    }

    private void abrirFormularioDenuncia() {
        DenunciaFormView denunciaFormView = new DenunciaFormView(stage);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(
                denunciaFormView.getDenunciaFormView(),
                screenBounds.getWidth() / 2,
                screenBounds.getHeight() / 2
        );
        scene.getStylesheets().add(getClass().getResource("/CSS/formDenuncia.css").toExternalForm());
        stage.setScene(scene);
    }

    private TableColumn<Report, String> criarColuna(String titulo, java.util.function.Function<Report, String> extractor) {
        TableColumn<Report, String> col = new TableColumn<>(titulo);
        col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(extractor.apply(cell.getValue())));
        return col;
    }

    public VBox getDenunciaView() {
        return layout;
    }
}
