package Controlles;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

}
