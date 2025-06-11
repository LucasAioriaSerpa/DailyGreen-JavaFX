package org.dailygreen.dailygreen.Users.Participante;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CadastroViewParticipante {
    private VBox layout;
    private TextField txtNome;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblStatus;

    public CadastroViewParticipante(Stage stage) {
        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        criarComponentes(stage);
    }

    private void criarComponentes(Stage stage) {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.getStyleClass().add("card");

        // Título
        Text titulo = new Text("Cadastro de Participante");
        titulo.getStyleClass().add("title");

        // Formulário em GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome");
        txtNome.getStyleClass().add("text-field");

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu email");
        txtEmail.getStyleClass().add("text-field");

        Label lblSenha = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setPromptText("Crie uma senha");
        txtSenha.getStyleClass().add("password-field");

        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblEmail, 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(lblSenha, 0, 2);
        grid.add(txtSenha, 1, 2);

        lblStatus = new Label();
        lblStatus.getStyleClass().add("status-label");
        lblStatus.setWrapText(true);

        // Botões
        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.getStyleClass().add("button-primary");
        btnCadastrar.setOnAction(e -> cadastrarParticipante());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button-secondary");
        btnVoltar.setOnAction(e -> voltarParaLogin(stage));

        HBox botoes = new HBox(15, btnCadastrar, btnVoltar);
        botoes.setAlignment(Pos.CENTER);

        card.getChildren().addAll(titulo, grid, lblStatus, botoes);
        layout.getChildren().add(card);
    }

    private void cadastrarParticipante() {
        try {
            if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()) {
                lblStatus.setText("Por favor, preencha todos os campos!");
                lblStatus.setStyle("-fx-text-fill: red;");
                return;
            }

            Participante novoParticipante = new Participante(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtSenha.getText()
            );

            ArquivoParticipante.adicionarParticipante(novoParticipante);

            lblStatus.setText("Cadastro realizado com sucesso!");
            lblStatus.setStyle("-fx-text-fill: green;");

            ArquivoParticipante.lerLista().forEach(System.out::println); // Debug

        } catch (Exception e) {
            lblStatus.setText("Erro ao cadastrar: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void voltarParaLogin(Stage stage) {
        LoginViewParticipante loginView = new LoginViewParticipante(stage);
        stage.getScene().setRoot(loginView.getView());
    }

    public VBox getView() {
        return layout;
    }
}