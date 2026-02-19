package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.ItemBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.impl.ItemBOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.ItemDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import lk.ijse.phoneshopmanagementsystem.entity.Item;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtModel;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<ItemDTO> tblItems;

    @FXML
    private TableColumn<ItemDTO, String> colItemCode;

    @FXML
    private TableColumn<ItemDTO, String> colDescription;

    @FXML
    private TableColumn<ItemDTO, String> colBrand;

    @FXML
    private TableColumn<ItemDTO, String> colModel;

    @FXML
    private TableColumn<ItemDTO, String> colCategory;

    @FXML
    private TableColumn<ItemDTO, Double> colUnitPrice;

    @FXML
    private TableColumn<ItemDTO, Integer> colQty;

    @FXML
    private TableColumn<ItemDTO, String> colSupplierId;

    private final ObservableList<ItemDTO> itemList = FXCollections.observableArrayList();
   //private final ItemDAOImpl itemDAO = new ItemDAOImpl();
   ItemBO  itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Item);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        cmbCategory.setItems(FXCollections.observableArrayList("Phone", "Accessory"));

        txtItemCode.setEditable(false);


        try {
            loadTableData();
            generateNextItemCode();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load data: " + e.getMessage());
        }

        tblItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadItemData(newSelection);
            }
        });
    }

    private void generateNextItemCode() throws SQLException, ClassNotFoundException {
        String nextCode = itemBO.getNextItemID();
        if (nextCode == null || nextCode.isEmpty()) {
            nextCode = "ITEM001";
        }
        txtItemCode.setText(nextCode);
    }

    private void loadItemData(ItemDTO item) {
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtBrand.setText(item.getBrand());
        txtModel.setText(item.getModel());
        cmbCategory.setValue(item.getCategory());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        txtSupplierId.setText(item.getSupplierId());
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemSet = itemBO.getAllItem();
        itemList.clear();
        itemList.addAll(itemSet);
        tblItems.setItems(itemList);
    }

    @FXML
    private void saveItem(ActionEvent event) {
        try {
            if (!validateFields()) {
                return;
            }

            String id = txtItemCode.getText();
            String model = txtModel.getText();
            String brand = txtBrand.getText();
            String category = cmbCategory.getValue();
            String supplierId = txtSupplierId.getText();
            String description = txtDescription.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

            ItemDTO itemDTO = new ItemDTO(id, description, brand, model, unitPrice, qtyOnHand, category, supplierId);

            boolean isSaved = itemBO.saveItem(itemDTO);

            if (isSaved) {
                loadTableData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item saved successfully!");
                clearFields(event);
                generateNextItemCode();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save item!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void searchItem(ActionEvent event) {
        try {
            String itemCode = txtItemCode.getText();
            if (itemCode.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter an item code!");
                return;
            }

            ItemDTO item = itemBO.searchItem(itemCode);
            if (item != null) {
                loadItemData(item);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item found!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "Item not found!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateItem(ActionEvent event) {
        try {
            ItemDTO selectedItem = tblItems.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select an item to update!");
                return;
            }

            if (!validateFields()) {
                return;
            }

            selectedItem.setDescription(txtDescription.getText());
            selectedItem.setBrand(txtBrand.getText());
            selectedItem.setModel(txtModel.getText());
            selectedItem.setCategory(cmbCategory.getValue());
            selectedItem.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
            selectedItem.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
            selectedItem.setSupplierId(txtSupplierId.getText());

            boolean isUpdated = itemBO.updateItem(selectedItem);

            if (isUpdated) {
                loadTableData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated successfully!");
                clearFields(event);
                generateNextItemCode();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update item!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Update failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        try {
            ItemDTO selectedItem = tblItems.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select an item to delete!");
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Item");
            confirmAlert.setContentText("Are you sure you want to delete this item?");

            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                boolean isDeleted = itemBO.deleteItem(selectedItem.getItemCode());

                if (isDeleted) {
                    loadTableData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item deleted successfully!");
                    clearFields(event);
                    generateNextItemCode();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete item!");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Delete failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void seachItem(ActionEvent event) throws SQLException, ClassNotFoundException {
        searchByText(event);
    }

    private void searchByText(ActionEvent event) throws SQLException, ClassNotFoundException {
        String searchText = txtSearch.getText().toLowerCase().trim();
        if (searchText.isEmpty()) {
            loadTableData();
        } else {
            ObservableList<ItemDTO> filteredList = FXCollections.observableArrayList();
            for (ItemDTO item : itemList) {
                if (item.getItemCode().toLowerCase().contains(searchText) ||
                        item.getBrand().toLowerCase().contains(searchText) ||
                        item.getModel().toLowerCase().contains(searchText) ||
                        item.getDescription().toLowerCase().contains(searchText)) {
                    filteredList.add(item);
                }
            }
            tblItems.setItems(filteredList);
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        txtDescription.clear();
        txtBrand.clear();
        txtModel.clear();
        cmbCategory.setValue(null);
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtSupplierId.clear();
        txtSearch.clear();
        tblItems.getSelectionModel().clearSelection();

        try {
            generateNextItemCode();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate item code: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateFields() {
        if (txtItemCode == null || txtItemCode.getText() == null || txtItemCode.getText().trim().isEmpty() ||
                txtDescription == null || txtDescription.getText() == null || txtDescription.getText().trim().isEmpty() ||
                txtBrand == null || txtBrand.getText() == null || txtBrand.getText().trim().isEmpty() ||
                txtModel == null || txtModel.getText() == null || txtModel.getText().trim().isEmpty() ||
                cmbCategory == null || cmbCategory.getValue() == null ||
                txtUnitPrice == null || txtUnitPrice.getText() == null || txtUnitPrice.getText().trim().isEmpty() ||
                txtQtyOnHand == null || txtQtyOnHand.getText() == null || txtQtyOnHand.getText().trim().isEmpty() ||
                txtSupplierId == null || txtSupplierId.getText() == null || txtSupplierId.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill all fields!");
            return false;
        }

        try {
            double price = Double.parseDouble(txtUnitPrice.getText().trim());
            int qty = Integer.parseInt(txtQtyOnHand.getText().trim());

            if (price < 0) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Unit Price cannot be negative!");
                return false;
            }

            if (qty < 0) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Quantity cannot be negative!");
                return false;
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Unit Price and Quantity must be valid numbers!");
            return false;
        }

        return true;
    }

    public void backButtonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleItemPrint(ActionEvent event) {
        try{
            itemBO.printItemReports();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}