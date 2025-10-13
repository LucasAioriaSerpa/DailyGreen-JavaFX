module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.google.gson;

    exports org.dailygreen.dailygreen;
    exports org.dailygreen.dailygreen.view;
    exports org.dailygreen.dailygreen.view.administrador;
    exports org.dailygreen.dailygreen.util.controller;

    opens org.dailygreen.dailygreen                     to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view                to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.view.administrador  to javafx.graphics;
    opens org.dailygreen.dailygreen.util                to javafx.base, javafx.graphics;
    opens org.dailygreen.dailygreen.util.controller     to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.abstractUser;
    opens org.dailygreen.dailygreen.model.abstractUser          to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.post;
    opens org.dailygreen.dailygreen.model.post          to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.event;
    opens org.dailygreen.dailygreen.model.event         to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.model.abstractUser.types;
    opens org.dailygreen.dailygreen.model.abstractUser.types    to javafx.base, javafx.graphics;
    exports org.dailygreen.dailygreen.repository;
    opens org.dailygreen.dailygreen.repository          to javafx.graphics;
    exports org.dailygreen.dailygreen.repository.impl;
    opens org.dailygreen.dailygreen.repository.impl     to javafx.graphics;
}
