module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires java.compiler;
    requires org.jetbrains.annotations;

    opens org.dailygreen.dailygreen.Users.Participante to javafx.fxml;
    opens org.dailygreen.dailygreen to javafx.fxml;
    opens org.dailygreen.dailygreen.Users.Administrador.models to javafx.base;
    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.Users.Administrador;
    exports org.dailygreen.dailygreen.Users.Participante;
    exports org.dailygreen.dailygreen.util;
    opens org.dailygreen.dailygreen.util to javafx.fxml;
}