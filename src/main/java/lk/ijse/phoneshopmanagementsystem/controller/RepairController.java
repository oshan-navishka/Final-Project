package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.RepairBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.RepairDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RepairController implements Initializable {

    @FXML
    private Button backbtn;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbIssueType;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<RepairDTO, String> colCustomerId;

    @FXML
    private TableColumn<RepairDTO, String> colDateReceived;

    @FXML
    private TableColumn<RepairDTO, String> colDeviceModel;

    @FXML
    private TableColumn<RepairDTO, String> colDeviceType;

    @FXML
    private TableColumn<RepairDTO, String> colIssueType;

    @FXML
    private TableColumn<RepairDTO, Double> colRepairCost;

    @FXML
    private TableColumn<RepairDTO, String> colRepairId;

    @FXML
    private TableColumn<RepairDTO, String> colStatus;

    @FXML
    private TableView<RepairDTO> tblRepairs;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDateReceived;

    @FXML
    private TextField txtDeviceModel;

    @FXML
    private TextField txtDeviceType;

   /* @FXML
    private TextArea txtIssueDescription;*/

    @FXML
    private TextField txtRepairCost;

    @FXML
    private TextField txtRepairId;

    @FXML
    private TextField txtSearch;

    //private final RepairDAOImpl repairDAO = new RepairDAOImpl();

    RepairBO repairBO = (RepairBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Repair);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDeviceType.setCellValueFactory(new PropertyValueFactory<>("deviceType"));
        colDeviceModel.setCellValueFactory(new PropertyValueFactory<>("deviceModel"));
        colIssueType.setCellValueFactory(new PropertyValueFactory<>("issueType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        colDateReceived.setCellValueFactory(new PropertyValueFactory<>("dateReceived"));

        ObservableList<String> issueTypes = FXCollections.observableArrayList(
                "Screen Damage",
                "Battery Issue",
                "Water Damage",
                "Software Problem",
                "Charging Port",
                "Speaker/Mic Issue",
                "Camera Issue",
                "Other"
        );
        cmbIssueType.setItems(issueTypes);

        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "Pending",
                "In Progress",
                "Completed",
                "Delivered",
                "Cancelled"
        );
        cmbStatus.setItems(statusOptions);

        loadTableData();

        tblRepairs.setOnMouseClicked(this::handleRowClick);

        try {
            generateNextRepairId();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate Repair ID: " + e.getMessage());
        }
    }

    private void loadTableData() {
        try {
            ArrayList<RepairDTO> repairList = repairBO.getAllRepair();
            ObservableList<RepairDTO> repairObservableList = FXCollections.observableArrayList(repairList);
            tblRepairs.setItems(repairObservableList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load repairs: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            RepairDTO selectedRepair = tblRepairs.getSelectionModel().getSelectedItem();
            if (selectedRepair != null) {
                fillFields(selectedRepair);
            }
        }
    }

    private void fillFields(RepairDTO repair) {
        txtRepairId.setText(repair.getRepairId());
        txtCustomerId.setText(repair.getCustomerId());
        txtDeviceType.setText(repair.getDeviceType());
        txtDeviceModel.setText(repair.getDeviceModel());
        cmbIssueType.setValue(repair.getIssueType());
        cmbStatus.setValue(repair.getStatus());
        txtRepairCost.setText(String.valueOf(repair.getRepairCost()));
        txtDateReceived.setText(repair.getDateReceived());
    }

    @FXML
    void saveRepair(ActionEvent event) {
        try {
            if (!validateInputs()) {
                return;
            }

            RepairDTO repairDTO = new RepairDTO(
                    txtRepairId.getText(),
                    txtCustomerId.getText(),
                    txtDeviceType.getText(),
                    txtDeviceModel.getText(),
                    cmbIssueType.getValue(),
                    cmbStatus.getValue(),
                    Double.parseDouble(txtRepairCost.getText()),
                    txtDateReceived.getText()
            );

            boolean isSaved = repairBO.saveRepair(repairDTO);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Repair saved successfully!");
                clearFields(event);
                loadTableData();
                generateNextRepairId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save repair!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid repair cost format!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save repair: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateRepair(ActionEvent event) {
        try {
            RepairDTO selectedRepair = tblRepairs.getSelectionModel().getSelectedItem();

            if (selectedRepair == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a repair to update!");
                return;
            }

            if (!validateInputs()) {
                return;
            }

            RepairDTO repairDTO = new RepairDTO(
                    txtRepairId.getText(),
                    txtCustomerId.getText(),
                    txtDeviceType.getText(),
                    txtDeviceModel.getText(),
                    cmbIssueType.getValue(),
                    cmbStatus.getValue(),
                    Double.parseDouble(txtRepairCost.getText()),
                    txtDateReceived.getText()
            );

            boolean isUpdated = repairBO.updateRepair(repairDTO);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Repair updated successfully!");
                clearFields(event);
                loadTableData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update repair!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid repair cost format!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update repair: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteRepair(ActionEvent event) {
        RepairDTO selectedRepair = tblRepairs.getSelectionModel().getSelectedItem();

        if (selectedRepair == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a repair to delete!");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Repair");
        confirmAlert.setContentText("Are you sure you want to delete repair " + selectedRepair.getRepairId() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDeleted = repairBO.deleteRepair(selectedRepair.getRepairId());

                if (isDeleted) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Repair deleted successfully!");
                    clearFields(event);
                    loadTableData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete repair!");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete repair: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        txtRepairId.clear();
        txtCustomerId.clear();
        txtDeviceType.clear();
        txtDeviceModel.clear();
        cmbIssueType.setValue(null);
        cmbStatus.setValue(null);
        txtRepairCost.clear();
        txtDateReceived.clear();
        txtSearch.clear();

        tblRepairs.getSelectionModel().clearSelection();

        try {
            generateNextRepairId();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate Repair ID: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchRepair(ActionEvent event) {
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty()) {
            loadTableData();
            return;
        }

        try {
            RepairDTO repair = repairBO.searchRepair(searchText);

            if (repair != null) {
                ObservableList<RepairDTO> searchResult = FXCollections.observableArrayList();
                searchResult.add(repair);
                tblRepairs.setItems(searchResult);
            } else {
                ArrayList<RepairDTO> allRepairs = repairBO.getAllRepair();
                ObservableList<RepairDTO> filteredList = FXCollections.observableArrayList();

                String lowerSearchText = searchText.toLowerCase();
                for (RepairDTO repairDTO : allRepairs) {
                    if (repairDTO.getRepairId().toLowerCase().contains(lowerSearchText) ||
                            repairDTO.getCustomerId().toLowerCase().contains(lowerSearchText) ||
                            repairDTO.getDeviceModel().toLowerCase().contains(lowerSearchText) ||
                            repairDTO.getDeviceType().toLowerCase().contains(lowerSearchText) ||
                            repairDTO.getIssueType().toLowerCase().contains(lowerSearchText)) {

                        filteredList.add(repairDTO);
                    }
                }

                tblRepairs.setItems(filteredList);

                if (filteredList.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Search Result", "No repairs found matching your search!");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to search repairs: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) backbtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        if (txtRepairId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Repair ID is required!");
            return false;
        }
        if (txtCustomerId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Customer ID is required!");
            return false;
        }
        if (txtDeviceType.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Device Type is required!");
            return false;
        }
        if (txtDeviceModel.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Device Model is required!");
            return false;
        }
        if (cmbIssueType.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Issue Type is required!");
            return false;
        }
        if (cmbStatus.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Status is required!");
            return false;
        }
        if (txtRepairCost.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Repair Cost is required!");
            return false;
        }
        if (txtDateReceived.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Date Received is required!");
            return false;
        }

        try {
            Double.parseDouble(txtRepairCost.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Repair Cost must be a valid number!");
            return false;
        }

        return true;
    }

    private void generateNextRepairId() throws SQLException, ClassNotFoundException {
        String nextId = repairBO.getNextRepairID();
        txtRepairId.setText(nextId);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void backuttonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }
}