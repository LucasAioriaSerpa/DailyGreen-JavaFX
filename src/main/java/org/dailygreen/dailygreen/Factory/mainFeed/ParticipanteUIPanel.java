package org.dailygreen.dailygreen.Factory.mainFeed;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.controller.MainFeedController;
import org.dailygreen.dailygreen.view.components.postagens.CreatePostForm;
import org.dailygreen.dailygreen.view.components.postagens.CreatePostList;

public class ParticipanteUIPanel implements IPostagensUIPanel {
    @Override
    public VBox createLeftSection(Stage stage, User user, VBox layout) {
        VBox leftSection = createBaseSection("left-section");

        Button btnPerfil = new Button("Meu Perfil");
        btnPerfil.getStyleClass().add("nav-button");
        btnPerfil.setOnAction(_ -> MainFeedController.goPerfil(stage, user.getAccountParticipante()));

        Button btnPostagens = new Button("Postagens");
        btnPostagens.getStyleClass().add("nav-button-active");
        leftSection.getChildren().addAll(btnPerfil, btnPostagens);
        return leftSection;
    }

    @Override
    public ScrollPane createCenterSection(Stage stage, User user, VBox layout) {
        VBox centerSectionContent = new VBox(20);
        centerSectionContent.setPadding(new Insets(15));
        centerSectionContent.getChildren().addAll(
                CreatePostForm.createPostForm(stage, user),
                CreatePostList.createPostList(stage, user, layout)
        );
        ScrollPane centerScrollPane = new ScrollPane(centerSectionContent);
        centerScrollPane.getStyleClass().add("center-scroll-pane");
        centerScrollPane.setFitToWidth(true);
        return centerScrollPane;
    }

    private VBox createBaseSection(String styleClass) {
        VBox section = new VBox(15);
        section.getStyleClass().add(styleClass);
        section.setPrefWidth(220);
        section.setMinWidth(200);
        return section;
    }
}
