package Controlles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Entites.Zone;
import Services.ZoneService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class AjouterZone {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField localisationTextField;

    @FXML
    private TextField nomzoneTextField;

    @FXML
    private TextField superficieTextField;

    @FXML
    void AjouterZone(ActionEvent event) {
        Zone zone = new Zone( Float.parseFloat(superficieTextField.getText()),nomzoneTextField.getText(),localisationTextField.getText());
        ZoneService zoneservice = new ZoneService();
        try {
            zoneservice.add(zone);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("alerte");
            alert.setContentText("ajouté avec succes");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherZone.fxml"));
            try{
                Parent root =loader.load();
                AfficherZone afficherZone = loader.getController();
                afficherZone.setSuperficie(Float.parseFloat(superficieTextField.getText()));
                afficherZone.setNomdezone(nomzoneTextField.getText());
                afficherZone.setLocalisation(localisationTextField.getText());
                superficieTextField.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
            //Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.setTitle("Erreur");
            //alert.show();
        }
    }





    @FXML
    void initialize() {
        }

}
