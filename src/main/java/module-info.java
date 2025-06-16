module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;
    requires java.logging;
    requires java.desktop;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.Users.Administrador;
    exports org.dailygreen.dailygreen.Users.Participante;
    exports org.dailygreen.dailygreen.Postagens;
    
    opens org.dailygreen.dailygreen to javafx.graphics;
    opens org.dailygreen.dailygreen.Users.Administrador to javafx.graphics;
    opens org.dailygreen.dailygreen.Users.Participante to javafx.graphics;
    opens org.dailygreen.dailygreen.Postagens to javafx.graphics;
}