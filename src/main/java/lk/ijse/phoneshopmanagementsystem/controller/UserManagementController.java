package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.UserBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.impl.UserBOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.UserDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {

    @FXML private TextField txtUserId;
    @FXML private TextField txtUsername;
    @FXML private TextField txtFullName;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private ComboBox<String> cmbRole;
    @FXML private ComboBox<String> cmbStatus;

    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @FXML private TextField txtSearch;

    @FXML private TableView<UserDTO> tblUsers;
    @FXML private TableColumn<UserDTO, String> colUserId;
    @FXML private TableColumn<UserDTO, String> colUserName;
    @FXML private TableColumn<UserDTO, String> colEmail;
    @FXML private TableColumn<UserDTO, String> colContactNo;
    @FXML private TableColumn<UserDTO, String> colRole;

    private ObservableList<UserDTO> userList = FXCollections.observableArrayList();
   // private final UserDAOImpl userDAO = new UserDAOImpl();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.User);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        cmbRole.getItems().addAll("Admin", "Employee");
        cmbStatus.getItems().addAll("Active", "Inactive");

        tblUsers.setItems(userList);

        loadAllUsers();

        tblUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });

        clearFields();
    }

    @FXML
    private void saveUser() {
        if (!validateFields()) return;

        try {
            UserDTO userDTO = new UserDTO(
                    txtUserId.getText(),
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    cmbRole.getValue()
            );

            boolean saved = userBO.saveUser(userDTO);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
                loadAllUsers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateUser() {
        if (!validateFields()) return;

        try {
            UserDTO userDTO = new UserDTO(
                    txtUserId.getText(),
                    txtUsername.getText(),
                    txtPassword.getText().isEmpty() ? null : txtPassword.getText(), // optional password update
                    txtEmail.getText(),
                    txtPhone.getText(),
                    cmbRole.getValue()
            );

            boolean updated = userBO.updateUser(userDTO);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                loadAllUsers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void deleteUser() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                boolean deleted = userBO.deleteUser(txtUserId.getText());
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                    loadAllUsers();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void clearFields() {
        try {
            txtUserId.setText(userBO.getNextUserID());
        } catch (SQLException e) {
            txtUserId.setText("U???");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtUsername.clear();
        txtPassword.clear();
        txtEmail.clear();
        txtPhone.clear();
        cmbRole.setValue(null);
        cmbStatus.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tblUsers.getSelectionModel().clearSelection();
    }

    @FXML
    private void loadAllUsers() {
        try {
            List<UserDTO> users = userBO.getAllUser();
            userList.setAll(users);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load users: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void searchUser() {
        String searchText = txtSearch.getText().trim();
        if (searchText.isEmpty()) {
            loadAllUsers();
            return;
        }

        try {
            UserDTO userDTO = userBO.searchUser(searchText);
            if (userDTO != null){
                userList.setAll(userDTO);
            } else {
                userList.clear();
                new Alert(Alert.AlertType.ERROR, "User not found!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Search error: " + e.getMessage()).show();
        }
    }

    private void fillFields(UserDTO user) {
        txtUserId.setText(user.getUserId());
        txtUsername.setText(user.getUserName());
        txtPassword.clear(); // security
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getContactNo());
        cmbRole.setValue(user.getRole());
    }

    private boolean validateFields() {
        if (txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtPhone.getText().isEmpty() || cmbRole.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields!").show();
            return false;
        }
        return true;
    }

    public void backButtonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }

    @FXML
    private void btnRefreshOnAction() {
        txtSearch.clear();
        loadAllUsers();
    }
}