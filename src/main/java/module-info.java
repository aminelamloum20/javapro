module com.example.pijihene {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.pijihene to javafx.fxml;
    exports com.example.pijihene;
    exports controllers;
    opens controllers to javafx.fxml;
}