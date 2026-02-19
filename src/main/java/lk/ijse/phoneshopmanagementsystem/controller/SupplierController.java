// src/main/java/lk/ijse/phoneshopmanagementsystem/controller/SupplierController.java
package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.CustomerBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.SupplierBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.SupplierDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.SupplierDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML private TextField txtSupplierId;
    @FXML private TextField txtName;
    @FXML private TextField txtCompany;
    @FXML private TextField txtAddress;
    @FXML private TextField txtContact;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSearch;

    @FXML private TableView<SupplierDTO> tblSuppliers;

    @FXML private TableColumn<SupplierDTO, String> colSupplierId;
    @FXML private TableColumn<SupplierDTO, String> colName;
    @FXML private TableColumn<SupplierDTO, String> colCompany;
    @FXML private TableColumn<SupplierDTO, String> colAddress;
    @FXML private TableColumn<SupplierDTO, String> colContact;
    @FXML private TableColumn<SupplierDTO, String> colEmail;

    private ObservableList<SupplierDTO> supplierList = FXCollections.observableArrayList();
    //private final SupplierDAOImpl supplierDAO = new SupplierDAOImpl();

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Supplier);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllSuppliers();
        generateNextId();
        setupTableListener();
    }

    private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void generateNextId() {
        try {
            txtSupplierId.setText(supplierBO.getNextSupplierID());
        } catch (SQLException e) {
            txtSupplierId.setText("S001");
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate ID!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSuppliers() {
        try {
            supplierList.clear();
            supplierList.addAll(supplierBO.getAllSupplier());
            tblSuppliers.setItems(supplierList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load suppliers!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTableListener() {
        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            if (newValue != null) {
                fillFields(newValue);
            }
        });
    }

    private void fillFields(SupplierDTO dto) {
        txtSupplierId.setText(dto.getSupplierId());
        txtName.setText(dto.getName());
        txtCompany.setText(dto.getCompany());
        txtAddress.setText(dto.getAddress());
        txtContact.setText(dto.getContact());
        txtEmail.setText(dto.getEmail());
    }

    private void clearFields() {
        txtName.clear();
        txtCompany.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtSearch.clear();
        generateNextId();
        tblSuppliers.getSelectionModel().clearSelection();
    }

    private SupplierDTO getFormData() {
        return new SupplierDTO(
                txtSupplierId.getText(),
                txtName.getText().trim(),
                txtCompany.getText().trim(),
                txtAddress.getText().trim(),
                txtContact.getText().trim(),
                txtEmail.getText().trim()
        );
    }

    @FXML
    void saveSupplier(ActionEvent event) {
        if (!isValidInput()) return;

        try {
            boolean saved = supplierBO.saveSupplier(getFormData());
            if (saved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier saved successfully!");
                clearFields();
                loadAllSuppliers();
            } else {
                //showAlert(Alert.Alert.AlertType.ERROR, "Failed", "Could not save supplier!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateSupplier(ActionEvent event) {
        if (!isValidInput()) return;

        try {
            boolean updated = supplierBO.updateSupplier(getFormData());
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated!");
                clearFields();
                loadAllSuppliers();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteSupplier(ActionEvent event) {
        String id = txtSupplierId.getText();
        if (id.isEmpty() || id.equals("Auto")) {
            showAlert(Alert.AlertType.WARNING, "Select Supplier", "Please select a supplier to delete!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Supplier");
        alert.setHeaderText("Delete Supplier: " + id);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (supplierBO.deleteSupplier(id)) {
                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "Supplier removed!");
                    clearFields();
                    loadAllSuppliers();
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        clearFields();
    }

    @FXML
    void searchSupplier(ActionEvent event) {
        String text = txtSearch.getText().trim();

        if (text.isEmpty()) {
            loadAllSuppliers();
            return;
        }

        try {
            SupplierDTO dto = supplierBO.searchSupplier(text);
            supplierList.clear();

            if (dto != null) {
                supplierList.add(dto);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Supplier not found!");
            }

            tblSuppliers.setItems(supplierList);

        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Search Failed", e.getMessage());
        }
    }


    @FXML
    void tableClicked(MouseEvent event) {
        SupplierDTO selected = tblSuppliers.getSelectionModel().getSelectedItem();
        if (selected != null) fillFields(selected);
    }

    private boolean isValidInput() {
        if (txtName.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Supplier Name is required!");
            txtName.requestFocus();
            return false;
        }
        if (!txtContact.getText().matches("^0\\d{9}$")) {  // ← මෙතන විතරයි වෙනස්
            showAlert(Alert.AlertType.WARNING, "Invalid Contact", "Contact must be 10 digits starting with 0");
            txtContact.requestFocus();
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty() && !txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email!");
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }

    public void backbtnAction(ActionEvent event) {
        App.setRoot(LoginController.getDashboardForRole());
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
    @FXML
    private void handleSupplierPrint(ActionEvent event) {
        try {
            supplierBO.printSupplierReports();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}