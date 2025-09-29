package org.dailygreen.dailygreen.view.organizador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Organizador;
import org.dailygreen.dailygreen.util.DAT.OrganizacaoDAT;

import java.util.List;

public class TelaCRUDOrganizadores {

    private Stage stage;
    private VBox layout;
    private ObservableList<Organizador> organizadores;
    private Scene scene;
    private String emailLogado;
    private final String FILE_PATH = "resources/db_dailygreen/organizadores.dat";

    // Formulários
    private TextField txtEmail;
    private PasswordField txtSenha;
    private TextField txtCnpj;

    private TableView<Organizador> tabela;

    // Botões
    private Button btnLogin;
    private Button btnAtualizar;
    private Button btnExcluir;

    public TelaCRUDOrganizadores(Stage stage, String emailLogado) {
        this.stage = stage;
        this.emailLogado = emailLogado;
        this.layout = new VBox(15);
        this.layout.setPadding(new Insets(20));
        this.layout.getStyleClass().add("postagens-view");

        organizadores = FXCollections.observableArrayList();

        montarComponentes();

        scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/CSS/classPostagem.css").toExternalForm());

        stage.setTitle("Gerenciar Conta de Organizador");
        stage.setScene(scene);
        stage.show();

        if (emailLogado == null || emailLogado.isEmpty()) {
            mostrarLogin();
        } else {
            carregarOrganizadorLogado();
            mostrarCRUD();
        }
    }

    private void montarComponentes() {
        // Campos para email e senha (serão usados para login e para edição)
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("text-field");

        txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.getStyleClass().add("text-field");

        txtCnpj = new TextField();
        txtCnpj.setPromptText("CNPJ");
        txtCnpj.getStyleClass().add("text-field");

        // Botão de login (apenas para fase login)
        btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("button");
        btnLogin.setOnAction(e -> fazerLogin());

        // Botões de atualizar e excluir (apenas para CRUD)
        btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button");
        btnAtualizar.setOnAction(e -> atualizarOrganizador());

        btnExcluir = new Button("Excluir Conta");
        btnExcluir.getStyleClass().add("button");
        btnExcluir.setOnAction(e -> excluirOrganizador());

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

    }

    private void mostrarLogin() {
        layout.getChildren().clear();

        Label titulo = new Label("Faça Login");
        titulo.getStyleClass().add("label");

        VBox formLogin = new VBox(10, txtEmail, txtSenha, btnLogin);
        formLogin.getStyleClass().add("center-section");
        formLogin.setAlignment(Pos.CENTER);

        // Botão Voltar
        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");
        btnVoltar.setOnAction(e -> new TelaOrganizador(stage)); // volta para tela principal

        HBox boxVoltar = new HBox(btnVoltar);
        boxVoltar.setAlignment(Pos.CENTER);
        boxVoltar.setPadding(new Insets(10, 0, 0, 0));

        layout.getChildren().addAll(titulo, formLogin, boxVoltar);
    }


    private void mostrarCRUD() {
        layout.getChildren().clear();

        Label titulo = new Label("Sua Conta");
        titulo.getStyleClass().add("label");

        HBox botoes = new HBox(10, btnAtualizar, btnExcluir);
        botoes.setAlignment(Pos.CENTER);

        // Botão Voltar
        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");
        btnVoltar.setOnAction(e -> mostrarLogin()); // ou outro método, se desejar voltar para outra tela

        VBox form = new VBox(10, txtEmail, txtSenha, txtCnpj, botoes, btnVoltar);
        form.getStyleClass().add("center-section");

        layout.getChildren().addAll(titulo, form, tabela);
    }


    private void fazerLogin() {
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        List<Organizador> todos = OrganizacaoDAT.lerOrganizadores();
        for (Organizador o : todos) {
            if (o.getEmail().equalsIgnoreCase(email) && o.getSenha().equals(senha)) {
                emailLogado = email;
                organizadores.clear();
                organizadores.add(o);
                mostrarAlerta("Sucesso", "Login efetuado com sucesso!", Alert.AlertType.INFORMATION);
                mostrarCRUD();
                return;
            }
        }
        mostrarAlerta("Erro", "Email ou senha inválidos.", Alert.AlertType.ERROR);
    }

    private void carregarOrganizadorLogado() {
        organizadores.clear();
        List<Organizador> todos = OrganizacaoDAT.lerOrganizadores();
        for (Organizador o : todos) {
            if (o.getEmail().equalsIgnoreCase(emailLogado)) {
                organizadores.add(o);
                break;
            }
        }
    }

    private void atualizarOrganizador() {
        if (organizadores.isEmpty()) {
            mostrarAlerta("Erro", "Nenhum organizador encontrado.", Alert.AlertType.ERROR);
            return;
        }

        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();
        String cnpj = txtCnpj.getText().trim();

        if (email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        Organizador organizador = organizadores.get(0); // só 1 logado
        organizador.setEmail(email);
        organizador.setSenha(senha);
        organizador.setCnpj(cnpj);

        salvarOrganizadorLogado(organizador);
        tabela.refresh();
        mostrarAlerta("Sucesso", "Dados atualizados com sucesso.", Alert.AlertType.INFORMATION);
    }

    private void excluirOrganizador() {
        if (organizadores.isEmpty()) return;

        List<Organizador> todos = OrganizacaoDAT.lerOrganizadores();
        todos.removeIf(o -> o.getEmail().equalsIgnoreCase(emailLogado));
        OrganizacaoDAT.salvarOrganizadores(todos);

        organizadores.clear();
        tabela.refresh();
        mostrarAlerta("Sucesso", "Conta excluída.", Alert.AlertType.INFORMATION);

        // Volta pra tela de login após exclusão
        emailLogado = "";
        mostrarLogin();
    }

    private void salvarOrganizadorLogado(Organizador atualizado) {
        List<Organizador> todos = OrganizacaoDAT.lerOrganizadores();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getEmail().equalsIgnoreCase(emailLogado)) {
                todos.set(i, atualizado);
                break;
            }
        }
        OrganizacaoDAT.salvarOrganizadores(todos);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
