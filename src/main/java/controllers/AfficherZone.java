package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AfficherZone {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField localisation;

    @FXML
    private TextField nomdezone;

    @FXML
    private TextField superficie;

    public void setSuperficie(float superficie) {
        this.superficie.setText(Float.toString(superficie));
    }

    public void setNomdezone(String nomdezone) {
        this.nomdezone.setText(nomdezone);
    }

    public void setLocalisation(String localisation) {
        this.localisation.setText(localisation);
    }

    @FXML
    void initialize() {

    }
    @FXML
    private void retourListeZones(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjava/ListeZones.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Liste des zones");
        stage.show();
    }


}