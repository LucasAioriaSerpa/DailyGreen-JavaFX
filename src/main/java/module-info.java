module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.dailygreen.dailygreen.Users.Participante to javafx.fxml;
    opens org.dailygreen.dailygreen to javafx.fxml;
    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.Users.Administrador;
    exports org.dailygreen.dailygreen.Users.Participante;
}