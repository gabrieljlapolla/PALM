package PALMGUI;

import PALM.PALM;
import PALM.PALMDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Label loginWarningLabel;

    public void loginAttempt(ActionEvent e) {
        PALMDB pdb = new PALMDB();
        if (usernameTextField.getText().isBlank()) {
            loginWarningLabel.setText("Please enter a username");
        } else if (passwordPasswordField.getText().isBlank()) {
            loginWarningLabel.setText("Please enter a password");
        } else {
            // Get entered username and password
            String username = usernameTextField.getText();
            String password = passwordPasswordField.getText();

            if (!pdb.containsUser(username)) { // Check if user is new user
                System.out.printf("New user '%s' creating account\n", username);
            } else if (pdb.checkUser(username, password)) {
                // User successfully logged in and items retrieved
                PALM.loadItems(username, password);
                loginWarningLabel.setText("Logging in...");
                loginWarningLabel.setTextFill(Color.web("#247357"));
                System.out.printf("User '%s' has been successfully logged in\n", username);
                ((Stage) loginWarningLabel.getScene().getWindow()).close(); // Close login window
            } else { // Recognized user, but wrong password
                loginWarningLabel.setText(String.format("Incorrect password for user %s", username));
                passwordPasswordField.clear();
            }
        }
    }

    public void exit(ActionEvent e) {
        System.out.println("Exit button clicked. Exiting...");
        Platform.exit();
        ((Stage) loginWarningLabel.getScene().getWindow()).close(); // Close login window
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove title bar
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();
    }
}
