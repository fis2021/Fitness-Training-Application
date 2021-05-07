module org.fta {
    requires javafx.controls;
    requires javafx.fxml;
    requires nitrite;

    opens org.fta to javafx.fxml;
    exports org.fta;
    exports org.fta.Controllers;
    opens org.fta.Controllers to javafx.fxml;
    opens org.fta.Models;
}