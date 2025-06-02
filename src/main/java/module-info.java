module org.dailygreen.dailygreen {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.dailygreen.dailygreen to javafx.fxml;
    exports org.dailygreen.dailygreen;
}