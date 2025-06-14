package org.dailygreen.dailygreen.Postagens;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PostagensView {
    private Stage stage;
    private VBox layout;
    public PostagensView(Stage stage) {
        this.stage = stage;
        layout = new VBox();
        layout.getStyleClass().add("postagens-view");
        stage.setTitle("Postagens");
        showComponents();
    }
    public VBox getView() {
        return layout;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void showComponents() {
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.25, 0.75);

        VBox leftSection = new VBox(10);
        leftSection.setPadding(new Insets(10));
        leftSection.getStyleClass().add("left-section");

        VBox centerSection = new VBox(10);
        centerSection.setPadding(new Insets(10));
        centerSection.getStyleClass().add("center-section");

        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        Label descriptionLabel = new Label("Descrição:");
        TextArea descriptionArea = new TextArea();
        Button submitButton = new Button("Postar");

        VBox postForm = new VBox(10);
        postForm.getChildren().addAll(titleLabel, titleField, descriptionLabel, descriptionArea, submitButton);

        ListView<String> postList = new ListView<>();
        VBox.setVgrow(postList, Priority.ALWAYS);

        centerSection.getChildren().addAll(postForm, postList);

        VBox rightSection = new VBox(10);
        rightSection.setPadding(new Insets(10));
        rightSection.getStyleClass().add("right-section");

        splitPane.getItems().addAll(leftSection, centerSection, rightSection);
        layout.getChildren().add(splitPane);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
    }
}