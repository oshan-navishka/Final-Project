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
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Button btnSearch;
    @FXML private TextField txtCustomerId;
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtContact;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNic;
    @FXML private TextField txtSearch;

    @FXML private TableView<CustomerDTO> tblCustomers;

    @FXML private TableColumn<CustomerDTO, String> colCustomerId;
    @FXML private TableColumn<CustomerDTO, String> colName;
    @FXML private TableColumn<CustomerDTO, String> colAddress;
    @FXML private TableColumn<CustomerDTO, String> colContact;
    @FXML private TableColumn<CustomerDTO, String> colEmail;
    @FXML private TableColumn<CustomerDTO, String> colNic;

    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    private final ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList();

    CustomerBO customerBo = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Customer);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllCustomers();
        setupTableSelection();
        generateNextCustomerId();
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }

    private void loadAllCustomers() {
        try {
            ArrayList<CustomerDTO> allCustomers = customerBo.getAllCustomer();
            customerList.clear();
            customerList.addAll(allCustomers);
            tblCustomers.setItems(customerList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customers: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTableSelection() {
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });
    }

    private void fillFields(CustomerDTO dto) {
        txtCustomerId.setText(dto.getCustomerId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtContact.setText(dto.getContact());
        txtEmail.setText(dto.getEmail());
        txtNic.setText(dto.getNic());
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtNic.clear();
        txtSearch.clear();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblCustomers.getSelectionModel().clearSelection();
        generateNextCustomerId();
    }

    private CustomerDTO getCustomerFromForm() {
        return new CustomerDTO(
                txtCustomerId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                txtEmail.getText(),
                txtNic.getText()
        );
    }

    private void generateNextCustomerId() {
        try {
            String nextId = customerBo.getNextCustomerID();
            txtCustomerId.setText(nextId);
        } catch (SQLException e) {
            txtCustomerId.setText("C001");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void saveCustomer(ActionEvent event) {
        // Validate karala balanna
        if (!validateInputs()) {
            return; // Validation fail unoth save wenne na
        }

        try {
            boolean isSaved = customerBo.saveCustomer(getCustomerFromForm());
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                loadAllCustomers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save customer").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Validation method eka
    private boolean validateInputs() {
        // Check if required fields are empty
        if (txtName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Name is required!").show();
            txtName.requestFocus();
            return false;
        }


        if (txtNic.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "NIC Number is required!").show();
            txtNic.requestFocus();
            return false;
        }

        if (txtContact.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Phone Number is required!").show();
            txtContact.requestFocus();
            return false;
        }

        // Validate NIC format (Sri Lankan NIC)
        String nic = txtNic.getText().trim();
        System.out.println("NIC Input: '" + nic + "'");
        System.out.println("NIC Length: " + nic.length());

        if (!nic.matches("^([0-9]{9}[vVxX]|[0-9]{12})$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid NIC format!\nFormat: 123456789V or 123456789012").show();
            txtNic.requestFocus();
            return false;
        }

        // Validate Phone number (Sri Lankan format)
        String phone = txtContact.getText().trim();
        if (!phone.matches("^0[0-9]{9}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid phone number!\nFormat: 0XXXXXXXXX (10 digits)").show();
            txtContact.requestFocus();
            return false;
        }

        // Validate Email (optional but if provided should be valid)
        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid email format!").show();
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    @FXML
    void updateCustomer(ActionEvent event) {
        if (validateInputs()) {
            return;
        }
        try {
            boolean isUpdated = customerBo.updateCustomer(getCustomerFromForm());
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                loadAllCustomers();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        String id = txtCustomerId.getText();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete customer " + id + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean isDeleted = customerBo.deleteCustomer(id);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer deleted!").show();
                        loadAllCustomers();
                        clearFields();
                    }
                } catch (SQLException e) {
                    // Foreign key error එක catch කරනවා
                    if (e.getErrorCode() == 1451) {
                        new Alert(Alert.AlertType.WARNING,
                                "Cannot delete! This customer has existing orders.").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete failed: " + e.getMessage()).show();
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void clearFields(ActionEvent event) {
        clearFields();
    }

    @FXML
    void searchCustomer(ActionEvent event) {
        String text = txtSearch.getText().trim();
        if (text.isEmpty()) {
            loadAllCustomers();
            return;
        }

        try {
            ArrayList<CustomerDTO> result = customerBo.searchCustomerByText(text);
            customerList.clear();
            customerList.addAll(result);
            tblCustomers.setItems(customerList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Search error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tableClicked(MouseEvent event) {
        CustomerDTO selected = tblCustomers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fillFields(selected);
        }
    }

    public void backButtonAction(){
        App.setRoot(LoginController.getDashboardForRole());
    }

    @FXML
    private void handleCustomerPrint(ActionEvent event) {
        try{
            customerBo.printReports();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}