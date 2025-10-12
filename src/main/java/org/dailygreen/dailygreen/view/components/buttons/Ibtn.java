package org.dailygreen.dailygreen.view.components.buttons;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public interface Ibtn {
    public static Button Ibtn(String label, String styleClass, EventHandler<? super MouseEvent> onClick) {
        Button button = new Button(label);
        button.getStyleClass().add(styleClass);
        button.setOnAction(_ -> onClick.handle(null));
        return button;
    }
}
