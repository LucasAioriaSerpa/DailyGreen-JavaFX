module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;


    opens org.dailygreen.dailygreen to javafx.fxml;
    exports org.dailygreen.dailygreen;
}