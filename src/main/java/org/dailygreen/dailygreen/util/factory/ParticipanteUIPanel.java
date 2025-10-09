package org.dailygreen.dailygreen.util.factory;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.util.controller.PostagensControll;

public class ParticipanteUIPanel implements IPostagensUIPanel {
    @Override
    public VBox createLeftSection(Stage stage, User user) {
        VBox leftSection = createBaseSection("left-section");

        Button btnPerfil = new Button("Meu Perfil");
        btnPerfil.getStyleClass().add("nav-button");
        btnPerfil.setOnAction(_ -> PostagensControll.goPerfil(stage, user.getAccountParticipante()));

        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("nav-button-active");

        leftSection.getChildren().addAll(btnPerfil, btnPostagens);
        return leftSection;
    }

    private VBox createBaseSection(String styleClass) {
        VBox section = new VBox(15);
        section.getStyleClass().add(styleClass);
        section.setPrefWidth(220);
        section.setMinWidth(200);
        return section;
    }
}
