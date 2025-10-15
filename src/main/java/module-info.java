module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.view.admin;
    exports org.dailygreen.dailygreen.controller;

    opens org.dailygreen.dailygreen.model.user to com.google.gson, com.fasterxml.jackson.databind;
    opens org.dailygreen.dailygreen.model.user.types to com.google.gson, com.fasterxml.jackson.databind;
    opens org.dailygreen.dailygreen                             to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.admin to javafx.graphics;
    opens org.dailygreen.dailygreen.util                        to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.controller to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.post;
    opens org.dailygreen.dailygreen.model.post                  to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.event;
    opens org.dailygreen.dailygreen.model.event                 to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.application;
    opens org.dailygreen.dailygreen.application to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.view.common;
    opens org.dailygreen.dailygreen.view.common to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.view.feed;
    opens org.dailygreen.dailygreen.view.feed to javafx.base, javafx.graphics;
}
