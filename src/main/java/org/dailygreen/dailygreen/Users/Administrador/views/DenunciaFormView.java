package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.controller.AdmController;
import org.dailygreen.dailygreen.Users.Administrador.dao.DenunciaDAO;
import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;
import org.dailygreen.dailygreen.Users.Participante.ArquivoParticipante;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DenunciaFormView {
    private VBox layout;
    private Stage stage;

    public DenunciaFormView(Stage stage) {
        this.stage = stage;
        this.layout = new VBox();
        layout.getStyleClass().add("denuncia-form-view");
        stage.setTitle("Formulário de Denúncias");
        showComponents();
    }

    public void showComponents() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("denuncia-form");
        grid.setAlignment(Pos.CENTER);


        // TITULO

        Label titleFormulario = new Label("FORMULÁRIO DE DENÚNCIAS");
        grid.add(titleFormulario, 0, 0);



        // PARTICIPANTE

        Label participanteDenunciado = new Label("Participante Denunciado");
        participanteDenunciado.getStyleClass().add("participante-denunciado");

        ComboBox<String> listaParticipantes = new ComboBox<>();
        listaParticipantes.setEditable(true);
        List<String> nomesParticipantes = ArquivoParticipante.lerLista().stream()
                .map(p -> p.getEmail())
                .toList();
        listaParticipantes.getItems().addAll(nomesParticipantes);

        listaParticipantes.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            listaParticipantes.hide();

            List<String> userFiltrado = nomesParticipantes.stream()
                    .filter(nome -> nome.toLowerCase().contains(newValue.toLowerCase()))
                    .toList();

            listaParticipantes.getItems().setAll(userFiltrado);
            listaParticipantes.show();
        });
        listaParticipantes.getStyleClass().add("combo-box");

        VBox participantes = new VBox(5, participanteDenunciado, listaParticipantes);
        grid.add(participantes, 0, 1);



        // TITULO DA DENUNCIA

        Label tituloDenuncia = new Label("Motivo:");
        tituloDenuncia.getStyleClass().add("denuncia-form-title");

        ComboBox<String> tituloOption = new ComboBox<>();
        tituloOption.getItems().addAll(
                "Spam",
                "Conteúdo Fora do Tema",
                "Linguagem inadequada",
                "Publicação Duplicada",
                "Informações Incorretas",
                "Discurso de Ódio",
                "Assédio ou Perseguição",
                "Incitação a Práticas Ilegais",
                "Conteúdo Impróprio ou Explicito"
        );
        tituloOption.setValue(null);
        tituloOption.getStyleClass().add("combo-box");

        VBox optionTitulo = new VBox(5, tituloDenuncia, tituloOption);
        grid.add(optionTitulo, 0, 2);



        // MOTIVO DA DENUNCIA

        Label motivoDenuncia = new Label("Descrição:");
        motivoDenuncia.getStyleClass().add("denuncia-form-title");

        ComboBox<String> motivoOption = new ComboBox<>();
        motivoOption.getItems().addAll(
                "Divulgação de links repetitivos e irrelevantes",
                "Envio de mensagens em massa nos comentários",
                "Promoção não autorizada de produtos ou serviços",
                "Postagens que não se relacionam com os tópicos do fórum",
                "Comentários que desviam do foco da discussão",
                "Publicações com assuntos pessoais ou irrelevantes",
                "Uso excessivo de palavrões",
                "Comentários sarcásticos ou provocativos",
                "Mensagens com tom agressivo sem ofensas diretas",
                "Repetição do mesmo post em diferentes categorias",
                "Comentários copiados e colados em várias discussões",
                "Reenvio de conteúdo já removido/moderado anteriormente",
                "Disseminação de dados ambientais sem fonte confiável",
                "Distorção de fatos sobre temas sustentáveis",
                "Compartilhamento de dicas ou práticas prejudiciais ao meio ambiente",
                "Comentários com preconceito racial, religioso, de gênero ou orientação sexual",
                "Ofensas a minorias ou grupos sociais",
                "Uso de termos discriminatórios ou incitação à violência",
                "Ataques direcionados a um ou mais usuários",
                "Envio repetido de mensagens indesejadas com teor ofensivo",
                "Tentativas de intimidar ou humilhar alguém na plataforma",
                "Apologia ao crime ambiental (como desmatamento ou caça ilegal)",
                "Incentivo ao descumprimento de leis de proteção ambiental",
                "Compartilhamento de conteúdo sobre atividades ilegais",
                "Imagens ou vídeos com nudez ou violência",
                "Divulgação de material gráfico que fere a política da plataforma"
        );
        motivoOption.setValue(null);
        motivoOption.getStyleClass().add("combo-box");

        VBox descricaoDenuncia = new VBox(5, motivoDenuncia, motivoOption);
        grid.add(descricaoDenuncia, 0, 3);



        // BOTÃO ENVIAR DENUNCIA

        Button voltar = new Button("VOLTAR");
        voltar.setOnAction(event -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            DenunciaView denunciaView = new DenunciaView(stage);
            Scene scene = new Scene(denunciaView.getDenunciaView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
            scene.getStylesheets().add(AdmController.class.getResource("/CSS/classAdm.css").toExternalForm());
            stage.setScene(scene);
        });

        Button enviarDenuncia = new Button("ENVIAR");
        enviarDenuncia.setOnAction(event -> {
            String participanteValue = listaParticipantes.getValue();
            String tittuloValue = tituloOption.getValue();
            String motivoValue = motivoOption.getValue();

            if (participanteValue != null && !participanteValue.isEmpty()
                    && tittuloValue != null && !tittuloValue.isEmpty()
                    && motivoValue != null && !motivoValue.isEmpty())

            {
                Denuncia denuncia = new Denuncia(participanteValue, tittuloValue, motivoValue);
                DenunciaDAO.registrar(denuncia);

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                DenunciaView denunciaView = new DenunciaView(stage);
                Scene scene = new Scene(denunciaView.getDenunciaView(), (int)(screenBounds.getWidth()/2), (int)(screenBounds.getHeight()/2));
                scene.getStylesheets().add(AdmController.class.getResource("/CSS/classAdm.css").toExternalForm());
                stage.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Necessário preencher todos os campos!");
                alert.showAndWait();
            }

        });
        grid.add(enviarDenuncia, 0, 4);

        VBox formulario = new VBox(5, titleFormulario, participantes, optionTitulo, descricaoDenuncia, enviarDenuncia, voltar   );
        grid.add(formulario, 0, 0);
        formulario.setAlignment(Pos.CENTER);

        layout.getChildren().add(grid);
    }

    public VBox getDenunciaFormView() {
        return layout;
    }
}
