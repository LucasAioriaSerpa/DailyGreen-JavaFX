package org.dailygreen.dailygreen.view.participante;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.util.DAT.ParticipanteDAT;
import org.dailygreen.dailygreen.Users.Participante;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.DAT.DATuser;
import org.dailygreen.dailygreen.util.Criptografia;

import java.util.Objects;

public class LoginViewParticipante {
    private VBox layout;
    private Stage stage;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblStatus;

    public LoginViewParticipante(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("main-screen");
        layout.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/CSS/participante.css")).toExternalForm()
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        criarComponentes();
    }

    private void criarComponentes() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.getStyleClass().add("card");

        Text titulo = new Text("Login do Participante");
        titulo.getStyleClass().add("title");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(15);
        formGrid.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu email");
        txtEmail.getStyleClass().add("text-field");

        Label lblSenha = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        txtSenha.getStyleClass().add("password-field");

        formGrid.add(lblEmail, 0, 0);
        formGrid.add(txtEmail, 1, 0);
        formGrid.add(lblSenha, 0, 1);
        formGrid.add(txtSenha, 1, 1);

        lblStatus = new Label();
        lblStatus.getStyleClass().add("status-label");

        Button btnEntrar = new Button("Entrar");
        btnEntrar.getStyleClass().add("button-primary");
        btnEntrar.setOnAction(e -> validarLogin());

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.getStyleClass().add("button-secondary");
        btnCadastrar.setOnAction(e -> abrirTelaCadastro());

        Button btnListaParticipantes = new Button("Lista de Participantes");
        btnListaParticipantes.getStyleClass().add("button-secondary");
        btnListaParticipantes.setOnAction(e -> abrirListaParticipantes());

        HBox botoes = new HBox(15, btnEntrar, btnCadastrar, btnListaParticipantes);
        botoes.setAlignment(Pos.CENTER);

        card.getChildren().addAll(titulo, formGrid, lblStatus, botoes);
        layout.getChildren().add(card);
    }

    private void abrirListaParticipantes() {
        ListaParticipantesView listaView = new ListaParticipantesView(stage);
        stage.getScene().setRoot(listaView.getView());
    }

    private void validarLogin() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        if (email.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }
        try {
            Participante participanteLogado = ParticipanteDAT.lerLista().stream()
                    .filter(p -> {
                        try {
                            boolean result = p.getEmail().equals(email) && Criptografia.descriptografar(p.getPassword(), Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE())).equals(senha);
                            if (result) {
                                User user = DATuser.getUser();
                                user.setAccountParticipante(p);
                                DATuser.setUser(user);
                            }
                            return result;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return false;
                        }
                    })
                    .findFirst()
                    .orElse(null);

            if (participanteLogado != null) {
                abrirPerfil(participanteLogado);
            } else {
                lblStatus.setText("Email ou senha inválidos!");
                lblStatus.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            lblStatus.setText("Erro ao validar login!");
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void abrirPerfil(Participante participante) {
        User user = DATuser.getUser();
        user.setLogged(true);
        user.setAccountParticipante(participante);
        DATuser.setUser(user);
        PerfilViewParticipante perfilView = new PerfilViewParticipante(stage, participante);
        stage.getScene().setRoot(perfilView.getView());
    }

    private void abrirTelaCadastro() {
        CadastroViewParticipante cadastroView = new CadastroViewParticipante(stage);
        stage.getScene().setRoot(cadastroView.getView());
    }

    public VBox getView() {
        return layout;
    }
}