package controllers;

import entities.Post;
import services.PostService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ModifierPost {

    @FXML private TextField titreField;
    @FXML private TextArea contenuArea;
    @FXML private ImageView imageView;
    @FXML private Button boutonImage;

    private Post post;
    private String selectedImagePath = null;

    public void setPost(Post post) {
        this.post = post;

        titreField.setText(post.getTitre());
        contenuArea.setText(post.getContenu());

        if (post.getImage() != null && !post.getImage().isEmpty()) {
            File file = new File(post.getImage());
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }

    @FXML
    private void handleChoisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png")
        );

        File selectedFile = fileChooser.showOpenDialog(titreField.getScene().getWindow());
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            imageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void handleEnregistrer() {
        post.setTitre(titreField.getText());
        post.setContenu(contenuArea.getText());
        if (selectedImagePath != null) {
            post.setImage(selectedImagePath);
        }

        PostService postService = new PostService();
        postService.updatePost(post);

        // Retour à la page de détails du forum avec le post modifié
        try {
            Stage stage = (Stage) titreField.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjava/DetailsForum.fxml"));
            VBox root = loader.load();

            DetailsForum controller = loader.getController();
            controller.setPost(post); // Important : passer le post mis à jour

            Stage newStage = new Stage();
            newStage.setTitle("Détails du Forum");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAnnuler() {
        ((Stage) titreField.getScene().getWindow()).close();
    }
}
