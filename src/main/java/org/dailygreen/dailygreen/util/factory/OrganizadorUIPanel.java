package org.dailygreen.dailygreen.util.factory;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.controller.MainFeedController;

public class OrganizadorUIPanel implements IPostagensUIPanel{
    @Override
    public VBox createLeftSection(Stage stage, User user, VBox layout) {
        VBox leftSection = new VBox();

        Button btnPerfilOrg = new Button("Perfil Organizador");
        btnPerfilOrg.getStyleClass().add("nav-button");
        btnPerfilOrg.setOnAction(_ -> MainFeedController.goPerfil(stage, user.getAccountParticipante()));

        Button btnGerenciarEventos = new Button("Meus Eventos");
        btnGerenciarEventos.getStyleClass().add("nav-button");

        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("nav-button-active");

        leftSection.getChildren().addAll(btnPerfilOrg, btnGerenciarEventos, btnPostagens);
        return leftSection;
    }

    @Override
    public ScrollPane createCenterSection(Stage stage, User user, VBox layout) {
        return null;
    }

    public VBox createBaseSection(String styleClass) {
        VBox section = new VBox(15);
        section.getStyleClass().add(styleClass);
        section.setPrefWidth(220);
        section.setMinWidth(200);
        return section;
    }
}
