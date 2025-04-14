package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminDashboardViewController {

    @FXML
    private void handleManageUsers(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjava/user-list-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            // Get stage from the event's source node
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
