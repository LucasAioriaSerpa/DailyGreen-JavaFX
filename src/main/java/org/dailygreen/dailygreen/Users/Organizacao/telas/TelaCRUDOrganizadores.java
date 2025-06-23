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
import org.dailygreen.dailygreen.Users.Organizacao.model.Organizador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TelaCRUDOrganizadores {

    private Stage stage;
    private VBox layout;
    private ObservableList<Organizador> organizadores;
    private Scene scene;
    private final String FILE_PATH = "resources/db_dailygreen/organizadores.dat";

    // Campos para editar/criar
    private TextField txtEmail;
    private PasswordField txtSenha;
    private TextField txtCnpj;

    private TableView<Organizador> tabela;

    public TelaCRUDOrganizadores(Stage stage) {
        this.stage = stage;
        this.layout = new VBox(15);
        this.layout.setPadding(new Insets(20));
        this.layout.getStyleClass().add("postagens-view"); // classe CSS principal do container

        organizadores = FXCollections.observableArrayList(carregarOrganizadores());

        montarComponentes();

        scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/CSS/classPostagem.css").toExternalForm());

        stage.setTitle("Gerenciar Organizadores");
        stage.setScene(scene);
        stage.show();
    }

    private void montarComponentes() {
        // Título
        Label titulo = new Label("CRUD Organizadores");
        titulo.getStyleClass().add("label");

        // Formulário
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("text-field");

        txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.getStyleClass().add("text-field");

        txtCnpj = new TextField();
        txtCnpj.setPromptText("CNPJ");
        txtCnpj.getStyleClass().add("text-field");

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.getStyleClass().add("button");
        btnAdicionar.setOnAction(e -> adicionarOrganizador());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button");
        btnAtualizar.setOnAction(e -> atualizarOrganizador());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().add("button");
        btnExcluir.setOnAction(e -> excluirOrganizador());

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnExcluir);
        botoes.setAlignment(Pos.CENTER);

        VBox form = new VBox(10, txtEmail, txtSenha, txtCnpj, botoes);
        form.getStyleClass().add("center-section");

        // Tabela
        tabela = new TableView<>(organizadores);
        tabela.getStyleClass().add("list-view");

        TableColumn<Organizador, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Organizador, String> colSenha = new TableColumn<>("Senha");
        colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        TableColumn<Organizador, String> colCnpj = new TableColumn<>("CNPJ");
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));

        tabela.getColumns().addAll(colEmail, colSenha, colCnpj);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabela.setPrefHeight(300);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtEmail.setText(newSelection.getEmail());
                txtSenha.setText(newSelection.getSenha());
                txtCnpj.setText(newSelection.getCnpj());
            }
        });

        layout.getChildren().addAll(titulo, form, tabela);
    }

    private void adicionarOrganizador() {
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();
        String cnpj = txtCnpj.getText().trim();

        if (email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        // Verifica se email já existe
        for (Organizador org : organizadores) {
            if (org.getEmail().equals(email)) {
                mostrarAlerta("Erro", "Email já cadastrado.", Alert.AlertType.ERROR);
                return;
            }
        }

        Organizador novo = new Organizador(email, senha, cnpj);
        organizadores.add(novo);
        salvarOrganizadores();
        limparCampos();
    }

    private void atualizarOrganizador() {
        Organizador selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Erro", "Selecione um organizador para atualizar.", Alert.AlertType.ERROR);
            return;
        }

        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();
        String cnpj = txtCnpj.getText().trim();

        if (email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        // Atualiza dados
        selecionado.setEmail(email);
        selecionado.setSenha(senha);
        selecionado.setCnpj(cnpj);

        tabela.refresh();
        salvarOrganizadores();
        limparCampos();
    }

    private void excluirOrganizador() {
        Organizador selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Erro", "Selecione um organizador para excluir.", Alert.AlertType.ERROR);
            return;
        }

        organizadores.remove(selecionado);
        salvarOrganizadores();
        limparCampos();
    }

    private void limparCampos() {
        txtEmail.clear();
        txtSenha.clear();
        txtCnpj.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private List<Organizador> carregarOrganizadores() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Organizador>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void salvarOrganizadores() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(organizadores));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
