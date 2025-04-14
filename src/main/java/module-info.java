module com.example.projectjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // These are good
    opens com.example.projectjava to javafx.fxml;
    exports com.example.projectjava;
    exports controllers;
    opens controllers to javafx.fxml;

    // ✅ ADD THIS LINE to fix TableView reflection issue
    opens entities to javafx.base;
}
