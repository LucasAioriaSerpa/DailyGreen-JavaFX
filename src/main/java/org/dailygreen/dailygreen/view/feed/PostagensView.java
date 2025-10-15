package org.dailygreen.dailygreen.view.feed;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.event.EventAttendance;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.repository.impl.EventJsonRepository;
import org.dailygreen.dailygreen.repository.impl.UserJsonRepository;
import org.dailygreen.dailygreen.Factory.mainFeed.IPostagensUIPanel;
import org.dailygreen.dailygreen.Factory.mainFeed.UIPanelFactory;

import java.util.Objects;

public class PostagensView {
    private final Stage stage;
    private final VBox layout;
    private final User user;
    private final IPostagensUIPanel uiPanel;

    public PostagensView(Stage stage) {
        user = new UserJsonRepository().findAll().getFirst();
        this.stage = stage;
        this.layout = new VBox();
        this.uiPanel = UIPanelFactory.getPanel(user);
        layout.getStyleClass().add("postagens-view");
        layout.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/classPostagem.css")).toExternalForm());
        stage.setTitle("DailyGreen - Feed");
        showComponents();
    }

    public VBox getView() {
        return layout;
    }

    public Stage getStage() {
        return stage;
    }

    private void showComponents() {
        HBox mainContainer = new HBox();
        mainContainer.getStyleClass().add("main-container");

        // ? Seção Esquerda (Navegação)
        VBox leftSection = uiPanel.createLeftSection(stage, user, layout);
        HBox.setHgrow(leftSection, Priority.NEVER);

        // ? Seção Central (Postagens)
        ScrollPane centerScrollPane = uiPanel.createCenterSection(stage, user, layout);
        HBox.setHgrow(centerScrollPane, Priority.ALWAYS);

        // ? Seção Direita (Eventos)
        VBox rightSection = createSection("right-section");
        rightSection.setPrefWidth(300);
        rightSection.setMinWidth(260);
        HBox.setHgrow(rightSection, Priority.SOMETIMES);

        Label eventosLabel = new Label("Próximos Eventos");
        eventosLabel.getStyleClass().add("section-title");

        ListView<EventAttendance> eventosListView = new ListView<>();
        eventosListView.getItems().addAll((EventAttendance) new EventJsonRepository().findAll());
        eventosListView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(EventAttendance evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox eventoCard = new VBox(5);
                    Label nomeEvento = new Label(evento.getNome());
                    nomeEvento.getStyleClass().add("event-title");
                    Label dataEvento = new Label("Data: " + evento.getData());
                    dataEvento.getStyleClass().add("event-detail");
                    eventoCard.getChildren().addAll(nomeEvento, dataEvento);
                    setGraphic(eventoCard);
                }
            }
        });
        VBox.setVgrow(eventosListView, Priority.ALWAYS);
        rightSection.getChildren().addAll(eventosLabel, eventosListView);
        mainContainer.getChildren().addAll(leftSection, centerScrollPane, rightSection);
        layout.getChildren().add(mainContainer);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
    }

    private VBox createSection(String styleClass) {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_CENTER);
        section.setPadding(new Insets(20));
        section.getStyleClass().add(styleClass);
        return section;
    }
}