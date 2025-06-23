package org.dailygreen.dailygreen.Users.Organizacao.telas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private String eventoFilePath;
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

        scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/CSS/classPostagem.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private void montarComponentes() {
        Label tituloLabel = new Label("Criar ou Editar Evento");
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

        Button btnSalvar = new Button("Postar Evento");
        btnSalvar.getStyleClass().add("button");

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

        // Variável de controle de edição
        final EventoOrganizacao[] eventoEditando = {null};

        btnSalvar.setOnAction(e -> {
            String nome = nomeField.getText();
            String descricao = descricaoArea.getText();
            String data = dataField.getText();

            if (nome.isEmpty() || descricao.isEmpty() || data.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
                return;
            }

            if (eventoEditando[0] == null) {
                // Criar novo evento
                EventoOrganizacao novo = new EventoOrganizacao(nome, descricao, data);
                eventos.add(novo);
            } else {
                // Editar evento existente
                eventoEditando[0].setNome(nome);
                eventoEditando[0].setDescricao(descricao);
                eventoEditando[0].setData(data);
                tabela.refresh();
                eventoEditando[0] = null;
                btnSalvar.setText("Postar Evento");
            }

            salvarEventos();

            nomeField.clear();
            descricaoArea.clear();
            dataField.clear();
        });

        // Duplo clique para editar
        tabela.setRowFactory(tv -> {
            TableRow<EventoOrganizacao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    EventoOrganizacao eventoSelecionado = row.getItem();
                    nomeField.setText(eventoSelecionado.getNome());
                    descricaoArea.setText(eventoSelecionado.getDescricao());
                    dataField.setText(eventoSelecionado.getData());
                    btnSalvar.setText("Salvar Alterações");
                    eventoEditando[0] = eventoSelecionado;
                }
            });
            return row;
        });

        // Botão excluir
        Button btnExcluir = new Button("Excluir Evento Selecionado");
        btnExcluir.getStyleClass().add("button-red");
        btnExcluir.setOnAction(e -> {
            EventoOrganizacao selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmar Exclusão");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Deseja realmente excluir este evento?");
                confirmacao.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.OK) {
                        eventos.remove(selecionado);
                        salvarEventos();
                    }
                });
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");
        btnVoltar.setOnAction(e -> {
            new TelaOrganizador(stage, email); // volta para a tela principal do organizador
        });

        VBox form = new VBox(10, nomeField, descricaoArea, dataField, btnSalvar, btnExcluir, btnVoltar);
        form.setAlignment(Pos.CENTER_LEFT);
        form.getStyleClass().add("center-section");

        layout.getStyleClass().add("postagens-view");
        layout.getChildren().addAll(tituloLabel, form, new Separator(), new Label("Eventos Criados:"), tabela);
    }


    private void editarEvento(EventoOrganizacao evento) {
        TextInputDialog dialog = new TextInputDialog(evento.getNome());
        dialog.setTitle("Editar Evento");
        dialog.setHeaderText("Editar nome do evento");
        dialog.setContentText("Novo nome:");

        dialog.showAndWait().ifPresent(novoNome -> {
            evento.setNome(novoNome);
            salvarEventos();
            eventos.setAll(carregarEventos());
        });
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
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(eventos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<EventoOrganizacao> carregarEventos() {
        File file = new File(eventoFilePath);
        if (!file.exists()) return new ArrayList<>();
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
