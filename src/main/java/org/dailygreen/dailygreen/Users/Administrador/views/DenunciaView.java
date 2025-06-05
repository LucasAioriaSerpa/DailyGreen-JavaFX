package org.dailygreen.dailygreen.Users.Administrador.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;


import java.util.Date;


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

        Text mainTitle = new Text("LISTA DE DENUNCIAS");
        mainTitle.getStyleClass().add("denuncia-title");
        grid.add(mainTitle, 0, 0, 4, 1);


        // TABELA DE DENUNCIAS
        TableView<Denuncia> tableView = new TableView<>();

        TableColumn<Denuncia, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<Denuncia, Integer>("id"));

        TableColumn<Denuncia, String> titulo = new TableColumn<>("TITULO");
        titulo.setCellValueFactory(new PropertyValueFactory<Denuncia, String>("titulo"));

        TableColumn<Denuncia, String> motivo = new TableColumn<>("MOTIVO");
        motivo.setCellValueFactory(new PropertyValueFactory<Denuncia, String>("motivo"));

        // REGISTRO DE ALGUMAS DENUNCIAS
        ObservableList<Denuncia> denunicias = FXCollections.observableArrayList(
                new Denuncia(1,"Spam","Divulgação de links repetitivos e irrelevantes"),
                new Denuncia(2,"Conteudo fora do tema","Postagens que não se relacionam com os tópicos do fórum"),
                new Denuncia(3,"Linguagem inadequada","Comentários sarcásticos ou provocativos"),
                new Denuncia(4,"Publicacao duplicada","Comentários copiados e colados em várias discussões."),
                new Denuncia(5, "Informacoes incorretas","Compartilhamento de dicas ou práticas prejudiciais ao meio ambiente"),
                new Denuncia(6, "Discurso de odio","Ofensas a minorias ou grupos sociais"),
                new Denuncia(7, "Assedio ou perseguicao", "Envio repetido de mensagens indesejadas com teor ofensivo"),
                new Denuncia(8,"Incitacao a praticas ilegais","Apologia ao crime ambiental (como desmatamento ou caça ilegal)"),
                new Denuncia(9, "Conteudo improprio ou explicito", "Imagens ou vídeos com nudez ou violência")
        );

        TableColumn<Denuncia, String> analise = new TableColumn<>("AÇÃO");
        analise.setCellFactory(Hyperlink linkAnalise = new Hyperlink("Analisar"));

        tableView.getColumns().addAll(id, titulo, motivo, analise);
        tableView.setItems(denunicias);
        grid.add(tableView, 0, 1);
        layout.getChildren().add(grid);
    }

    public VBox getDenunciaView() {
        return layout;
    }
}
