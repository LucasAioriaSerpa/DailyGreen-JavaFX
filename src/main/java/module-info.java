module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;
    requires java.desktop;
    requires org.jetbrains.annotations;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.Users;
    exports org.dailygreen.dailygreen.Postagens;
    exports org.dailygreen.dailygreen.view;
    exports org.dailygreen.dailygreen.view.participante;
    exports org.dailygreen.dailygreen.view.organizador;
    exports org.dailygreen.dailygreen.view.administrador;
    exports org.dailygreen.dailygreen.util.controller;
    exports org.dailygreen.dailygreen.util.DAT;
    exports org.dailygreen.dailygreen.util.DAO;

    opens org.dailygreen.dailygreen                     to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.Users               to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.Postagens           to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view                to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.organizador    to javafx.base;
    opens org.dailygreen.dailygreen.view.participante   to javafx.graphics;
    opens org.dailygreen.dailygreen.view.administrador  to javafx.graphics;
    opens org.dailygreen.dailygreen.util                to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.util.controller     to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.util.DAT            to javafx.graphics;
    opens org.dailygreen.dailygreen.util.DAO            to javafx.graphics;
}
