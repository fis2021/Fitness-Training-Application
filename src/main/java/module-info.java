module org.fta {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.fta to javafx.fxml;
    exports org.fta;
}