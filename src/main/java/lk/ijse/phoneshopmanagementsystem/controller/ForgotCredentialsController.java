package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.UserBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.UserDAOImpl;

import java.sql.SQLException;

public class ForgotCredentialsController {

    @FXML private TextField txtCurrentUsername;
    @FXML private TextField txtNewUsername;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;

    //private final UserDAOImpl userDAO = new UserDAOImpl();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.User);

    @FXML
    void updateCredentials(ActionEvent event) {
        String currentUsername = txtCurrentUsername.getText().trim();
        String newUsername = txtNewUsername.getText().trim();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (currentUsername.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter your current username!");
            return;
        }

        if (newPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a new password!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "New password and confirm password do not match!");
            return;
        }

        if (newUsername.isEmpty()) {
            newUsername = currentUsername;
        }

        try {
            boolean updated = userBO.updateUserCredentials(currentUsername, newUsername, newPassword);

            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Username and Password updated successfully!\nYou can now login with new credentials.");
                backToLogin(null);
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Current username not found or update failed!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating credentials: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void backToLogin(ActionEvent event) {
        App.setRoot("login");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}