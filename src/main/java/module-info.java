module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;
    requires java.logging;
    requires java.desktop;
    requires org.jetbrains.annotations;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.Users.Administrador;
    exports org.dailygreen.dailygreen.Users.Participante;
    exports org.dailygreen.dailygreen.Postagens;


    opens org.dailygreen.dailygreen to javafx.graphics;
    opens org.dailygreen.dailygreen.Users.Organizacao.model to javafx.base;
    opens org.dailygreen.dailygreen.Users.Organizacao.telas to javafx.base;
    opens org.dailygreen.dailygreen.Users.Organizacao.util to javafx.base;
    opens org.dailygreen.dailygreen.Users.Administrador to javafx.graphics;
    opens org.dailygreen.dailygreen.Users.Participante to javafx.graphics;
    opens org.dailygreen.dailygreen.Postagens to javafx.graphics;
}