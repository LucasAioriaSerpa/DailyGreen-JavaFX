package org.dailygreen.dailygreen.Users.Organizacao.telas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Organizacao.util.EventoOrganizacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TelaEventosOrganizacao {
    private final String email;
    private VBox layout;
    private ObservableList<EventoOrganizacao> eventos;
    private Stage stage;
    private String eventoFilePath;  // caminho do arquivo específico do usuário
    private Scene scene;

    public TelaEventosOrganizacao(Stage stage, String email) {
        this.email = email;
        this.stage = stage;
        this.layout = new VBox(15);
        this.layout.setPadding(new Insets(20));

        this.eventoFilePath = "data/eventosOrganizacao_" + email.replace("@", "_").replace(".", "_") + ".dat";

        stage.setTitle("Painel do Organizador - Eventos");

        eventos = FXCollections.observableArrayList(carregarEventos());

        montarComponentes();

        // Criar a scene e aplicar CSS
        scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/CSS/classPostagem.css").toExternalForm());

        // Setar a scene no stage
        stage.setScene(scene);
        stage.show();
    }

    private void montarComponentes() {
        Label tituloLabel = new Label("Criar Novo Evento");
        tituloLabel.getStyleClass().add("label");
        tituloLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do evento");
        nomeField.getStyleClass().add("text-field");

        TextArea descricaoArea = new TextArea();
        descricaoArea.setPromptText("Descrição do evento");
        descricaoArea.setPrefRowCount(3);
        descricaoArea.getStyleClass().add("text-area");

        TextField dataField = new TextField();
        dataField.setPromptText("Data (ex: 25/06/2025)");
        dataField.getStyleClass().add("text-field");

        Button btnPostar = new Button("Postar Evento");
        btnPostar.getStyleClass().add("button");

        TableView<EventoOrganizacao> tabela = new TableView<>();
        tabela.setItems(eventos);

        TableColumn<EventoOrganizacao, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<EventoOrganizacao, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<EventoOrganizacao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getColumns().addAll(colNome, colDesc, colData);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnPostar.setOnAction(e -> {
            String nome = nomeField.getText();
            String descricao = descricaoArea.getText();
            String data = dataField.getText();

            if (nome.isEmpty() || descricao.isEmpty() || data.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
                return;
            }

            EventoOrganizacao novo = new EventoOrganizacao(nome, descricao, data);
            eventos.add(novo);
            salvarEventos();

            nomeField.clear();
            descricaoArea.clear();
            dataField.clear();
        });

        VBox form = new VBox(10, nomeField, descricaoArea, dataField, btnPostar);
        form.setAlignment(Pos.CENTER_LEFT);
        form.getStyleClass().add("center-section");

        layout.getStyleClass().add("postagens-view");
        layout.getChildren().addAll(tituloLabel, form, new Separator(), new Label("Eventos Criados:"), tabela);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void salvarEventos() {
        File file = new File(eventoFilePath);
        file.getParentFile().mkdirs(); // Cria pasta data/ se não existir

        System.out.println("Salvando evento em: " + file.getAbsolutePath());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(eventos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<EventoOrganizacao> carregarEventos() {
        File file = new File(eventoFilePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        System.out.println("Carregando eventos de: " + file.getAbsolutePath());

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<EventoOrganizacao>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public VBox getView() {
        return layout;
    }

}
