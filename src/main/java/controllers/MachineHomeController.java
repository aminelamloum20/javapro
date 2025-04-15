package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MachineHomeController {

    @FXML
    private void handleAddMachine(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/projectjava/machine-add.fxml");
    }

    @FXML
    private void handleListMachines(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/projectjava/machine-index.fxml");
    }

    @FXML
    private void handleReservation(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/projectjava/reservation-view.fxml");
    }

    private void loadPage(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load(), 800, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("🌿 DevHarvest - Gestion");
        stage.show();
    }
}

