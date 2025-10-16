module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.view.admin;
    exports org.dailygreen.dailygreen.model.moderation;
    exports org.dailygreen.dailygreen.controller;
    exports org.dailygreen.dailygreen.model.post;
    exports org.dailygreen.dailygreen.model.event;
    exports org.dailygreen.dailygreen.application;
    exports org.dailygreen.dailygreen.view.common;
    exports org.dailygreen.dailygreen.view.feed;

    opens org.dailygreen.dailygreen.model.user       to com.google.gson, com.fasterxml.jackson.databind;
    opens org.dailygreen.dailygreen.model.user.types to com.google.gson, com.fasterxml.jackson.databind;
    opens org.dailygreen.dailygreen.model.moderation to com.google.gson, com.fasterxml.jackson.databind;
    opens org.dailygreen.dailygreen                  to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.util             to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.controller       to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.model.post       to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.model.event      to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.application      to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.common      to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.feed        to com.google.gson, javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.admin       to com.google.gson, javafx.graphics;
}
