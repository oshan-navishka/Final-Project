package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.UserDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lk.ijse.phoneshopmanagementsystem.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public static String loggedUserId;
    public static String loggedUserName;
    public static String loggedUserRole;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink changeDetails;

    private final UserDAOImpl userDAO = new UserDAOImpl();

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showErrorAlert("Please enter username and password!");
            return;
        }

        try {
            User user = userDAO.validateLogin(username, password);

            if (user != null) {
                loggedUserId = user.getUserId();
                loggedUserName = user.getUserName();
                loggedUserRole = user.getRole();

                System.out.println("Login Successful! Role: " + loggedUserRole);

                // Role based redirect
                if ("Admin".equals(user.getRole())) {
                    App.setRoot("dashboard"); // Admin dashboard - full access
                } else if ("Employee".equals(user.getRole())) {
                    App.setRoot("employeeDashboard"); // Employee dashboard - limited access
                } else {
                    showErrorAlert("Invalid user role!");
                }
            } else {
                showErrorAlert("Incorrect Username or Password!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void editeLoginDetails() {
        App.setRoot("forgot_credentials");
    }

    private void showErrorAlert(String message) {
        usernameField.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 8;");
        passwordField.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 8;");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Access Denied");
        alert.setContentText(message);
        alert.show();

        passwordField.clear();
        usernameField.clear();

        new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            usernameField.setStyle("");
            passwordField.setStyle("");
        })).play();
    }
    public static String getDashboardForRole() {
        if ("Admin".equals(loggedUserRole)) {
            return "dashboard";
        } else if ("Employee".equals(loggedUserRole)) {
            return "employeeDashboard";
        } else {
            return "login";
        }
    }
}