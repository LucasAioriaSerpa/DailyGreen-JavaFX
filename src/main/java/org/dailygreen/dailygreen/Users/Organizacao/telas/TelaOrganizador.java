package org.dailygreen.dailygreen.Users.Organizacao.telas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.MainController;
import org.dailygreen.dailygreen.Users.Organizacao.model.Organizador;

import java.io.*;
import java.util.ArrayList;

public class TelaOrganizador {

    private VBox layout;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private TextField txtCnpj;
    private Label lblStatus;
    private final String FILE_PATH = "resources/db_dailygreen/organizadores.dat";
    private Stage stage;
    private Scene scene;

    public TelaOrganizador(Stage stage) {
        this.stage = stage;

        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        criarComponentes();

        scene = new Scene(layout, 900, 500);
        scene.getStylesheets().add(getClass().getResource("/CSS/classAdm.css").toExternalForm());

        stage.setTitle("Login Organizador");
        stage.setScene(scene);
        stage.show();
    }

    public VBox getView() {
        return layout;
    }

    private void criarComponentes() {
        VBox card = new VBox(20);
        card.getStyleClass().add("screen");
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));

        Text titulo = new Text("Login e Cadastro de Organizador");
        titulo.getStyleClass().add("title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("denuncia-grid");
        grid.setHgap(7);
        grid.setVgap(7);
        grid.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu email");

        Label lblSenha = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");

        Label lblCnpj = new Label("CNPJ:");
        txtCnpj = new TextField();
        txtCnpj.setPromptText("Digite seu CNPJ");

        grid.add(lblEmail, 0, 0);
        grid.add(txtEmail, 1, 0);
        grid.add(lblSenha, 0, 1);
        grid.add(txtSenha, 1, 1);
        grid.add(lblCnpj, 0, 2);
        grid.add(txtCnpj, 1, 2);

        lblStatus = new Label();
        lblStatus.setWrapText(true);

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER);

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> fazerLogin());

        Button btnCadastro = new Button("Cadastrar");
        btnCadastro.setOnAction(e -> fazerCadastro());

        Button btnGerenciar = new Button("Listar Organizadores");
        btnGerenciar.setOnAction(e -> MainController.btnGerenciarOrganizadores(stage));

        botoes.getChildren().addAll(btnLogin, btnCadastro, btnGerenciar);

        card.getChildren().addAll(titulo, grid, lblStatus, botoes);
        layout.getChildren().add(card);
    }

    private void fazerLogin() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarErro("Preencha todos os campos.");
            return;
        }

        ArrayList<Organizador> lista = carregarOrganizadores();
        for (Organizador org : lista) {
            if (org.getEmail().equals(email) && org.getSenha().equals(senha)) {
                mostrarSucesso("Login bem-sucedido!");
                new TelaEventosOrganizacao(stage, email);
                return;
            }
        }

        mostrarErro("Email ou senha inválidos.");
    }

    private void fazerCadastro() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String cnpj = txtCnpj.getText();

        if (email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            mostrarErro("Preencha todos os campos.");
            return;
        }

        ArrayList<Organizador> lista = carregarOrganizadores();
        for (Organizador org : lista) {
            if (org.getEmail().equals(email)) {
                mostrarErro("Email já cadastrado.");
                return;
            }
        }

        lista.add(new Organizador(email, senha, cnpj));
        salvarOrganizadores(lista);
        mostrarSucesso("Cadastro realizado com sucesso!");
    }

    private void mostrarErro(String mensagem) {
        lblStatus.setText(mensagem);
        lblStatus.setStyle("-fx-text-fill: red;");
    }

    private void mostrarSucesso(String mensagem) {
        lblStatus.setText(mensagem);
        lblStatus.setStyle("-fx-text-fill: green;");
    }

    private ArrayList<Organizador> carregarOrganizadores() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Organizador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void salvarOrganizadores(ArrayList<Organizador> lista) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
