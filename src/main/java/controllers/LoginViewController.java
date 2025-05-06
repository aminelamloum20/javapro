package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.Session;
import services.UserService;
import utils.PasswordUtils;

import java.io.IOException;
import java.util.List;

public class LoginViewController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label registerLabel;

    private final UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Email and password are required.");
            return;
        }

        String hashedInput = PasswordUtils.hashPassword(password);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(hashedInput)) {
                Session.setCurrentUser(user);
                statusLabel.setText("✅ Login successful!");
                redirectUser();
                return;
            }
        }

        statusLabel.setText("❌ Invalid email or password.");
    }


    private void redirectUser() {
        try {
            User user = Session.getCurrentUser();
            String role = user.getRole().toUpperCase();

            String fxmlPath;
            if (role.equals("ADMIN") || role.equals("ROLE_ADMIN")) {
                fxmlPath = "/com/example/projectjava/admin-dashboard-view.fxml";
            } else {
                fxmlPath = "/com/example/projectjava/machine-home.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 800, 600);  // Set dimensions here

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("❌ Failed to load the dashboard.");
        }
    }
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjava/register-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);  // Set dimensions here

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("❌ Failed to load register page.");
        }
    }
    }
